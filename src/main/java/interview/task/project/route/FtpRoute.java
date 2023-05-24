package interview.task.project.route;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import interview.task.project.dto.DataDTO;
import interview.task.project.util.JSONObjectConverter;
import interview.task.project.util.XmlPrettyFormat;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpRoute extends RouteBuilder {
    @Value("${ftp.server.hostname}")
    private String ftpServerHostname;

    @Value("${ftp.server.username}")
    private String ftpUsername;

    @Value("${ftp.server.password}")
    private String ftpPassword;

    @Value("${ftp.server.csv.directory}")
    private String ftpCsvDirectory;

    @Value("${ftp.server.xml.directory}")
    private String ftpXmlDirectory;

    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3) // Maximum number of redelivery attempts
                .redeliveryDelay(1000) // Delay between redelivery attempts in milliseconds
                .retryAttemptedLogLevel(LoggingLevel.ERROR)); // Log level for retry attempts

        onException(Exception.class)
                .handled(true) // Mark the exception as handled
                .log(LoggingLevel.ERROR, "Error calling API: ${exception.message}")
                .maximumRedeliveries(0);

        from("direct:retrieveFile")
                .pollEnrich().simple("ftp://" + ftpServerHostname + "/" + ftpCsvDirectory + "?username=" + ftpUsername + "&password=" + ftpPassword + "&fileName=${header.fileName}");


        XStreamDataFormat xstreamDataFormat = new XStreamDataFormat();
        XStream xstream = new XStream(new DomDriver("UTF_8", new NoNameCoder()));
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.registerConverter(new JSONObjectConverter());
        xstream.processAnnotations(DataDTO.class);
        xstreamDataFormat.setXstream(xstream);

        from("direct:saveFile")
                .marshal(xstreamDataFormat)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String xml = exchange.getIn().getBody(String.class);
                        XmlPrettyFormat xmlPrettyFormat = new XmlPrettyFormat();
                        xml = xmlPrettyFormat.formatXml(xml);
                        exchange.getIn().setBody(xml);
                    }
                }).to("ftp://" + ftpServerHostname + "/" + ftpXmlDirectory + "?username=" + ftpUsername + "&password=" + ftpPassword + "&fileName=${header.fileName}");

    }

}

package interview.task.project.util;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONObjectConverter implements Converter {


    @Override
    public void marshal(Object obj, HierarchicalStreamWriter writer, com.thoughtworks.xstream.converters.MarshallingContext context) {
        JSONObject jsonObject = (JSONObject) obj;
        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);

            if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                for (int i = 0; i < jsonArray.length(); i++) {
                    writer.startNode(key);
                    marshal(((JSONArray) value).get(i), writer, context);
                    writer.endNode();
                }


            } else if (value instanceof JSONObject) {
                writer.startNode(key);
                marshal(value, writer, context);
                writer.endNode();// Recursively handle nested JSON objects
            } else {
                writer.startNode(key);
                writer.setValue(value.toString());
                writer.endNode();
            }
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, com.thoughtworks.xstream.converters.UnmarshallingContext context) {
        throw new UnsupportedOperationException("Unmarshaling of JSONObject is not supported.");
    }

    @Override
    public boolean canConvert(Class type) {
        return JSONObject.class.isAssignableFrom(type);
    }
}


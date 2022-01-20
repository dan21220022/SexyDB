import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class JsonConverter {

    public Object stringToObject(String jsonString)
    {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = null;
        try {
            obj = mapper.readValue(jsonString, Object.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public String objectToString(Object obj)
    {
        ObjectMapper mapper = new ObjectMapper();
        String recordString = "";
        try {
            recordString = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return recordString;
    }

}

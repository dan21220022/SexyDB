import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class JsonConverter {

    public HashMap<String, Object> stringToRecord(String jsonString)
    {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,Object> map = new HashMap<>();
        try {
            map = mapper.readValue(jsonString, HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String recordToString(HashMap<String, Object> record)
    {
        ObjectMapper mapper = new ObjectMapper();
        String recordString = "";
        try {
            recordString = mapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return recordString;
    }

}

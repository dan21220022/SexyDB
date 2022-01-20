import java.util.HashMap;

public class DataRecord {

    String path;
    int line;
    ObjectReader objectReader;
    HashMap<String, Object> fields;
    JsonConverter jsonConverter = new JsonConverter();

    DataRecord(String path, int line)
    {
        this.path = path;
        this.line = line;
        this.objectReader = new ObjectReader(path);
        this.fields = jsonConverter.stringToRecord(objectReader.readByLine(line));
    }


}

import java.util.ArrayList;
import java.util.HashMap;

//line 0 contains fieldNameToLine object
//line 1 contains path of table
//line 2 contains lastLine

public class Table {
    String tableName;
    String tablePath;
    String path;
    int currentLine;
    int currentLineTemp;

    HashMap<String, Integer> fieldNameToLine = new HashMap<>();

    ArrayList<String> fieldNames = new ArrayList<>();

    HashMap<String, ArrayList<Integer>> fieldsToLine = new HashMap<>();
    HashMap<String, ArrayList<Integer>> fieldsToLineTemp = new HashMap<>();

    //region constructors
    Table(String tableName, String path) {
        this.tableName = tableName;
        this.path = path;
        initFieldNameToLine();
        initFieldNames();
        initCurrentLine();
        initTablePath();
    }

    Table(String tableName, String path, String tablePath, ArrayList<String> fieldNames) {
        this.tableName = tableName;
        this.path = path;
        this.tablePath = tablePath;
        this.fieldNames = fieldNames;
        this.currentLine = 0;

        ObjectWriter objectWriter = new ObjectWriter(path);
        JsonConverter jsonConverter = new JsonConverter();

        for(int i = 0; i < fieldNames.size(); i++)
        {
            fieldNameToLine.put(fieldNames.get(i), 3+i);
        }

        objectWriter.writeObject(jsonConverter.objectToString(fieldNameToLine));
        objectWriter.writeObject(jsonConverter.objectToString(tablePath));
        objectWriter.writeObject(jsonConverter.objectToString(currentLine));

        for(int i = 0; i < fieldNames.size(); i++)
        {
            objectWriter.writeObject(jsonConverter.objectToString(fieldNameToLine));
        }
    }
    //endregion

    //region initiators
    private void initFieldNames() {
        for (String key : fieldNameToLine.keySet()) {
            if (!fieldNames.contains(key)) {
                fieldNames.add(key);
            }
        }
    }

    private void initFieldNameToLine() {
        ObjectReader objectReader = new ObjectReader(path);
        JsonConverter jsonConverter = new JsonConverter();
        fieldNameToLine =  (HashMap<String, Integer>) jsonConverter.stringToObject(objectReader.readByLine(0));
    }

    private void initCurrentLine()
    {
        ObjectReader objectReader = new ObjectReader(path);
        JsonConverter jsonConverter = new JsonConverter();
        currentLine =  (int) jsonConverter.stringToObject(objectReader.readByLine(2));
    }

    private void initTablePath()
    {
        ObjectReader objectReader = new ObjectReader(path);
        JsonConverter jsonConverter = new JsonConverter();
        tablePath = (String) jsonConverter.stringToObject(objectReader.readByLine(3));
    }

    //endregion

    public String getTableName() {
        return tableName;
    }


    private HashMap<String, ArrayList<Integer>> getFieldsToLineByFieldName(String fieldName)
    {
        ObjectReader objectReader = new ObjectReader(path);
        JsonConverter jsonConverter = new JsonConverter();
        return (HashMap<String, ArrayList<Integer>>) jsonConverter.stringToObject(objectReader.readByLine(fieldNameToLine.get(fieldName)));
    }

    // TODO: 20.01.2022
    public ArrayList<DataRecord> selectAllWhere(String fieldName, String fieldValue) {
        ArrayList<DataRecord> dataRecords = new ArrayList<>();
        fieldsToLine = getFieldsToLineByFieldName(fieldName);

        ArrayList<Integer> lines = fieldsToLine.get(fieldValue);

        for(int line : lines)
        {
            dataRecords.add(new DataRecord(tablePath, line));
        }

        return dataRecords;
    }

    public boolean addRecord(ArrayList<Object> values)
    {
        currentLineTemp = currentLine;
        if(values.size() < fieldNames.size())
        {
            return false;
        }
        else
        {
            HashMap<String, Object> fields = new HashMap<>();
            for(int i = 0; i < fieldNames.size(); i++)
            {
                fields.put(fieldNames.get(i), values.get(i));
                ObjectReader objectReader = new ObjectReader(path);
                JsonConverter jsonConverter = new JsonConverter();
                fieldsToLine = (HashMap<String, ArrayList<Integer>>) jsonConverter.stringToObject(objectReader.readByLine(fieldNameToLine.get(fieldNames.get(i))));
                fieldsToLineTemp = fieldsToLine;
                //fieldsToLineTemp.put();//TODO
                updateData();
            }
            ObjectWriter objectWriter = new ObjectWriter(tablePath);
            objectWriter.writeObject(fields);
            currentLine++;
        }

        return true;
    }

    public void updateData()
    {
        ObjectWriter objectWriter = new ObjectWriter(path);
        objectWriter.replaceObject();
    }

    public boolean removeWhere(String fieldName, String fieldValue) {
        return false;
    }


}

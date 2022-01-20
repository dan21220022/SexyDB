import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ObjectWriter {

    String path;

    ObjectWriter(String path)
    {
        this.path = path;
    }

    public void writeObject(Object obj)
    {
        JsonConverter jsonConverter = new JsonConverter();
        String jsonObj = jsonConverter.objectToString(obj);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(jsonObj);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceObject(Object oldObj, Object newObj)
    {
        JsonConverter jsonConverter = new JsonConverter();
        String oldObjJson = jsonConverter.objectToString(oldObj);
        String newObjJson = jsonConverter.objectToString(newObj);
        try {
            if(OsUtils.isUnix()) {
                String[] cmdarr = {"bash", "-c", "sed 's/" + oldObjJson + "/" + newObjJson + "/g' " + path + " > " + path + ""};
                Process runCmd = Runtime.getRuntime().exec(cmdarr);
            }
            else if(OsUtils.isWindows())
            {
                String[] cmdarr = {"cmd", "/c", "powershell -Command \"(gc " + path + ") -replace '" + oldObjJson + "', '" + newObjJson +"' | Out-File -encoding ASCII " + path};
                Process runCmd = Runtime.getRuntime().exec(cmdarr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeObject(Object oldObj)
    {
        JsonConverter jsonConverter = new JsonConverter();
        String oldObjJson = jsonConverter.objectToString(oldObj);
        String newObjJson = "";
        try {
            if(OsUtils.isUnix()) {
                String[] cmdarr = {"bash", "-c", "sed 's/" + oldObjJson + "/" + newObjJson + "/g' " + path + " > " + path + ""};
                Process runCmd = Runtime.getRuntime().exec(cmdarr);
            }
            else if(OsUtils.isWindows())
            {
                String[] cmdarr = {"cmd", "/c", "powershell -Command \"(gc " + path + ") -replace '" + oldObjJson + "', '" + newObjJson +"' | Out-File -encoding ASCII " + path};
                Process runCmd = Runtime.getRuntime().exec(cmdarr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

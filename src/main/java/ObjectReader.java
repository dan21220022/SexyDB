import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class ObjectReader {

    String path;

    ObjectReader(String path)
    {
        this.path = path;
    }

    public String ReadByLine(int line)
    {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
             return lines.skip(line).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

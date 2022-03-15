package my;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonUtilTest {

    Path fixtureDir = Paths.get(".").resolve("src/test/fixture");

    @Test
    public void test_prettyPrint_object() throws IOException {
        Path file = fixtureDir.resolve("object.json");
        String jsonText = new String(Files.readAllBytes(file));
        System.out.println("---- object ----");
        System.out.println(jsonText);
        String pp = JsonUtil.prettyPrint(jsonText);
        System.out.println(pp);
    }

    @Test
    public void test_prettyPrint_array() throws IOException {
        Path file = fixtureDir.resolve("array.json");
        String jsonText = new String(Files.readAllBytes(file));
        System.out.println("---- array ----");
        System.out.println(jsonText);
        String pp = JsonUtil.prettyPrint(jsonText, List.class);
        System.out.println(pp);
    }

}

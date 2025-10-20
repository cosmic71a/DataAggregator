package functions.function1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonReader {
	public static String readJsonFile(String file) throws IOException {
		Path path = Paths.get(file);
		String jsonString = new String(Files.readAllBytes(path));
		return jsonString;
	}
}

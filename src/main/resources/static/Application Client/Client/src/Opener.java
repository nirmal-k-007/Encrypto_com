import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Opener extends JFrame {

    Opener() throws JsonProcessingException {
        String userData = readFileContent("ChatApp/AppData/UserCredentials.json");
        if(userData != null) {
            FileObject data = new ObjectMapper().readValue(userData,FileObject.class);
            //new Home(data);
        }
        else {
            new Login();
        }
    }


    public static String readFileContent(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) { // Check if the file exists
            try {
                // Read the contents of the file into a String
                return new String(Files.readAllBytes(path));
            } catch (IOException e) {
                e.printStackTrace(); // Log the exception
            }
        }
        return null; // Return null if the file does not exist or could not be read
    }




    public static void main(String[] args) throws JsonProcessingException {
        new Opener();
    }
}

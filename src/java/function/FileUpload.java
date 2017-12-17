package function;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import javax.servlet.http.Part;

public class FileUpload {
    private final Part file;
    private final Path path;

    public FileUpload(Part file, Path path) {
        this.file = file;
        this.path = path;
    }
    
    public void submit(){
        System.out.println("FileUpload submit method start");
        try{
            InputStream in = file.getInputStream();
            Files.copy(in, path, REPLACE_EXISTING);
            System.out.println("FileUpload submit method successful");
        }catch(IOException e){
            System.out.println(e);
        }
    }
}

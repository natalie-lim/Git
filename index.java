import java.io.File;
import java.io.IOException;

public class index {
    public void init(){
        File index = new File("index.txt");
        if (!index.exists()) {
            Blob.write("index.txt", "");
        }
        File Objects = new File("Objects");
        if (!Objects.exists()){
            Objects.mkdirs();
        }
    }

    public void add(String fileName) throws IOException{
        Blob b = new Blob(fileName);
    }


}

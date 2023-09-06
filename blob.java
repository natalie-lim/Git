import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.util.zip.*;

public class Blob{
    public static void main(String[] args) throws IOException{
        index cool  = new index();
        // cool.init();
        // boolean check = cool.checkIfUnique("index.txt", "anotherFile.txt");
        // System.out.println(check);
        cool.add("anotherFile.txt");
        // cool.add("test.txt");
        // cool.remove("test.txt");

        // Blob blob = new Blob("test.txt");
    }

    //Creates a blob, which is a has of the compressed 
    //contents of a given file, then writes it to objects folder
    public Blob(String fileName) throws IOException{
        File obj = new File(fileName);
        String content = read(obj);
        String compressedContent = compress(content);
        String hashed = encryptThisString(compressedContent);
        write(hashed, content, "Objects");
    }

    //Returns compressed version of String
    public static String compress(String str) throws IOException{ 
        if (str == null || str.length() == 0) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    //Hashes String
    public static String encryptThisString(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //Reads a file and returns it as a String
    public static String read(File txt){
        String content = "";
        try 
        {
            File myObj = txt;
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()) 
            {
              String data = myReader.nextLine();
              content = content + data;
            }
            myReader.close();
          } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        return content;
    }

    //Writes to given directory
    public static void write(String fileName, String content, String directory){
        try
        {
            File file = new File(directory, fileName);
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
            System.out.println("Successfully wrote to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Write to local directory
    public static void write(String fileName, String content){
        try
        {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
            System.out.println("Successfully wrote to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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
        cool.init();

        // Blob blob = new Blob("test.txt");
    }

    //Takes a String Filename and reads its contents
    //Hash the content to another String variable
    //Writes content to a fileName with Hash to Objects
    public Blob(String fileName) throws IOException{
        File obj = new File(fileName);
        String content = read(obj);
        String compressedContent = compress(content);
        String hashed = encryptThisString(compressedContent);
        write(hashed, compressedContent, "Objects");
    }

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
            System.out.println("Reading file.");
            while(myReader.hasNextLine()) 
            {
              String data = myReader.nextLine();
              content = content + data;
            }
            myReader.close();
          } 
        catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }

    //Writes fileName with content to Objects directory
    public static void write(String fileName, String content, String directory){
        try
        {
            File file = new File(directory, fileName);
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void write(String fileName, String content){
        try
        {
            File file = new File(fileName);
            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
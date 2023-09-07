import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        Index index  = new Index();
        index.init();
        
        index.add("test1.txt");
        // index.add("test2.txt");
        // index.add("text3.txt");
        
        // index.remove("test2.txt");
    }

    //Creates a blob, which is a has of the compressed 
    //contents of a given file, then writes it to objects folder
    public Blob(String fileName) throws IOException{
        File obj = new File(fileName);
        String content = read(obj);
        byte[] compressedContent = compress(content);
        String hashed = encryptThisString(compressedContent);
        write(hashed, compressedContent, "objects");
    }

    //Returns compressed version of String
    public static byte[] compress(String str) throws IOException{ 
        if ((str == null) || (str.length() == 0)) {
            return null;
          }
          ByteArrayOutputStream obj = new ByteArrayOutputStream();
          GZIPOutputStream gzip = new GZIPOutputStream(obj);
          gzip.write(str.getBytes("UTF-8"));
          gzip.flush();
          gzip.close();
          return obj.toByteArray();
    }

    //Hashes String
    public static String encryptThisString(byte[] input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input);
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
    public static void write(String fileName, byte[] content, String directory){
        try
        {
            try (FileOutputStream fos = new FileOutputStream("Objects/" + fileName)) {
                fos.write(content);
            }
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
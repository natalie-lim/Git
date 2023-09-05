import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Blob{
    public static void main(String[] args){
        Blob blob = new Blob("test.txt");
    }

    //Takes a String Filename and reads its contents
    //Hash the content to another String variable
    //Writes content to a fileName with Hash to Objects
    public Blob(String fileName){
        File obj = new File(fileName);
        String content = read(obj);
        String hashed = encryptThisString(content);
        write(hashed, content);
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
    public static void write(String fileName, String content){
        try
        {
            File file = new File("Objects", fileName);
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
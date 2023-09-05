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

    public Blob(String txt){
        File obj = new File(txt);
        String content = read(obj);
        String hashed = encryptThisString(content);
        write(hashed, content);
    }

    public static String encryptThisString(String input){
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");
 
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
 
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
 
            // return the HashText
            return hashtext;
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(File txt){
        String stringifiedFile = "";
        try 
        {
            File myObj = txt;
            Scanner myReader = new Scanner(myObj);
            System.out.println("Reading file.");
            while(myReader.hasNextLine()) 
            {
              String data = myReader.nextLine();
              stringifiedFile = stringifiedFile + data;
            }
            myReader.close();
          } 
        catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return stringifiedFile;
    }

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
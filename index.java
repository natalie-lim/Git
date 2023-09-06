import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Index{
    //initilizes Repository with an index.txt and Objects dir
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
        if (!checkIfUnique("index.txt", fileName)){
            System.out.println("File Found");
            return;
        }
        Blob blob = new Blob(fileName);
        File f = new File(fileName);
        String toAdd = fileName + " : " + Blob.encryptThisString(Blob.compress(Blob.read(f)));
        try (FileWriter file = new FileWriter("index.txt", true);
            BufferedWriter b = new BufferedWriter(file);
            PrintWriter p = new PrintWriter(b);){
                p.println(toAdd);
        }
    }

    public void remove(String fileName) throws IOException{
        File inputFile = new File("index.txt");
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        File toRead = new File(fileName);
        String lineToRemove = fileName + " : " + Blob.encryptThisString(Blob.compress(Blob.read(toRead)));
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
        // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        writer.close(); 
        reader.close(); 
        boolean successful = tempFile.renameTo(inputFile);
    }

    public boolean checkIfUnique(String fileToSearchIn, String fileToSearch) throws IOException{
        Scanner scan = new Scanner(new File(fileToSearchIn));
        File toRead = new File(fileToSearch);
        String toSearch = fileToSearch + " : " + Blob.encryptThisString(Blob.compress(Blob.read(toRead)));
        while(scan.hasNext()){
            String line = scan.nextLine().toString();
            System.out.println(line.equals(toSearch));
            if(line.equals(toSearch)){
                return false;
            }
        }
        return true;
    }
}



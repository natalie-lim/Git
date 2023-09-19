import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Scanner;

<<<<<<< Updated upstream
public class Index {
    // initilizes Repository with an index.txt and Objects dir
    public void init() {
=======
public class Index{
    //initilizes Repository with an index file and objects dir
    public void init(){
>>>>>>> Stashed changes
        File index = new File("index");
        if (!index.exists()) {
            Blob.write("index", "");
        }
        File Objects = new File("objects");
        if (!Objects.exists()) {
            Objects.mkdirs();
        }
    }
<<<<<<< Updated upstream

    public void add(String fileName) throws IOException {
        // Creates a Blob of fileName that gets added to Objects
=======
    
    //Adds a blob of fileName to objects and fileName : hash to index
    public void add(String fileName) throws IOException{
        //Creates a Blob of fileName that gets added to Objects
>>>>>>> Stashed changes
        Blob blob = new Blob(fileName);
        if (!checkIfUnique("index", fileName)) {
            System.out.println("File Found");
            return;
        }
        File f = new File(fileName);
        String toAdd = fileName + " : " + Blob.encryptThisString(Blob.compress(Blob.read(f)));
        try (FileWriter file = new FileWriter("index", true);
                BufferedWriter b = new BufferedWriter(file);
                PrintWriter p = new PrintWriter(b);) {
            p.println(toAdd);
        }
    }

<<<<<<< Updated upstream
    public void remove(String fileName) throws IOException {
=======
    //remove a unique fileName : hash pair in index file
    public void remove(String fileName) throws IOException{
>>>>>>> Stashed changes
        File inputFile = new File("index");
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        File toRead = new File(fileName);
        String lineToRemove = fileName + " : " + Blob.encryptThisString(Blob.compress(Blob.read(toRead)));
        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(lineToRemove))
                continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(inputFile);
    }

<<<<<<< Updated upstream
    public boolean checkIfUnique(String fileToSearchIn, String fileToSearch) throws IOException {
=======
    //Checks if there is a unique fileName : hash pair in index file
    //Means that remove doesn't have to check if there is a unique
    public boolean checkIfUnique(String fileToSearchIn, String fileToSearch) throws IOException{
>>>>>>> Stashed changes
        Scanner scan = new Scanner(new File(fileToSearchIn));
        File toRead = new File(fileToSearch);
        String toSearch = fileToSearch + " : " + Blob.encryptThisString(Blob.compress(Blob.read(toRead)));
        while (scan.hasNext()) {
            String line = scan.nextLine().toString();
            System.out.println(line.equals(toSearch));
            if (line.equals(toSearch)) {
                return false;
            }
        }
        return true;
    }
}

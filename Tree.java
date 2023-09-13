import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

public class Tree {

    private String hash = "";

    public static void main(String[] args) throws IOException {
        Tree t = new Tree();
        t.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        t.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        t.add("tree : penis");
        t.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        t.remove("file1.txt");
        System.out.println(decompress("objects/" + t.getHash()));

    }

    public String getHash() {
        return hash;
    }

    public void add(String content) throws IOException {
        File Objects = new File("objects");
        if (!Objects.exists()) {
            Objects.mkdirs();
        }
        if (hash != "") {
            if (!checkIfUnique("objects/" + hash, content)) {
                return;
            }
            content = '\n' + content;
        }
        byte[] compressed = Blob.compress(content);
        if (hash == "") {
            hash = Blob.encryptThisString(compressed);
            write(hash, compressed, "objects", true);
        } else {
            File oldFile = new File("objects/" + hash);
            write(hash, compressed, "objects", true);
            hash = getSha(new File("objects/" + hash), content);
            File newFile = new File("objects/" + hash);
            oldFile.renameTo(newFile);
        }

    }

    public void remove(String delteFileName) throws FileNotFoundException, IOException {
        String fileContents = decompress("objects/" + hash);
        String keepString = "";
        boolean didDeleteAnything = false;

        try (Scanner scan = new Scanner(fileContents)) {
            while (scan.hasNext()) {
                String line = scan.nextLine().toString();
                String[] split = line.split("\\s+");
                if (split[2].equals(delteFileName) || split[split.length - 1].equals(delteFileName)) {
                    didDeleteAnything = true;
                } else {
                    keepString += line;
                    keepString += '\n';
                }
            }
        }
        if (!didDeleteAnything) {
            return;
        }
        keepString = keepString.trim();

        byte[] compressed = Blob.compress(keepString);
        File oldFile = new File("objects/" + hash);
        write(hash, compressed, "objects", false);
        hash = Blob.encryptThisString(compressed);
        File newFile = new File("objects/" + hash);
        oldFile.renameTo(newFile);

    }

    private String getSha(File file, String newContent) {
        String content = "";
        try {
            File myObj = file;
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                content = content + data;
            }
            content += newContent;
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String encrypted = Blob.encryptThisString(content.getBytes());
        return encrypted;
    }

    public boolean checkIfUnique(String fileToSearchIn, String content) throws IOException {
        String fileContents = decompress(fileToSearchIn);
        try (Scanner scan = new Scanner(fileContents)) {
            while (scan.hasNext()) {
                String line = scan.nextLine().toString();
                if (line.equals(content)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void write(String fileName, byte[] content, String directory, boolean append) {
        try {
            try (FileOutputStream fos = new FileOutputStream("Objects/" + fileName, append)) {
                fos.write(content);

            }
            System.out.println("Successfully wrote to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String decompress(String path) throws FileNotFoundException, IOException {
        try (
                FileInputStream fis = new FileInputStream(path);
                GZIPInputStream gis = new GZIPInputStream(fis);
                ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return bos.toString("UTF-8");
        }
    }

}

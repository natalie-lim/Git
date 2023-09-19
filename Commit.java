import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;

public class Commit {
    private String shaPrevious;
    private String shaNext;
    private String author;
    private String date;
    private String summary;
    private Tree tree;
    private File tempFile;
    private String fileContentsWithoutThird;
    private String fileContents;

    //A commit constructor takes an optional String of the SHA1 of a parent Commit, and two Strings for author and summary
    public Commit (String author, String summary) throws IOException {
        this.tree = new Tree();
        this.author = author;
        this.summary = summary;
        createFile();
        Date d1 = new Date();

    }
    public Commit (String parent, String author, String summary) throws IOException {
        this.tree = new Tree();
        this.shaPrevious = parent;
        this.author = author;
        this.summary = summary;
        createFile();
        Date d1 = new Date();
    }

    public void setContents() {
        this.fileContents = tree.getSha(new File("objects/" + tree.getHash()))
        + "\n" + shaPrevious
        + "\n" + shaNext
        + "\n" + author
        + "\n" + date
        + "\n" + summary;
    }

    public void setWithoutContents() {
        this.fileContentsWithoutThird = tree.getSha(new File("objects/" + tree.getHash()))
        + "\n" + shaPrevious
        + "\n" + author
        + "\n" + date
        + "\n" + summary;
    }

    public void createFile() throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("objects/" + convertToSha1(fileContentsWithoutThird)));
        pw.print(fileContents);
        pw.close();
    }

    public static String convertToSha1(String fileContents) {
    String sha1 = "";
    try {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(fileContents.getBytes("UTF-8"));
        sha1 = byteToHex(crypt.digest());
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
    return sha1;
    }

    // Used for sha1
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}

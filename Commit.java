import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Formatter;

public class Commit {
    private String shaPrevious;
    private String shaNext;
    private String author;
    private String date;
    private String summary;
    private Tree tree;
    private String fileContentsWithoutThird;
    private String fileContents;

    //A commit constructor takes an optional String of the SHA1 of a parent Commit, and two Strings for author and summary
    public Commit (String parent, String author, String summary) throws IOException {
        File objects = new File("objects");
        if (!objects.exists()) {
            objects.mkdirs();
        }
        this.tree = new Tree();
        this.shaPrevious = parent;
        this.author = author;
        this.shaNext = "";
        this.summary = summary;
        this.date = getDate();
        setContents();
        setWithoutContents();
        if (!parent.equals("")) {
            changeParent();
        }
        createFile();
    }
    public Commit (String author, String summary) throws IOException {
        this("", author, summary);
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
        File file = new File("objects/" + convertToSha1(fileContentsWithoutThird));
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter pw = new PrintWriter(new FileWriter(file));
        pw.print(fileContents);
        pw.close();
    }

    public void changeParent() throws IOException {
        File toChange = new File("objects/" + shaPrevious);
        BufferedReader br = new BufferedReader(new FileReader(toChange));
        File temp = new File ("temp");
        PrintWriter pw = new PrintWriter(temp);
        pw.println(br.readLine());
        pw.println(br.readLine());
        pw.println(getShaOfCommit());
        br.readLine();
        pw.println(br.readLine());
        pw.println(br.readLine());
        pw.print(br.readLine());
        br.close();
        pw.close();
        temp.renameTo(toChange);
    }

    public String getShaOfCommit() {
        return convertToSha1(fileContentsWithoutThird);
    }

    public String getDate() {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        String monthName = Month.of(month).name();
        int day = localDate.getDayOfMonth();
        return (monthName + " " + day + ", " + year);
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

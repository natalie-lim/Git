import java.io.IOException;

public class CommitTester {
    public static void main (String [] args) throws IOException {
        Commit commit1 = new Commit("bertrum", "first commit");
        Commit commit2 = new Commit("d0c96342b33cfcd774ed4f5be3ce106ee6366dc5", "author", "second commit");
    }
}

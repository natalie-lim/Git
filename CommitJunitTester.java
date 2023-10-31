import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommitJunitTester {
    @Test
    static void testOneCommit() throws IOException {
        Commit commit1 = new Commit("bertrum", "first commit");
        BufferedReader br = new BufferedReader(new FileReader("objects/" + commit1.getShaOfCommit()));
        String tree = "";
        String previous = "";
        String next = "";
        String author = "";
        String summary = "";
        if (br.ready()) {
            tree = br.readLine();
        }
        assertEquals(tree, "da39a3ee5e6b4b0d3255bfef95601890afd80709");
        if (br.ready()) {
            previous = br.readLine();
        }
        assertEquals(previous, "");
        if (br.ready()) {
            next = br.readLine();
        }
        assertEquals(next, "");
        if (br.ready()) {
            author = br.readLine();
        }
        assertEquals(author, "bertrum");
        if (br.ready()) {
            summary = br.readLine();
        }
        assertEquals(summary, "first commit");
        br.close();
    }
    @Test
    static void testTwoCommits() throws IOException {
        Commit commit1 = new Commit("bertrum", "first commit");
        Commit commit2 = new Commit(commit1.getShaOfCommit(), "author", "second commit");
        BufferedReader br = new BufferedReader(new FileReader("objects/" + commit2.getShaOfCommit()));
        String tree = "";
        String previous = "";
        String next = "";
        String author = "";
        String summary = "";
        if (br.ready()) {
            tree = br.readLine();
        }
        assertEquals(tree, "da39a3ee5e6b4b0d3255bfef95601890afd80709");
        if (br.ready()) {
            previous = br.readLine();
        }
        assertEquals(previous, commit1.getShaOfCommit());
        if (br.ready()) {
            next = br.readLine();
        }
        assertEquals(next, "");
        if (br.ready()) {
            author = br.readLine();
        }
        assertEquals(author, "bertrum");
        if (br.ready()) {
            summary = br.readLine();
        }
        assertEquals(summary, "second commit");
        br.close();

        //test that changing the previous works
        BufferedReader br2 = new BufferedReader(new FileReader("objects/" + commit1.getShaOfCommit()));
        br2.readLine();
        br2.readLine();
        assertEquals(br2.readLine(), commit2.getShaOfCommit());

    }
}

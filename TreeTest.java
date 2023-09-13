import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TreeTest {

    @Test
    public void testAdd() throws IOException {
        Tree tree = new Tree();
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");

        assertEquals(
                "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b\nblob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt",
                decompress("objects/" + tree.getHash()));

        // cleanup cause the teardown didnt work for somereason:
        Files.deleteIfExists(Paths.get("objects/" + tree.getHash()));

    }

    @Test
    public void testRemove() throws IOException {
        Tree tree = new Tree();
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");

        tree.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        tree.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        tree.remove("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.remove("file1.txt");

        assertEquals(
                "tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976\nblob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt",
                decompress("objects/" + tree.getHash()));

        // cleanup cause the teardown didnt work for somereason:
        Files.deleteIfExists(Paths.get("objects/" + tree.getHash()));

    }

    public String decompress(String path) throws FileNotFoundException, IOException {
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

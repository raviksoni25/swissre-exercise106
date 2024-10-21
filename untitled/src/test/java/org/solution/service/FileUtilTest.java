package org.solution.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.solution.dao.DataStore;
import org.solution.util.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

    private FileUtil fileUtil;
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        fileUtil = new FileUtil();
        // Create a temporary file
        tempFile = Files.createTempFile("testfile", ".csv").toFile();
    }

    @AfterEach
    void tearDown() {
        // Clean up the temporary file
        if (tempFile != null && tempFile.exists()) {
            boolean removed = tempFile.delete();
        }
    }

    @Test
    void testReadFileValid() throws IOException {
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("id,firstName,lastName,salary,managerId\n");
            writer.write("1,John,Doe,50000,\n");
            writer.write("2,Jane,Smith,60000,1\n");
        }

        DataStore dataStore = fileUtil.readFile(tempFile.getAbsolutePath());

        assertNotNull(dataStore);
        assertEquals(2, dataStore.getEmployeeMap().size());
        assertTrue(dataStore.getManagerMap().containsKey(1));
        assertEquals(List.of(2), dataStore.getManagerMap().get(1));
    }

    @Test
    void testReadFileInvalidPath() {
        assertThrows(IllegalArgumentException.class, () -> fileUtil.readFile(null));
        assertThrows(IllegalArgumentException.class, () -> fileUtil.readFile(""));
    }

    @Test
    void testReadFileIOException() {
        assertThrows(RuntimeException.class, () -> {
            fileUtil.readFile("invalid_path.csv");
        });
    }


}

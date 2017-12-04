import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class JavaClassGeneratorTest {
    JavaClassGenerator testGenerator;
    File testFile;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        testGenerator = new JavaClassGenerator();
        ClassLoader classLoader = getClass().getClassLoader();
        testFile = new File(classLoader.getResource("ReferencePOJO.java").getFile());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        testGenerator = null;
        testFile = null;
    }

    @org.junit.jupiter.api.Test
    void generateClassFromClassFile() {
        try {
            testGenerator.GenerateClassFromClassFile(testFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
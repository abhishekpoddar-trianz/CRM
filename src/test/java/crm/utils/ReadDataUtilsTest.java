package crm.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.JFrame;
import java.io.File;

public class ReadDataUtilsTest {

    @Test
    public void testReadFileWithNullParent() {
        String dialogMessage = "Select a file";
        String fileExtensionDescription = "Text files";
        String[] fileExtension = {"txt"};

        File result = ReadDataUtils.ReadFile(dialogMessage, null, fileExtensionDescription, fileExtension);
        assertNull(result, "ReadFile should return null when no file is selected");
    }

    @Test
    public void testReadFileWithValidParameters() {
        String dialogMessage = "Select a CSV file";
        JFrame parent = new JFrame();
        String fileExtensionDescription = "CSV files";
        String[] fileExtension = {"csv"};

        File result = ReadDataUtils.ReadFile(dialogMessage, parent, fileExtensionDescription, fileExtension);
        // Note: This test will return null in headless environment as no user interaction
        assertNull(result);
        parent.dispose();
    }

    @Test
    public void testReadFileWithMultipleExtensions() {
        String dialogMessage = "Select a document";
        JFrame parent = new JFrame();
        String fileExtensionDescription = "Document files";
        String[] fileExtensions = {"pdf", "doc", "docx"};

        File result = ReadDataUtils.ReadFile(dialogMessage, parent, fileExtensionDescription, fileExtensions);
        assertNull(result);
        parent.dispose();
    }

    @Test
    public void testReadFileWithEmptyMessage() {
        String dialogMessage = "";
        JFrame parent = new JFrame();
        String fileExtensionDescription = "All files";
        String[] fileExtension = {"*"};

        File result = ReadDataUtils.ReadFile(dialogMessage, parent, fileExtensionDescription, fileExtension);
        assertNull(result);
        parent.dispose();
    }

    @Test
    public void testReadFileWithSingleExtension() {
        String dialogMessage = "Select an image";
        JFrame parent = new JFrame();
        String fileExtensionDescription = "Image files";
        String[] fileExtension = {"jpg"};

        File result = ReadDataUtils.ReadFile(dialogMessage, parent, fileExtensionDescription, fileExtension);
        assertNull(result);
        parent.dispose();
    }
}
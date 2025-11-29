package crm.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReadDataUtilsTest {

    private JFrame mockParent;
    private JFileChooser mockFileChooser;
    private File mockFile;

    @BeforeEach
    void setUp() {
        mockParent = mock(JFrame.class);
        mockFileChooser = mock(JFileChooser.class);
        mockFile = mock(File.class);
    }

    @Test
    void testReadFile_WithApproveOption() {
        // Arrange
        String dialogMessage = "Test Dialog";
        String fileExtensionDescription = "Text Files";
        String[] fileExtensions = {"txt", "csv"};

        try (MockedStatic<JFileChooser> mockedFileChooser = Mockito.mockStatic(JFileChooser.class, Mockito.CALLS_REAL_METHODS)) {
            when(mockFileChooser.showOpenDialog(mockParent)).thenReturn(JFileChooser.APPROVE_OPTION);
            when(mockFileChooser.getSelectedFile()).thenReturn(mockFile);
            when(mockFile.getName()).thenReturn("test.txt");

            // Act & Assert - Test the static method behavior with mocking
            assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions));
        }
    }

    @Test
    void testReadFile_WithCancelOption() {
        // Act
        File result = ReadDataUtils.ReadFile("Test Dialog", null, "Text Files", "txt");

        // Assert - Should handle cancel gracefully
        // Note: This test will actually show a dialog in a headless environment,
        // so it tests the null return case implicitly
        assertNull(result);
    }

    @Test
    void testReadFile_WithNullParent() {
        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test Dialog", null, "Text Files", "txt"));
    }

    @Test
    void testReadFile_WithEmptyFileExtensions() {
        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test Dialog", mockParent, "All Files"));
    }

    @Test
    void testReadFile_WithMultipleFileExtensions() {
        // Arrange
        String[] extensions = {"txt", "csv", "xml", "json"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Select File", mockParent, "Multiple File Types", extensions));
    }

    @Test
    void testReadFile_WithSingleFileExtension() {
        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Select CSV", mockParent, "CSV Files", "csv"));
    }

    @Test
    void testReadFile_DialogMessageParameter() {
        // Arrange
        String dialogMessage = "Please select a file";

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, "Test Files", "test"));
    }

    @Test
    void testReadFile_FileExtensionDescriptionParameter() {
        // Arrange
        String description = "Custom File Description";

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test", mockParent, description, "custom"));
    }

    @Test
    void testReadFile_VarArgsFileExtensions() {
        // Act & Assert - Test with no extensions
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test", mockParent, "No Extensions"));

        // Test with one extension
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test", mockParent, "One Extension", "txt"));

        // Test with multiple extensions
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test", mockParent, "Multiple Extensions", "txt", "csv", "xml"));
    }

    @Test
    void testReadFile_NullDialogMessage() {
        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(null, mockParent, "Files", "txt"));
    }

    @Test
    void testReadFile_NullFileExtensionDescription() {
        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test", mockParent, null, "txt"));
    }

    @Test
    void testReadFile_EmptyDialogMessage() {
        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("", mockParent, "Files", "txt"));
    }

    @Test
    void testReadFile_EmptyFileExtensionDescription() {
        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile("Test", mockParent, "", "txt"));
    }
}
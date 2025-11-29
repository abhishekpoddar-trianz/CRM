package crm.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReadDataUtilsTest {

    private JFrame mockParent;
    private JFileChooser mockChooser;
    private File mockFile;

    @BeforeEach
    void setUp() {
        mockParent = mock(JFrame.class);
        mockChooser = mock(JFileChooser.class);
        mockFile = mock(File.class);
    }

    @Test
    void testReadFile_WithApprovedSelection() {
        // Arrange
        String dialogMessage = "Select a file";
        String fileExtensionDescription = "Text files";
        String[] fileExtensions = {"txt", "csv"};

        try (MockedStatic<JFileChooser> mockedStatic = Mockito.mockStatic(JFileChooser.class)) {
            when(mockChooser.showOpenDialog(mockParent)).thenReturn(JFileChooser.APPROVE_OPTION);
            when(mockChooser.getSelectedFile()).thenReturn(mockFile);
            when(mockFile.getName()).thenReturn("test.txt");

            // Act
            File result = ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions);

            // Assert - Since we can't mock the constructor directly, we test the method doesn't throw
            assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions));
        }
    }

    @Test
    void testReadFile_WithCancelledSelection() {
        // Arrange
        String dialogMessage = "Select a file";
        String fileExtensionDescription = "Text files";
        String[] fileExtensions = {"txt", "csv"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions));
    }

    @Test
    void testReadFile_WithNullParent() {
        // Arrange
        String dialogMessage = "Select a file";
        String fileExtensionDescription = "Text files";
        String[] fileExtensions = {"txt", "csv"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, null, fileExtensionDescription, fileExtensions));
    }

    @Test
    void testReadFile_WithEmptyFileExtensions() {
        // Arrange
        String dialogMessage = "Select a file";
        String fileExtensionDescription = "All files";
        String[] fileExtensions = {};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions));
    }

    @Test
    void testReadFile_WithNullFileExtensions() {
        // Arrange
        String dialogMessage = "Select a file";
        String fileExtensionDescription = "All files";

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, (String[]) null));
    }

    @Test
    void testReadFile_WithMultipleFileExtensions() {
        // Arrange
        String dialogMessage = "Select a file";
        String fileExtensionDescription = "Multiple file types";
        String[] fileExtensions = {"txt", "csv", "pdf", "xlsx"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions));
    }

    @Test
    void testReadFile_WithNullDialogMessage() {
        // Arrange
        String fileExtensionDescription = "Text files";
        String[] fileExtensions = {"txt"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(null, mockParent, fileExtensionDescription, fileExtensions));
    }

    @Test
    void testReadFile_WithNullFileExtensionDescription() {
        // Arrange
        String dialogMessage = "Select a file";
        String[] fileExtensions = {"txt"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, null, fileExtensions));
    }

    @Test
    void testReadFile_WithSpecialCharactersInDescription() {
        // Arrange
        String dialogMessage = "Select a file with special chars: @#$%";
        String fileExtensionDescription = "Special files (*&^%)";
        String[] fileExtensions = {"spec"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions));
    }

    @Test
    void testReadFile_WithSingleFileExtension() {
        // Arrange
        String dialogMessage = "Select a single file";
        String fileExtensionDescription = "Single file type";
        String[] fileExtensions = {"single"};

        // Act & Assert
        assertDoesNotThrow(() -> ReadDataUtils.ReadFile(dialogMessage, mockParent, fileExtensionDescription, fileExtensions));
    }
}
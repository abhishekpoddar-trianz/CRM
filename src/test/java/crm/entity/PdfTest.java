package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import static org.junit.jupiter.api.Assertions.*;

class PdfTest {

    private Pdf pdf;

    @BeforeEach
    void setUp() {
        pdf = new Pdf();
    }

    @Test
    void pdf_defaultConstructor_shouldCreateInstance() {
        // Act
        Pdf newPdf = new Pdf();

        // Assert
        assertNotNull(newPdf, "Pdf should be created");
        assertNull(newPdf.getId(), "Default id should be null");
        assertNull(newPdf.getName(), "Default name should be null");
        assertNull(newPdf.getContent(), "Default content should be null");
    }

    @Test
    void pdf_allArgsConstructor_shouldCreateInstanceWithAllFields() {
        // Arrange
        Long id = 1L;
        String name = "test.pdf";
        String content = "PDF content";

        // Act
        Pdf newPdf = new Pdf(id, name, content);

        // Assert
        assertNotNull(newPdf, "Pdf should be created");
        assertEquals(id, newPdf.getId());
        assertEquals(name, newPdf.getName());
        assertEquals(content, newPdf.getContent());
    }

    @Test
    void pdf_builder_shouldCreateInstanceWithSpecifiedFields() {
        // Arrange
        String name = "test.pdf";
        String content = "PDF content";

        // Act
        Pdf newPdf = Pdf.builder()
                .name(name)
                .content(content)
                .build();

        // Assert
        assertNotNull(newPdf, "Pdf should be created");
        assertEquals(name, newPdf.getName());
        assertEquals(content, newPdf.getContent());
    }

    @Test
    void setId_withValidId_shouldSetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        pdf.setId(expectedId);

        // Assert
        assertEquals(expectedId, pdf.getId(), "Id should be set correctly");
    }

    @Test
    void getId_afterSettingId_shouldReturnCorrectId() {
        // Arrange
        Long expectedId = 456L;
        pdf.setId(expectedId);

        // Act
        Long actualId = pdf.getId();

        // Assert
        assertEquals(expectedId, actualId, "getId should return the set id");
    }

    @Test
    void setName_withValidName_shouldSetName() {
        // Arrange
        String expectedName = "document.pdf";

        // Act
        pdf.setName(expectedName);

        // Assert
        assertEquals(expectedName, pdf.getName(), "Name should be set correctly");
    }

    @Test
    void getName_afterSettingName_shouldReturnCorrectName() {
        // Arrange
        String expectedName = "report.pdf";
        pdf.setName(expectedName);

        // Act
        String actualName = pdf.getName();

        // Assert
        assertEquals(expectedName, actualName, "getName should return the set name");
    }

    @Test
    void setContent_withValidContent_shouldSetContent() {
        // Arrange
        String expectedContent = "This is PDF content";

        // Act
        pdf.setContent(expectedContent);

        // Assert
        assertEquals(expectedContent, pdf.getContent(), "Content should be set correctly");
    }

    @Test
    void getContent_afterSettingContent_shouldReturnCorrectContent() {
        // Arrange
        String expectedContent = "PDF binary content";
        pdf.setContent(expectedContent);

        // Act
        String actualContent = pdf.getContent();

        // Assert
        assertEquals(expectedContent, actualContent, "getContent should return the set content");
    }

    @Test
    void setName_withNullName_shouldSetNullName() {
        // Act
        pdf.setName(null);

        // Assert
        assertNull(pdf.getName(), "Name should be null when set to null");
    }

    @Test
    void setName_withEmptyName_shouldSetEmptyName() {
        // Arrange
        String emptyName = "";

        // Act
        pdf.setName(emptyName);

        // Assert
        assertEquals(emptyName, pdf.getName(), "Name should be empty string");
    }

    @Test
    void setContent_withNullContent_shouldSetNullContent() {
        // Act
        pdf.setContent(null);

        // Assert
        assertNull(pdf.getContent(), "Content should be null when set to null");
    }

    @Test
    void setContent_withEmptyContent_shouldSetEmptyContent() {
        // Arrange
        String emptyContent = "";

        // Act
        pdf.setContent(emptyContent);

        // Assert
        assertEquals(emptyContent, pdf.getContent(), "Content should be empty string");
    }

    @Test
    void setId_withNullId_shouldSetNullId() {
        // Act
        pdf.setId(null);

        // Assert
        assertNull(pdf.getId(), "Id should be null when set to null");
    }

    @Test
    void setId_withZeroId_shouldSetZeroId() {
        // Act
        pdf.setId(0L);

        // Assert
        assertEquals(0L, pdf.getId(), "Id should be set to 0");
    }

    @Test
    void setName_withLongName_shouldSetLongName() {
        // Arrange
        String longName = "this-is-a-very-long-pdf-file-name-that-might-be-used-in-some-systems.pdf";

        // Act
        pdf.setName(longName);

        // Assert
        assertEquals(longName, pdf.getName(), "Long name should be set correctly");
    }

    @Test
    void setContent_withLongContent_shouldSetLongContent() {
        // Arrange
        String longContent = "This is a very long PDF content that might contain multiple pages and various text elements";

        // Act
        pdf.setContent(longContent);

        // Assert
        assertEquals(longContent, pdf.getContent(), "Long content should be set correctly");
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        Pdf pdf1 = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("PDF content")
                .build();

        Pdf pdf2 = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("PDF content")
                .build();

        // Act & Assert
        assertEquals(pdf1, pdf2, "PDFs with same values should be equal");
        assertEquals(pdf1.hashCode(), pdf2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void equals_withDifferentValues_shouldNotBeEqual() {
        // Arrange
        Pdf pdf1 = Pdf.builder()
                .id(1L)
                .name("test1.pdf")
                .content("Content 1")
                .build();

        Pdf pdf2 = Pdf.builder()
                .id(2L)
                .name("test2.pdf")
                .content("Content 2")
                .build();

        // Act & Assert
        assertNotEquals(pdf1, pdf2, "PDFs with different values should not be equal");
    }

    @Test
    void toString_shouldReturnStringRepresentation() {
        // Arrange
        pdf.setId(1L);
        pdf.setName("test.pdf");
        pdf.setContent("PDF content");

        // Act
        String result = pdf.toString();

        // Assert
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("1"), "toString should contain id");
        assertTrue(result.contains("test.pdf"), "toString should contain name");
        assertTrue(result.contains("PDF content"), "toString should contain content");
    }

    @Test
    void toString_withNullValues_shouldHandleGracefully() {
        // Act
        String result = pdf.toString();

        // Assert
        assertNotNull(result, "toString should not return null even with null values");
    }

    @Test
    void pdfClass_shouldHaveCorrectJPAAnnotations() {
        // Assert
        assertTrue(Pdf.class.isAnnotationPresent(Entity.class), "Pdf should have @Entity annotation");

        Entity entityAnnotation = Pdf.class.getAnnotation(Entity.class);
        assertEquals("pdf", entityAnnotation.name(), "Entity name should be 'pdf'");
    }

    @Test
    void idField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var idField = Pdf.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(Id.class), "id field should have @Id annotation");
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class), "id field should have @GeneratedValue annotation");

        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy(), "GeneratedValue strategy should be IDENTITY");
    }

    @Test
    void nameField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var nameField = Pdf.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(Column.class), "name field should have @Column annotation");
        assertTrue(nameField.isAnnotationPresent(Size.class), "name field should have @Size annotation");

        Column column = nameField.getAnnotation(Column.class);
        assertFalse(column.nullable(), "Column should not be nullable");

        Size size = nameField.getAnnotation(Size.class);
        assertEquals(2, size.min(), "Size min should be 2");
    }

    @Test
    void contentField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var contentField = Pdf.class.getDeclaredField("content");

        // Assert
        assertTrue(contentField.isAnnotationPresent(Transient.class), "content field should have @Transient annotation");
    }

    @Test
    void pdfClass_shouldHaveLombokAnnotations() {
        // Assert
        assertTrue(Pdf.class.isAnnotationPresent(lombok.Data.class), "Pdf should have @Data annotation");
        assertTrue(Pdf.class.isAnnotationPresent(lombok.Builder.class), "Pdf should have @Builder annotation");
        assertTrue(Pdf.class.isAnnotationPresent(lombok.NoArgsConstructor.class), "Pdf should have @NoArgsConstructor annotation");
        assertTrue(Pdf.class.isAnnotationPresent(lombok.AllArgsConstructor.class), "Pdf should have @AllArgsConstructor annotation");
    }

    @Test
    void pdfClass_shouldHaveCorrectFieldTypes() throws NoSuchFieldException {
        // Assert
        var idField = Pdf.class.getDeclaredField("id");
        assertEquals(Long.class, idField.getType(), "id field should be of type Long");

        var nameField = Pdf.class.getDeclaredField("name");
        assertEquals(String.class, nameField.getType(), "name field should be of type String");

        var contentField = Pdf.class.getDeclaredField("content");
        assertEquals(String.class, contentField.getType(), "content field should be of type String");
    }
}
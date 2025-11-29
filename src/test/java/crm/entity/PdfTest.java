package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class PdfTest {

    private Pdf pdf;

    @BeforeEach
    void setUp() {
        pdf = new Pdf();
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Pdf newPdf = new Pdf();

        // Assert
        assertNotNull(newPdf);
        assertNull(newPdf.getId());
        assertNull(newPdf.getName());
        assertNull(newPdf.getContent());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String name = "test.pdf";
        String content = "PDF content";

        // Act
        Pdf pdf = new Pdf(id, name, content);

        // Assert
        assertEquals(id, pdf.getId());
        assertEquals(name, pdf.getName());
        assertEquals(content, pdf.getContent());
    }

    @Test
    void testBuilder() {
        // Act
        Pdf pdf = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("PDF content")
                .build();

        // Assert
        assertEquals(1L, pdf.getId());
        assertEquals("test.pdf", pdf.getName());
        assertEquals("PDF content", pdf.getContent());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        pdf.setId(expectedId);

        // Assert
        assertEquals(expectedId, pdf.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "document.pdf";

        // Act
        pdf.setName(expectedName);

        // Assert
        assertEquals(expectedName, pdf.getName());
    }

    @Test
    void testSetAndGetContent() {
        // Arrange
        String expectedContent = "This is the PDF content";

        // Act
        pdf.setContent(expectedContent);

        // Assert
        assertEquals(expectedContent, pdf.getContent());
    }

    @Test
    void testSetIdWithNull() {
        // Act
        pdf.setId(null);

        // Assert
        assertNull(pdf.getId());
    }

    @Test
    void testSetIdWithZero() {
        // Act
        pdf.setId(0L);

        // Assert
        assertEquals(0L, pdf.getId());
    }

    @Test
    void testSetIdWithNegative() {
        // Act
        pdf.setId(-1L);

        // Assert
        assertEquals(-1L, pdf.getId());
    }

    @Test
    void testSetIdWithLargeValue() {
        // Arrange
        Long largeId = Long.MAX_VALUE;

        // Act
        pdf.setId(largeId);

        // Assert
        assertEquals(largeId, pdf.getId());
    }

    @Test
    void testSetNameWithNull() {
        // Act
        pdf.setName(null);

        // Assert
        assertNull(pdf.getName());
    }

    @Test
    void testSetNameWithEmpty() {
        // Act
        pdf.setName("");

        // Assert
        assertEquals("", pdf.getName());
    }

    @Test
    void testSetNameWithShortString() {
        // Act
        pdf.setName("a");

        // Assert
        assertEquals("a", pdf.getName());
    }

    @Test
    void testSetNameWithMinValidLength() {
        // Act
        pdf.setName("ab");

        // Assert
        assertEquals("ab", pdf.getName());
    }

    @Test
    void testSetNameWithLongString() {
        // Arrange
        String longName = "A".repeat(1000) + ".pdf";

        // Act
        pdf.setName(longName);

        // Assert
        assertEquals(longName, pdf.getName());
    }

    @Test
    void testSetNameWithSpecialCharacters() {
        // Arrange
        String specialName = "document@#$%^&*().pdf";

        // Act
        pdf.setName(specialName);

        // Assert
        assertEquals(specialName, pdf.getName());
    }

    @Test
    void testSetNameWithUnicodeCharacters() {
        // Arrange
        String unicodeName = "документ.pdf";

        // Act
        pdf.setName(unicodeName);

        // Assert
        assertEquals(unicodeName, pdf.getName());
    }

    @Test
    void testSetContentWithNull() {
        // Act
        pdf.setContent(null);

        // Assert
        assertNull(pdf.getContent());
    }

    @Test
    void testSetContentWithEmpty() {
        // Act
        pdf.setContent("");

        // Assert
        assertEquals("", pdf.getContent());
    }

    @Test
    void testSetContentWithLargeString() {
        // Arrange
        String largeContent = "Content ".repeat(10000);

        // Act
        pdf.setContent(largeContent);

        // Assert
        assertEquals(largeContent, pdf.getContent());
    }

    @Test
    void testSetContentWithSpecialCharacters() {
        // Arrange
        String specialContent = "Content with special chars: @#$%^&*()";

        // Act
        pdf.setContent(specialContent);

        // Assert
        assertEquals(specialContent, pdf.getContent());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Pdf pdf1 = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("content")
                .build();

        Pdf pdf2 = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("content")
                .build();

        // Act & Assert
        assertEquals(pdf1, pdf2);
        assertEquals(pdf1.hashCode(), pdf2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        Pdf pdf1 = Pdf.builder()
                .id(1L)
                .name("test1.pdf")
                .build();

        Pdf pdf2 = Pdf.builder()
                .id(2L)
                .name("test2.pdf")
                .build();

        // Act & Assert
        assertNotEquals(pdf1, pdf2);
    }

    @Test
    void testNotEqualsWithSameIdDifferentName() {
        // Arrange
        Pdf pdf1 = Pdf.builder()
                .id(1L)
                .name("test1.pdf")
                .build();

        Pdf pdf2 = Pdf.builder()
                .id(1L)
                .name("test2.pdf")
                .build();

        // Act & Assert
        assertNotEquals(pdf1, pdf2);
    }

    @Test
    void testToString() {
        // Arrange
        pdf.setId(1L);
        pdf.setName("document.pdf");
        pdf.setContent("PDF content");

        // Act
        String result = pdf.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("document.pdf"));
        assertTrue(result.contains("PDF content"));
    }

    @Test
    void testToStringWithNullValues() {
        // Act
        String result = pdf.toString();

        // Assert
        assertNotNull(result);
    }

    @Test
    void testAnnotationsPresent() {
        // Act & Assert
        assertTrue(Pdf.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        assertTrue(Pdf.class.isAnnotationPresent(lombok.Data.class));
        assertTrue(Pdf.class.isAnnotationPresent(lombok.Builder.class));
        assertTrue(Pdf.class.isAnnotationPresent(lombok.NoArgsConstructor.class));
        assertTrue(Pdf.class.isAnnotationPresent(lombok.AllArgsConstructor.class));
    }

    @Test
    void testEntityName() {
        // Act
        jakarta.persistence.Entity entityAnnotation = Pdf.class.getAnnotation(jakarta.persistence.Entity.class);

        // Assert
        assertNotNull(entityAnnotation);
        assertEquals("pdf", entityAnnotation.name());
    }

    @Test
    void testIdFieldAnnotations() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field idField = Pdf.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Id.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.GeneratedValue.class));
    }

    @Test
    void testNameFieldAnnotations() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field nameField = Pdf.class.getDeclaredField("name");

        // Assert
        assertTrue(nameField.isAnnotationPresent(jakarta.persistence.Column.class));
        assertTrue(nameField.isAnnotationPresent(jakarta.validation.constraints.Size.class));
    }

    @Test
    void testContentFieldTransientAnnotation() throws NoSuchFieldException {
        // Act
        java.lang.reflect.Field contentField = Pdf.class.getDeclaredField("content");

        // Assert
        assertTrue(contentField.isAnnotationPresent(jakarta.persistence.Transient.class));
    }

    @Test
    void testBuilderPattern() {
        // Act
        Pdf.PdfBuilder builder = Pdf.builder();

        // Assert
        assertNotNull(builder);
        assertNotNull(builder.build());
    }
}
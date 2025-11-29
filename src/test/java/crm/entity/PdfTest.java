package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PdfTest {

    private Pdf pdf;
    private Validator validator;

    @BeforeEach
    void setUp() {
        pdf = new Pdf();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
        String name = "test-document.pdf";
        String content = "PDF content here";

        // Act
        Pdf newPdf = new Pdf(id, name, content);

        // Assert
        assertEquals(id, newPdf.getId());
        assertEquals(name, newPdf.getName());
        assertEquals(content, newPdf.getContent());
    }

    @Test
    void testBuilderPattern() {
        // Act
        Pdf builtPdf = Pdf.builder()
                .id(1L)
                .name("builder-document.pdf")
                .content("Builder PDF content")
                .build();

        // Assert
        assertEquals(1L, builtPdf.getId());
        assertEquals("builder-document.pdf", builtPdf.getName());
        assertEquals("Builder PDF content", builtPdf.getContent());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 100L;

        // Act
        pdf.setId(expectedId);

        // Assert
        assertEquals(expectedId, pdf.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "sample-document.pdf";

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
    void testSetNameWithNull() {
        // Act
        pdf.setName(null);

        // Assert
        assertNull(pdf.getName());
    }

    @Test
    void testSetContentWithNull() {
        // Act
        pdf.setContent(null);

        // Assert
        assertNull(pdf.getContent());
    }

    @Test
    void testSetIdWithDifferentValues() {
        // Test with various ID values
        Long[] testIds = {0L, 1L, 100L, 999L, Long.MAX_VALUE};

        for (Long testId : testIds) {
            // Act
            pdf.setId(testId);

            // Assert
            assertEquals(testId, pdf.getId());
        }
    }

    @Test
    void testSetNameWithDifferentValues() {
        // Arrange
        String[] testNames = {
            "document.pdf", "report_2023.pdf", "invoice-001.pdf",
            "user-manual.pdf", "presentation.pdf"
        };

        for (String testName : testNames) {
            // Act
            pdf.setName(testName);

            // Assert
            assertEquals(testName, pdf.getName());
        }
    }

    @Test
    void testNameSizeValidation() {
        // Arrange
        pdf.setName("A"); // Only 1 character, should fail validation

        // Act
        Set<ConstraintViolation<Pdf>> violations = validator.validate(pdf);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testValidNameSize() {
        // Arrange
        pdf.setName("valid-name.pdf"); // More than 2 characters

        // Act
        Set<ConstraintViolation<Pdf>> violations = validator.validate(pdf);

        // Assert
        assertTrue(violations.stream().noneMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testNameMinimumLength() {
        // Arrange
        pdf.setName("AB"); // Exactly 2 characters, should pass validation

        // Act
        Set<ConstraintViolation<Pdf>> violations = validator.validate(pdf);

        // Assert
        assertTrue(violations.stream().noneMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testSetNameWithEmptyString() {
        // Arrange
        String emptyName = "";

        // Act
        pdf.setName(emptyName);

        // Assert
        assertEquals(emptyName, pdf.getName());
        assertTrue(pdf.getName().isEmpty());
    }

    @Test
    void testSetContentWithEmptyString() {
        // Arrange
        String emptyContent = "";

        // Act
        pdf.setContent(emptyContent);

        // Assert
        assertEquals(emptyContent, pdf.getContent());
        assertTrue(pdf.getContent().isEmpty());
    }

    @Test
    void testSetContentWithLargeString() {
        // Arrange
        String largeContent = "A".repeat(10000);

        // Act
        pdf.setContent(largeContent);

        // Assert
        assertEquals(largeContent, pdf.getContent());
        assertEquals(10000, pdf.getContent().length());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Pdf pdf1 = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("test content")
                .build();

        Pdf pdf2 = Pdf.builder()
                .id(1L)
                .name("test.pdf")
                .content("test content")
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
                .content("content1")
                .build();

        Pdf pdf2 = Pdf.builder()
                .id(2L)
                .name("test2.pdf")
                .content("content2")
                .build();

        // Act & Assert
        assertNotEquals(pdf1, pdf2);
    }

    @Test
    void testToString() {
        // Arrange
        pdf.setId(1L);
        pdf.setName("test.pdf");
        pdf.setContent("test content");

        // Act
        String toString = pdf.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id"));
        assertTrue(toString.contains("name"));
        assertTrue(toString.contains("content"));
    }

    @Test
    void testLombokGeneratedMethods() {
        // Test that Lombok @Data generates equals, hashCode, and toString
        Pdf pdf1 = new Pdf();
        Pdf pdf2 = new Pdf();

        pdf1.setId(10L);
        pdf1.setName("TEST_PDF.pdf");
        pdf1.setContent("Test content");

        pdf2.setId(10L);
        pdf2.setName("TEST_PDF.pdf");
        pdf2.setContent("Test content");

        // Assert equals and hashCode work
        assertEquals(pdf1, pdf2);
        assertEquals(pdf1.hashCode(), pdf2.hashCode());

        // Assert toString works
        String str = pdf1.toString();
        assertNotNull(str);
        assertTrue(str.length() > 0);
    }

    @Test
    void testPdfNameWithSpecialCharacters() {
        // Arrange
        String[] specialNames = {
            "document-2023.pdf", "report_final.pdf", "invoice#123.pdf",
            "user.manual.pdf", "presentation (1).pdf"
        };

        for (String specialName : specialNames) {
            // Act
            pdf.setName(specialName);

            // Assert
            assertEquals(specialName, pdf.getName());
        }
    }

    @Test
    void testIdBoundaryValues() {
        // Test with boundary values
        Long[] boundaryIds = {Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE};

        for (Long boundaryId : boundaryIds) {
            // Act
            pdf.setId(boundaryId);

            // Assert
            assertEquals(boundaryId, pdf.getId());
        }
    }

    @Test
    void testContentWithSpecialCharacters() {
        // Arrange
        String specialContent = "Content with special chars: !@#$%^&*()_+-={}[]|\\:;\"'<>,.?/";

        // Act
        pdf.setContent(specialContent);

        // Assert
        assertEquals(specialContent, pdf.getContent());
    }

    @Test
    void testContentWithNewlines() {
        // Arrange
        String contentWithNewlines = "Line 1\nLine 2\nLine 3\rCarriage return\r\nBoth";

        // Act
        pdf.setContent(contentWithNewlines);

        // Assert
        assertEquals(contentWithNewlines, pdf.getContent());
        assertTrue(pdf.getContent().contains("\n"));
        assertTrue(pdf.getContent().contains("\r"));
    }

    @Test
    void testMultiplePropertiesUpdate() {
        // Test updating multiple properties in sequence
        Long[] ids = {1L, 2L, 3L};
        String[] names = {"pdf1.pdf", "pdf2.pdf", "pdf3.pdf"};
        String[] contents = {"content1", "content2", "content3"};

        for (int i = 0; i < ids.length; i++) {
            // Act
            pdf.setId(ids[i]);
            pdf.setName(names[i]);
            pdf.setContent(contents[i]);

            // Assert
            assertEquals(ids[i], pdf.getId());
            assertEquals(names[i], pdf.getName());
            assertEquals(contents[i], pdf.getContent());
        }
    }

    @Test
    void testResetToNull() {
        // Arrange - First set values
        pdf.setId(100L);
        pdf.setName("initial.pdf");
        pdf.setContent("initial content");

        // Act - Reset to null
        pdf.setId(null);
        pdf.setName(null);
        pdf.setContent(null);

        // Assert
        assertNull(pdf.getId());
        assertNull(pdf.getName());
        assertNull(pdf.getContent());
    }
}
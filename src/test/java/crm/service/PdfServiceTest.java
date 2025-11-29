package crm.service;

import crm.entity.Pdf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PdfServiceTest {

    @Test
    void testPdfServiceInterface() {
        assertTrue(PdfService.class.isInterface());
    }

    @Test
    void testFindByNameMethodExists() {
        assertDoesNotThrow(() -> {
            PdfService.class.getDeclaredMethod("findByName", String.class);
        });
    }

    @Test
    void testSavePdfMethodExists() {
        assertDoesNotThrow(() -> {
            PdfService.class.getDeclaredMethod("savePdf", Pdf.class);
        });
    }

    @Test
    void testFindByNameReturnsCorrectType() throws Exception {
        var method = PdfService.class.getDeclaredMethod("findByName", String.class);
        assertEquals(Pdf.class, method.getReturnType());
    }

    @Test
    void testSavePdfReturnsVoid() throws Exception {
        var method = PdfService.class.getDeclaredMethod("savePdf", Pdf.class);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    void testInterfaceHasCorrectNumberOfMethods() {
        assertEquals(2, PdfService.class.getDeclaredMethods().length);
    }

    @Test
    void testInterfaceIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(PdfService.class.getModifiers()));
    }

    @Test
    void testInterfaceIsAbstract() {
        assertTrue(java.lang.reflect.Modifier.isAbstract(PdfService.class.getModifiers()));
    }

    @Test
    void testAllMethodsArePublicAbstract() throws Exception {
        var methods = PdfService.class.getDeclaredMethods();
        for (var method : methods) {
            assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
        }
    }

    @Test
    void testMethodSignatures() throws NoSuchMethodException {
        var findByNameMethod = PdfService.class.getDeclaredMethod("findByName", String.class);
        var savePdfMethod = PdfService.class.getDeclaredMethod("savePdf", Pdf.class);

        assertNotNull(findByNameMethod);
        assertNotNull(savePdfMethod);

        assertEquals("findByName", findByNameMethod.getName());
        assertEquals("savePdf", savePdfMethod.getName());
    }

    @Test
    void testMethodParameterTypes() throws NoSuchMethodException {
        var findByNameMethod = PdfService.class.getDeclaredMethod("findByName", String.class);
        var savePdfMethod = PdfService.class.getDeclaredMethod("savePdf", Pdf.class);

        assertArrayEquals(new Class<?>[]{String.class}, findByNameMethod.getParameterTypes());
        assertArrayEquals(new Class<?>[]{Pdf.class}, savePdfMethod.getParameterTypes());
    }
}
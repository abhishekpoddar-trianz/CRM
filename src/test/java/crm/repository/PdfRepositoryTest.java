package crm.repository;

import crm.entity.Pdf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PdfRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PdfRepository pdfRepository;

    @Test
    void testFindByName() {
        Pdf pdf = new Pdf();
        pdf.setName("TestPDF");
        entityManager.persist(pdf);
        entityManager.flush();

        Pdf found = pdfRepository.findByName("TestPDF");
        assertNotNull(found);
        assertEquals("TestPDF", found.getName());
    }

    @Test
    void testFindByNameNotFound() {
        Pdf found = pdfRepository.findByName("NonExistentPDF");
        assertNull(found);
    }

    @Test
    void testSavePdf() {
        Pdf pdf = new Pdf();
        pdf.setName("NewPDF");
        Pdf saved = pdfRepository.save(pdf);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("NewPDF", saved.getName());
    }

    @Test
    void testFindById() {
        Pdf pdf = new Pdf();
        pdf.setName("FindByIdPDF");
        entityManager.persist(pdf);
        entityManager.flush();

        Pdf found = pdfRepository.findById(pdf.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("FindByIdPDF", found.getName());
    }

    @Test
    void testDeletePdf() {
        Pdf pdf = new Pdf();
        pdf.setName("DeletePDF");
        entityManager.persist(pdf);
        entityManager.flush();

        pdfRepository.delete(pdf);
        Pdf found = pdfRepository.findByName("DeletePDF");
        assertNull(found);
    }
}

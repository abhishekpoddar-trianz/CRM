package crm.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import crm.entity.Pdf;
import crm.service.PdfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@Slf4j
public class PdfController {

    private PdfService pdfService;

    @Value("${aws.s3.bucket.name:crm-pdfs}")
    private String s3BucketName;

    @Value("${aws.region:us-east-1}")
    private String awsRegion;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    private ByteArrayOutputStream generateSamplePdf(String fileName, String text) throws DocumentException, IOException {
        if (!fileName.endsWith(".pdf")) {
            fileName += ".pdf";
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();
        Paragraph paragraph = new Paragraph(text);
        document.add(paragraph);
        document.close();

        log.info("Generated PDF in memory for cloud storage: {}", fileName);
        return baos;
    }

    @GetMapping("/pdf-generator")
    public String pdfGenerator(Model model) {
        model.addAttribute("pdf", new Pdf());
        return "pdf/generator";
    }

    @PostMapping("/pdf-generator")
    public String generatePdf(@Valid Pdf pdf, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/pdf-generator";
        } else {
            try {
                ByteArrayOutputStream pdfStream = generateSamplePdf(pdf.getName(), pdf.getContent());
                // TODO: Upload to AWS S3 bucket instead of local storage
                // AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(awsRegion).build();
                // s3Client.putObject(s3BucketName, pdf.getName(), new ByteArrayInputStream(pdfStream.toByteArray()), null);
                pdfService.savePdf(pdf);
                log.info("PDF generated and ready for cloud storage upload: {}", pdf.getName());
            } catch (DocumentException e) {
                log.error("Error generating PDF document: {}", e.getMessage(), e);
            } catch (IOException e) {
                log.error("Error handling PDF stream: {}", e.getMessage(), e);
            }
            return "pdf/success";
        }
    }

    @GetMapping("/pdf-download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadPdf(@Value("fileName") String fileName) {
        try {
            // TODO: Retrieve from AWS S3 instead of generating new
            // For now, this generates a sample PDF. In production, retrieve from S3:
            // AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(awsRegion).build();
            // S3Object s3Object = s3Client.getObject(s3BucketName, fileName);
            // byte[] content = IOUtils.toByteArray(s3Object.getObjectContent());

            ByteArrayOutputStream pdfStream = generateSamplePdf(fileName, "Sample content for download");
            byte[] content = pdfStream.toByteArray();

            ByteArrayResource resource = new ByteArrayResource(content);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(content.length)
                    .body(resource);
        } catch (Exception e) {
            log.error("Error downloading PDF: {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

}

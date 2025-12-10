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
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import org.slf4j.MDC;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j
public class PdfController {

    private final PdfService pdfService;
    private final S3Client s3Client;

    @Value("${aws.s3.bucket.name:${S3_BUCKET_NAME:crm-pdf-storage}}")
    private String bucketName;

    public PdfController(PdfService pdfService, S3Client s3Client) {
        this.pdfService = pdfService;
        this.s3Client = s3Client;
    }

    private String generateSamplePdf(String fileName, String text) throws IOException, DocumentException {
        if (!fileName.endsWith(".pdf")) {
            fileName += ".pdf";
        }

        String correlationId = MDC.get("correlationId");
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
            MDC.put("correlationId", correlationId);
        }

        log.info("Generating PDF file: {} with correlation ID: {}", fileName, correlationId);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();
            Paragraph paragraph = new Paragraph(text);
            document.add(paragraph);
            document.close();

            // Upload to S3 instead of local file system
            String s3Key = "pdfs/" + correlationId + "/" + fileName;
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType("application/pdf")
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(baos.toByteArray()));
            log.info("PDF uploaded to S3 successfully: s3://{}/{}", bucketName, s3Key);

            return s3Key;
        }
    }

    @GetMapping("/pdf-generator")
    public String pdfGenerator(Model model) {
        model.addAttribute("pdf", new Pdf());
        return "pdf/generator";
    }

    @PostMapping("/pdf-generator")
    public String generatePdf(@Valid Pdf pdf, BindingResult bindingResult, Model model) {
        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);

        log.info("Processing PDF generation request with correlation ID: {}", correlationId);

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors in PDF generation request: {}", bindingResult.getAllErrors());
            return "redirect:/pdf-generator";
        } else {
            try {
                String s3Key = generateSamplePdf(pdf.getName(), pdf.getContent());
                pdf.setStorageLocation("s3://" + bucketName + "/" + s3Key);
                pdfService.savePdf(pdf);

                model.addAttribute("s3Location", s3Key);
                log.info("PDF generation completed successfully for correlation ID: {}", correlationId);

            } catch (IOException e) {
                log.error("IO error during PDF generation for correlation ID: {}: {}", correlationId, e.getMessage());
                model.addAttribute("error", "Failed to generate PDF: " + e.getMessage());
                return "pdf/error";
            } catch (DocumentException e) {
                log.error("Document error during PDF generation for correlation ID: {}: {}", correlationId, e.getMessage());
                model.addAttribute("error", "Failed to create PDF document: " + e.getMessage());
                return "pdf/error";
            } catch (Exception e) {
                log.error("Unexpected error during PDF generation for correlation ID: {}: {}", correlationId, e.getMessage());
                model.addAttribute("error", "An unexpected error occurred");
                return "pdf/error";
            } finally {
                MDC.clear();
            }
            return "pdf/success";
        }
    }

}

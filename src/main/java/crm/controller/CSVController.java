package crm.controller;

import crm.entity.Customer;
import crm.service.CustomerService;
import crm.utils.WriteCsvToResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class CSVController {

    private final CustomerService customerService;

    @Value("${app.csv.max-records:${CSV_MAX_RECORDS:10000}}")
    private int maxRecords;

    @Value("${app.csv.page-size:${CSV_PAGE_SIZE:1000}}")
    private int pageSize;

    public CSVController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/customers", produces = "text/csv")
    public void findCustomers(
            HttpServletResponse httpServletResponse,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size) throws IOException {

        // Enforce maximum page size to prevent memory issues
        size = Math.min(size, pageSize);

        log.info("Exporting customers CSV - Page: {}, Size: {}", page, size);

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Customer> customerPage = customerService.listCustomersPaginated(pageable);

            // Check if total elements exceed safe limits
            if (customerPage.getTotalElements() > maxRecords) {
                log.warn("Customer export exceeds maximum records: {} > {}",
                        customerPage.getTotalElements(), maxRecords);
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.getWriter().write("Error: Dataset too large. Use pagination or reduce filter criteria.");
                return;
            }

            List<Customer> customers = customerPage.getContent();
            WriteCsvToResponse.writeCustomers(httpServletResponse.getWriter(), customers);

            log.info("Successfully exported {} customers", customers.size());

        } catch (Exception e) {
            log.error("Error exporting customers CSV: {}", e.getMessage());
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write("Error: Failed to export customers data");
        }
    }

    @GetMapping(value = "/customers/{id}", produces = "text/csv")
    public void findCustomer(@PathVariable Long id, HttpServletResponse httpServletResponse) throws IOException {
        log.info("Exporting customer CSV for ID: {}", id);

        try {
            Customer customer = customerService.showCustomer(id);
            if (customer == null) {
                httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                httpServletResponse.getWriter().write("Error: Customer not found with ID: " + id);
                return;
            }

            WriteCsvToResponse.writeCustomer(httpServletResponse.getWriter(), customer);
            log.info("Successfully exported customer with ID: {}", id);

        } catch (Exception e) {
            log.error("Error exporting customer CSV for ID {}: {}", id, e.getMessage());
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            httpServletResponse.getWriter().write("Error: Failed to export customer data");
        }
    }

//    @GetMapping("/show-import")
//    public String showImportCsvSite() {
//        return "csv/import";
//    }

    /*@GetMapping("/import")
    public String processRequestImportCsv(Model model) {
        File document = ReadDataUtils.ReadFile("Select CSV file", null, "Only CSV Files", "csv");
//        System.out.println(document.getName());

            CSVReader reader;
            List<String[]> data = new ArrayList<>();
            try {
                reader = new CSVReader(new FileReader(document));
                String[] line;
                while ((line = reader.readNext()) != null) {
//                    System.out.println(line[1] + "\t" + line[2]);
                    data.add(line);
//                    if(line[1].equals("QUICK SUB")){
//                        System.out.println(line[0] + "\t" + line[1] + "\t" + line[2]);
//                    }
                }
                model.addAttribute("data", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
		*//*System.out.println(data.get(0)[1] + "\t" + data.get(0)[2]);
		System.out.println(data.get(1)[1] + "\t" + data.get(1)[2]);*//*

        return "csv/show";
    }*/

//    @GetMapping("/show")
//    public String showPageWithCsvImported(@ModelAttribute List<String[]> data) {
//        data.
//        return "csv/show";
//    }

}

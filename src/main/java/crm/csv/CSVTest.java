package crm.csv;

import com.opencsv.CSVReader;
import crm.utils.ReadDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CSVTest {

    @Autowired
    private ReadDataUtils readDataUtils;

    /**
     * Cloud-ready CSV processing method
     * Replaces desktop file access with classpath resource reading
     */
    public List<String[]> processCSVFromClasspath(String csvFileName) {
        List<String[]> data = new ArrayList<>();

        try (InputStream inputStream = readDataUtils.readFileFromClasspath(csvFileName)) {
            if (inputStream == null) {
                log.warn("CSV file not found in classpath: {}", csvFileName);
                return data;
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
                String[] line;
                int rowCount = 0;

                while ((line = reader.readNext()) != null) {
                    data.add(line);
                    rowCount++;

                    // Use structured logging instead of System.out.println
                    if (line.length > 1 && "QUICK SUB".equals(line[1])) {
                        log.info("Found QUICK SUB record: row={}, col0={}, col1={}, col2={}",
                                rowCount,
                                line.length > 0 ? line[0] : "N/A",
                                line.length > 1 ? line[1] : "N/A",
                                line.length > 2 ? line[2] : "N/A");
                    }
                }

                log.info("Successfully processed CSV file: {} with {} rows", csvFileName, rowCount);

            }
        } catch (IOException e) {
            log.error("Error processing CSV file: {}", csvFileName, e);
        }

        return data;
    }

    /**
     * Process CSV data with structured logging
     */
    public void analyzeCSVData(List<String[]> data) {
        if (data.isEmpty()) {
            log.warn("No data to analyze");
            return;
        }

        log.info("CSV Analysis Results:");
        log.info("Total rows: {}", data.size());

        if (!data.isEmpty() && data.get(0).length > 1) {
            String[] firstRow = data.get(0);
            log.info("First row sample: col1={}, col2={}",
                    firstRow.length > 1 ? firstRow[1] : "N/A",
                    firstRow.length > 2 ? firstRow[2] : "N/A");
        }

        if (data.size() > 1) {
            String[] secondRow = data.get(1);
            log.info("Second row sample: col1={}, col2={}",
                    secondRow.length > 1 ? secondRow[1] : "N/A",
                    secondRow.length > 2 ? secondRow[2] : "N/A");
        }
    }

    /**
     * Legacy main method for backward compatibility
     * Now uses cloud-ready methods
     */
    public static void main(String[] args) {
        log.info("Starting CSV processing in cloud-ready mode");

        // In cloud environments, this would be replaced with:
        // 1. Web endpoint to upload CSV files
        // 2. Processing files from S3 bucket
        // 3. Scheduled processing of files from cloud storage

        CSVTest csvTest = new CSVTest();
        csvTest.readDataUtils = new ReadDataUtils();

        // Example: process a CSV file from classpath
        List<String[]> data = csvTest.processCSVFromClasspath("sample.csv");
        csvTest.analyzeCSVData(data);

        log.info("CSV processing completed");
    }
}

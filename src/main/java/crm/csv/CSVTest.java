package crm.csv;

import com.opencsv.CSVReader;
import crm.utils.ReadDataUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * NOTE: This test class uses GUI-based file selection which is incompatible with containerized environments.
 * For containerized deployments, use REST endpoints with MultipartFile uploads instead.
 * This class should only be used for local development testing with a GUI environment.
 */
public class CSVTest {

    public static void main(String[] args) {
        // NOTE: The old GUI-based ReadFile method is deprecated for container compatibility
        // For container environments, provide the file path directly or use file uploads via REST API
        if (args.length == 0) {
            System.err.println("ERROR: GUI-based file selection is not supported in containers.");
            System.err.println("Usage: java CSVTest <path-to-csv-file>");
            System.err.println("Example: java CSVTest /data/import/customers.csv");
            System.exit(1);
        }

        File document = ReadDataUtils.ReadFile(new File(args[0]), "csv");
        if (document == null) {
            System.err.println("Invalid CSV file provided");
            System.exit(1);
        }
//        System.out.println(document.getName());

        CSVReader reader;
        List<Object[]> data = new ArrayList<>();
        try {
            reader = new CSVReader(new FileReader(document));
            String[] line;
            while ((line = reader.readNext()) != null) {
//                System.out.println(line[1] + "\t" + line[2]);
                data.add(line);
                if(line[1].equals("QUICK SUB")){
                    System.out.println(line[0] + "\t" + line[1] + "\t" + line[2]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		/*System.out.println(data.get(0)[1] + "\t" + data.get(0)[2]);
		System.out.println(data.get(1)[1] + "\t" + data.get(1)[2]);*/
    }

}

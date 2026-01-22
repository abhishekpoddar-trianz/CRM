package crm.utils;

import java.io.File;

/**
 * Utility class for file operations in containerized environments.
 * NOTE: GUI-based file selection (JFileChooser) has been removed as it's incompatible with headless containers.
 * For web applications, use MultipartFile uploads via REST endpoints instead.
 */
public class ReadDataUtils {

    /**
     * Validates if a file exists and has the correct extension.
     * This method replaces the GUI-based file chooser for container compatibility.
     *
     * @param file The file to validate
     * @param fileExtension Expected file extensions
     * @return The validated file or null if invalid
     */
    public static File ReadFile(File file, String... fileExtension) {
        if (file == null || !file.exists()) {
            System.err.println("File does not exist or is null");
            return null;
        }

        if (fileExtension != null && fileExtension.length > 0) {
            String fileName = file.getName().toLowerCase();
            boolean validExtension = false;
            for (String ext : fileExtension) {
                if (fileName.endsWith("." + ext.toLowerCase())) {
                    validExtension = true;
                    break;
                }
            }
            if (!validExtension) {
                System.err.println("Invalid file extension. Expected: " + String.join(", ", fileExtension));
                return null;
            }
        }

        System.out.println("File validated: " + file.getName());
        return file;
    }

    /**
     * @deprecated This method signature used JFileChooser which is incompatible with containerized environments.
     * Use ReadFile(File file, String... fileExtension) instead and pass files from MultipartFile uploads.
     */
    @Deprecated
    public static File ReadFile(String dialogMessage, Object parent, String fileExtensionDescription,
                                String... fileExtension) {
        throw new UnsupportedOperationException(
            "GUI-based file selection is not supported in containerized environments. " +
            "Use REST endpoints with MultipartFile for file uploads instead."
        );
    }

}

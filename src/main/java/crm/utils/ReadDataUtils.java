package crm.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ReadDataUtils {

    public static File ReadFile(String dialogMEssage, JFrame parent, String fileExtensionDescription,
                                String... fileExtension) {
        // JFileChooser chooser = new JFileChooser(); // Commented out - not compatible with headless containers
        // FileNameExtensionFilter filter = new FileNameExtensionFilter(fileExtensionDescription, fileExtension);
        // chooser.setFileFilter(filter);
        // int returnVal = chooser.showOpenDialog(parent);
        // if (returnVal == JFileChooser.APPROVE_OPTION) {
        //     System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
        //     return chooser.getSelectedFile();
        // }
        // return null;
        throw new UnsupportedOperationException("GUI file chooser not supported in containerized environment");
    }

}

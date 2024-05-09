package dev.anes.core.util;

import java.io.File;



import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class FileUtil {
    // public static File getFileFromRegistry(String key) {
    //     Preferences prefs = Preferences.userNodeForPackage(App.class);
    //     String filePath = prefs.get(key, null);
    //     return filePath != null ? new File(filePath) : null;

    // }

    // public static void setFileRegistry(File file, String key) {
    //     Preferences prefs = Preferences.userNodeForPackage(App.class);
    //     if (file != null)
    //         prefs.put(key, file.getPath());
    //     else
    //         prefs.remove(key);
    // }

    private static FileChooser fileChooser(ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(filters);
        return fc;
    }

    public static ExtensionFilter registerFilter(String description, String... extenions) {
        return new ExtensionFilter(description, extenions);
    }

    public static File openDialog(Window owner, ExtensionFilter... filters) {
        FileChooser fc = fileChooser(filters);

        return fc.showOpenDialog(owner);
    }

    public static File saveDialog(Window owner, ExtensionFilter... filters) {
        FileChooser fc = new FileChooser();

        return fc.showSaveDialog(owner);
    }

}

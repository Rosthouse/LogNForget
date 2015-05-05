/**
 * $HeadURL$
 */
package rosthouse.lognforget.shortcut.loggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import rosthouse.lognforget.shortcut.ShortCutListenerController;

/**
 * Logs all text to a text file.
 *
 * @author Rosthouse <rosthouse@gmail.com>
 * @created 12.03.2015 14:49:06
 */
public class FileLogger implements Logger {

    @Override
    public void logText(String text) {
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "log.txt";
        File logDocument = openOrCreateFile(path);
        logTextToFile(text, logDocument);
    }

    void logTextToFile(String text, File logDocument) {
        String formattedDate = getCurrentTimeString();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logDocument, true)))) {
            out.println(String.format("%s - %s", formattedDate, text));
        } catch (IOException e) {
            java.util.logging.Logger.getLogger(ShortCutListenerController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    String getCurrentTimeString() {
        LocalDateTime logTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(LOG_DATE_FORMAT);
        String formattedDate = formatter.format(logTime);
        return formattedDate;
    }
    public static final String LOG_DATE_FORMAT = "dd.MM.yyyy - HH:mm:ss";

    File openOrCreateFile(String logDocumentPath) {
        File logDocument = new File(logDocumentPath);
        if (!logDocument.exists()) {
            try {
                logDocument.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(ShortCutListenerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return logDocument;
    }

}

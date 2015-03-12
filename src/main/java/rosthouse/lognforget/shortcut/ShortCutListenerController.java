package rosthouse.lognforget.shortcut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 * Controlls GUIs created by the fxml file editor.fxml.
 *
 * @author Rosthouse
 * @created 10.03.2015 12:46:54
 */
public class ShortCutListenerController extends Pane implements Initializable {

    @FXML
    private Parent parent;
    @FXML
    private TextField logField;
    private CloseListener closeListener;

    @FXML
    public void onKeyDown(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            closeWindow();
        }
    }

    public void closeWindow() {
        closeListener.closeStage();
    }

    public void writeToLogFile() {
        String logText = logField.getText();
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "log.txt";
        File logDocument = new File(path);
        if (!logDocument.exists()) {
            try {
                logDocument.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ShortCutListenerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LocalDateTime logTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logDocument, true)))) {
            out.println(String.format("%s - %s", formatter.format(logTime), logText));
        } catch (IOException e) {
            Logger.getLogger(ShortCutListenerController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void onEnter() {
        writeToLogFile();
        closeWindow();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logField.requestFocus();
    }

    void setCloseListener(ShortCutListener closeListener) {
        this.closeListener = closeListener;
    }

}

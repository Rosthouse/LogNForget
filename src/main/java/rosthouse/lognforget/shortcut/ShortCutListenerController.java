package rosthouse.lognforget.shortcut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Controlls GUIs created by the fxml file editor.fxml.
 *
 * @author Rosthouse
 * @created 10.03.2015 12:46:54
 */
public class ShortCutListenerController extends Pane implements Initializable {

    @FXML
    private TextField logField;

    @FXML
    public void onKeyTyped(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            closeWindow();
        }
    }

    public void closeWindow() {
        Window window = logField.getScene().getWindow();
        Stage stage = (Stage) window;
        stage.close();
    }

    public void writeToLogFile() {
        String text = logField.getText();
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "log.txt";
        File logDocument = new File(path);
        if (!logDocument.exists()) {
            try {
                logDocument.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ShortCutListenerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logDocument)))) {
            out.println(text);
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

}

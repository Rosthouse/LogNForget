package rosthouse.lognforget.shortcut;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import rosthouse.lognforget.shortcut.loggers.FileLogger;
import rosthouse.lognforget.shortcut.loggers.Logger;

/**
 * Controlls GUIs created by the fxml file editor.fxml.
 *
 * @author Rosthouse
 * @created 10.03.2015 12:46:54
 */
public class ShortCutListenerController extends Pane implements Initializable {

    @FXML
    private StackPane parent;
    @FXML
    private TextField logField;
    private Logger logger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logField.requestFocus();
        this.logger = new FileLogger();
    }

    @FXML
    public void onKeyDown(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            closeWindow();
        }
    }

    public void closeWindow() {
        parent.getScene().getWindow().hide();
    }

    @FXML
    public void onEnter() {
        logger.logText(logField.getText());
        closeWindow();
    }


    void setLogger(Logger logger) {
        this.logger = logger;
    }

}

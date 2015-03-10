package rosthouse.lognforget.shortcut;

import javafx.fxml.FXML;
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
public class ShortCutListenerController extends Pane {

    @FXML
    private TextField logField;

    @FXML
    public void onKeyTyped(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            int i = 1;
        }
    }

}

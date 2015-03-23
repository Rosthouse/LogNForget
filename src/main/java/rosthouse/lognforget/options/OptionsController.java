package rosthouse.lognforget.options;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import rosthouse.lognforget.util.ModKeyMapping;

/**
 * FXML Controller class
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class OptionsController implements Initializable, ChangeListener<Boolean> {

    @FXML
    AnchorPane parent;
    @FXML
    TextField shortCut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shortCut.focusedProperty().addListener(this);
    }

    public void closeWindow() {
        parent.getScene().getWindow().hide();
        shortCut.setEditable(false);
    }

    @FXML
    public void onShortcutFieldKeyPressed(KeyEvent key) {
        ModKeyMapping mapping = null;
        switch (key.getCode()) {
            case WINDOWS:
                mapping = ModKeyMapping.WIN;
                break;
            case ALT:
                mapping = ModKeyMapping.ALT;
                break;
            case ALT_GRAPH:
                mapping = ModKeyMapping.ALT_GR;
                break;
            case CONTROL:
                mapping = ModKeyMapping.CTRL;
                break;
            case SHIFT:
                mapping = ModKeyMapping.SHIFT;
                break;
            default:
                break;
        }
        String text = shortCut.getText();
        if(mapping != null){
            text += mapping.name() + "+";
        } else if(!text.isEmpty()){
            text += key.getText();
            parent.requestFocus();
        } else {
            text = "A shortcut must contain a modifier key (CTRL, ALT, SHIFT, WIN)";
            parent.requestFocus();
        }
        shortCut.setText(text);
    }
    
    

    @FXML
    public void onSave() {
        setOptions();
        closeWindow();
    }

    @FXML
    public void onApply() {
        setOptions();
    }

    @FXML
    public void onCancel() {
        closeWindow();
    }

    private void setOptions() {

    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if(newValue){
            shortCut.clear();
            shortCut.setEditable(true);
        } else {
            shortCut.setEditable(false);
        }
    }
}

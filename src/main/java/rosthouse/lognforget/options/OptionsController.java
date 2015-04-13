package rosthouse.lognforget.options;

import com.sun.xml.internal.ws.util.StringUtils;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import rosthouse.lognforget.Settings;
import rosthouse.lognforget.util.ModKeyMapping;
import rosthouse.lognforget.util.ResourceLoader;

/**
 * FXML Controller class
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class OptionsController implements Initializable, ChangeListener<Boolean> {

    @FXML
    private AnchorPane parent;
    @FXML
    private TextField shortCut;
    @FXML
    private ComboBox<String> cmbAlertSound;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadAlertSounds();
            setUpShortCutTextField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpShortCutTextField() {
        shortCut.setText(Settings.getModKey().name() + "+" + (char) Settings.getShortCutKey());
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
        if (mapping != null) {
            text += mapping.name() + "+";
        } else if (!text.isEmpty()) {
            text += key.getText().toUpperCase();
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

    @FXML
    public void onPlayPressed() {
        String fileName = cmbAlertSound.getValue();
        if (fileName != null && !fileName.isEmpty()) {
            String path = getClass().getResource("/audio/alert/" + fileName).toString();
            AudioClip alert = new AudioClip(path);
            alert.play();
        }

    }

    @FXML
    public void onResetPressed() {
        Settings.resetDefaults();
    }

    private void setOptions() {
        String text = shortCut.getText();
        String[] values = text.split("\\+");
        Settings.setModKey(ModKeyMapping.valueOf(values[0]));
        Settings.setShortCutKey(values[1].trim().charAt(0));
        Settings.setAlertClip(cmbAlertSound.getValue());
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue != oldValue) {
            shortCut.clear();
            shortCut.setEditable(true);
        } else {
            shortCut.setEditable(false);
        }
    }

    private void loadAlertSounds() {
        ObservableList<String> files = FXCollections.observableArrayList();
        try {
            for (String listFile : ResourceLoader.getResourceListing(this.getClass(), "audio/alert/")) {
                if (listFile.endsWith("wav")) {
                    files.add(listFile);
                }
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(OptionsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cmbAlertSound.setItems(files);
        cmbAlertSound.setValue(Settings.getAlertClip());
    }
}

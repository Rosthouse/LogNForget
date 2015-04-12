package rosthouse.lognforget.options;

import java.io.File;
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
    @FXML
    ComboBox<File> cmbAlertSound;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            shortCut.setText(Settings.getModKey().name() + "+" + (char) Settings.getShortCutKey());
            shortCut.focusedProperty().addListener(this);
            loadAlertSounds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
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
    public void onPlayPressed(){
        File alertSound = cmbAlertSound.getValue();
        if(alertSound != null){
            AudioClip clip = new AudioClip(alertSound.getAbsolutePath());
            clip.play();
        }
    }

    private void setOptions() {
        String text = shortCut.getText();
        String[] values = text.split("\\+");
        Settings.setModKey(values[0]);
        Settings.setShortCutKey(values[1].trim().charAt(0));
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            shortCut.clear();
            shortCut.setEditable(true);
        } else {
            shortCut.setEditable(false);
        }
    }

    private void loadAlertSounds() {
        URL alertDirectoryPath = getClass().getResource("/audio/alert/");
        File alertDirectory = null;
        try {
            alertDirectory = new File(alertDirectoryPath.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(OptionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        if(alertDirectory.isDirectory()){
            ObservableList<File> files =     FXCollections.observableArrayList();
            
            for (File listFile : alertDirectory.listFiles()) {
                files.add(listFile);
            }
            cmbAlertSound.setItems(files);
//        }
    }
}

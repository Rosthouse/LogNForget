package rosthouse.lognforget.shortcut;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rosthouse.lognforget.Settings;
import rosthouse.lognforget.util.ModKeyMapping;

/**
 * Listens to a defined shortcut and opens a text input field when that occurs.
 *
 * @author Rosthouse
 * @created 09.03.2015 17:23:05
 */
public class ShortCutListener implements HotkeyListener, PreferenceChangeListener {

    final private JIntellitype instance;
    private static final int SHORTCUT_LISTENER_IDENTIFIER = 1;
    private Stage stage;

    public ShortCutListener() {
        this.instance = JIntellitype.getInstance();
        instance.addHotKeyListener(this);
        Settings.addPreferencesChangedListener(this);
    }

    private void openWindow() {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Editor.fxml"));
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            scene.setFill(new javafx.scene.paint.Color(0, 0, 0, 0));
            stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ShortCutListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onHotKey(int i) {
        if (Platform.isFxApplicationThread()) {
            openWindow();
        } else {
            Platform.runLater(() -> openWindow());
        }
    }

    public void registerHotKeyForLogging(ModKeyMapping modifier, int keycode) {
        instance.registerHotKey(SHORTCUT_LISTENER_IDENTIFIER, modifier.getJIntelitypeKey(), keycode);
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        instance.unregisterHotKey(SHORTCUT_LISTENER_IDENTIFIER);
        ModKeyMapping modKeyMapping = Settings.getModKey();
        char shortCutKey = (char) Settings.getShortCutKey();
        instance.registerHotKey(SHORTCUT_LISTENER_IDENTIFIER, modKeyMapping.getJIntelitypeKey(), shortCutKey);
    }
}

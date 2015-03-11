package rosthouse.lognforget.shortcut;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Listens to a defined shortcut and opens a text input field when that occurs.
 *
 * @author Rosthouse
 * @created 09.03.2015 17:23:05
 */
public class ShortCutListener implements HotkeyListener {

    final private JIntellitype instance;
    private static final int SHORTCUT_LISTENER_IDENTIFIER = 1;

    public ShortCutListener(JIntellitype instance) {
        this.instance = instance;
    }

    private void openWindow() throws IOException {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Editor.fxml"));
        final Parent root = loader.load();
        final Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        final Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void onHotKey(int i) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    openWindow();
                } catch (IOException ex) {
                    Logger.getLogger(ShortCutListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Platform.runLater(runnable);
    }

    public void registerHotKeyForLogging(int modifier, int keycode) {
        instance.registerHotKey(SHORTCUT_LISTENER_IDENTIFIER, modifier, keycode);
    }
}

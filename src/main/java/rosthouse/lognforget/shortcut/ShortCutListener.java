package rosthouse.lognforget.shortcut;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Listens to a defined shortcut and opens a text input field when that occurs.
 *
 * @author Rosthouse
 * @created 09.03.2015 17:23:05
 */
public class ShortCutListener implements HotkeyListener {

    final private JIntellitype instance;
    private static final int SHORTCUT_LISTENER_IDENTIFIER = 1;
    private final FXMLLoader loader;

    public ShortCutListener(JIntellitype instance) throws URISyntaxException {
        this.instance = instance;
        loader = new FXMLLoader();
    }

    @Override
    public void onHotKey(int i) {
        try {
            loader.setLocation(getClass().getResource("/fxml/Editor.fxml"));
            loader.setController(new ShortCutListenerController());
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            final Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(ShortCutListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerHotKeyForLogging(int modifier, int keycode) {
        instance.registerHotKey(SHORTCUT_LISTENER_IDENTIFIER, modifier, keycode);
    }
}

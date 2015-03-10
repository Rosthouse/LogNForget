/**
 * $HeadURL$
 */
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
 * @author jopa
 * @copyright (c) 2007-2015, Wuerth ITensis AG
 * @created 09.03.2015 17:23:05
 *
 * @$Revision$
 *
 * @$LastChangedBy$
 * @$LastChangedDate$
 * @$Id$
 */
public class ShortCutListener implements HotkeyListener {

    final private JIntellitype instance;
    private static final int SHORTCUT_LISTENER_IDENTIFIER = 1;

    public ShortCutListener() throws URISyntaxException {
        instance = JIntellitype.getInstance();
        instance.addHotKeyListener(this);
    }

    @Override
    public void onHotKey(int i) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Editor.fxml"));
            ShortCutListenerController controller = new ShortCutListenerController();
            loader.setController(controller);
            final Parent root = loader.load();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            final Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ShortCutListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerHotKeyForLogging(int modifier, int keycode) {
        instance.registerHotKey(SHORTCUT_LISTENER_IDENTIFIER, modifier, keycode);
    }
}

package rosthouse.lognforget;

import com.melloware.jintellitype.JIntellitype;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.stage.Stage;
import rosthouse.lognforget.shortcut.ShortCutListener;
import rosthouse.lognforget.util.Constants;
import rosthouse.lognforget.util.ModKeyMapping;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Platform.setImplicitExit(false);
        JIntellitype instance = JIntellitype.getInstance();
        initializeShortCutListener(instance);
        Tray tray = new Tray();
    }

    private void initializeShortCutListener(JIntellitype instance) {
        ShortCutListener listener = new ShortCutListener();
        ReminderManager reminder = new ReminderManager();
        listener.setLogEventHandler(reminder);
        listener.registerHotKeyForLogging(Settings.getModKey(), Settings.getShortCutKey());
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

package rosthouse.lognforget;

import com.melloware.jintellitype.JIntellitype;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.stage.Stage;
import rosthouse.lognforget.options.Options;
import rosthouse.lognforget.shortcut.ShortCutListener;

public class MainApp extends Application {

    public static final String LOG_N_FORGET = "LogNForget";

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        JIntellitype instance = JIntellitype.getInstance();
        initializeShortCutListener(instance);
        Tray tray = new Tray();
    }

    private void initializeShortCutListener(JIntellitype instance) {
        ShortCutListener listener = new ShortCutListener();
        listener.registerHotKeyForLogging(JIntellitype.MOD_WIN, 'A');
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

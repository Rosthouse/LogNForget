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

    private static final String LOG_N_FORGET = "LogNForget";

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        JIntellitype instance = JIntellitype.getInstance();
        initializeShortCutListener(instance);
        initializeSystemTray();
    }

    private void initializeShortCutListener(JIntellitype instance) {
        ShortCutListener listener = new ShortCutListener();
        listener.registerHotKeyForLogging(JIntellitype.MOD_WIN, 'A');
    }

    private void initializeSystemTray() {
        if (SystemTray.isSupported()) {
            setTrayIcon();
        }
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

    private void setTrayIcon() {
        SystemTray tray = SystemTray.getSystemTray();
        TrayIcon icon = loadTrayIcon();
        PopupMenu menu = createPopupMenu(tray, icon);
        icon.setPopupMenu(menu);
        try {
            tray.add(icon);
        } catch (AWTException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private TrayIcon loadTrayIcon() {
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/famfamfam/silk/accept.png"));
        TrayIcon icon = new TrayIcon(image, LOG_N_FORGET);
        return icon;
    }

    private PopupMenu createPopupMenu(SystemTray tray, TrayIcon icon) throws HeadlessException {
        PopupMenu menu = new PopupMenu(LOG_N_FORGET);
        MenuItem options = new MenuItem("Options");
        options.addActionListener((e) -> {
            Platform.runLater(() -> {
                try {
                    Options optionsWindow = new Options();
                } catch (IOException ex) {
                    Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });

        MenuItem close = new MenuItem("Exit");
        close.addActionListener((e) -> {
            System.out.println("Ending " + LOG_N_FORGET);
            JIntellitype.getInstance().cleanUp();
            tray.remove(icon);
            Platform.exit();
        });
        menu.add(options);
        menu.add(close);
        return menu;
    }

}

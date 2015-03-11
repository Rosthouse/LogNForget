package rosthouse.lognforget;

import com.melloware.jintellitype.JIntellitype;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.stage.Stage;
import rosthouse.lognforget.shortcut.ShortCutListener;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        JIntellitype instance = JIntellitype.getInstance();
        ShortCutListener listener = new ShortCutListener(instance);
        instance.addHotKeyListener(listener);
        listener.registerHotKeyForLogging(JIntellitype.MOD_WIN, 'C');
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

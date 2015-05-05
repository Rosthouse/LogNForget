package rosthouse.lognforget.shortcut;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import rosthouse.lognforget.Settings;
import rosthouse.lognforget.reminder.LogEvent;
import rosthouse.lognforget.reminder.LogEventHandler;
import static rosthouse.lognforget.util.WindowManager.loadWindow;
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
    private List<LogEventHandler> eventHandlers;

    public ShortCutListener() {
        this.eventHandlers = new ArrayList<LogEventHandler>();
        this.instance = JIntellitype.getInstance();
        instance.addHotKeyListener(this);
        Settings.addPreferencesChangedListener(this);
    }

    private void openWindow() {
        stage = loadWindow("/fxml/Editor.fxml");
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        for (LogEventHandler eventHandler : eventHandlers) {
            stage.addEventHandler(LogEvent.LOG_EVENT, eventHandler);
        }
        stage.show();
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

    public void setLogEventHandler(LogEventHandler logEventHandler) {
        this.eventHandlers.add(logEventHandler);
    }
}

package rosthouse.lognforget;

import rosthouse.lognforget.shortcut.ShortCutListener;
import com.melloware.jintellitype.JIntellitype;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ShortCutListenerTest {

    ShortCutListener listener;

    @Before
    public void setup() throws URISyntaxException {
        listener = new ShortCutListener();
    }

    @Test
    public void testRegisterHotKey() {
        listener.registerHotKeyForLogging(JIntellitype.MOD_WIN, (int) 'A');
    }
}

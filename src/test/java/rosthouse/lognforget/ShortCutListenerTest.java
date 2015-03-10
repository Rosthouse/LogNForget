package rosthouse.lognforget;

import com.melloware.jintellitype.JIntellitype;
import java.net.URISyntaxException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import rosthouse.lognforget.shortcut.ShortCutListener;

@Ignore
public class ShortCutListenerTest {

    ShortCutListener listener;

    @Mock
    JIntellitype mockInstance;

    @Before
    public void setup() throws URISyntaxException {
        listener = new ShortCutListener(mockInstance);
    }

    @Test
    public void testRegisterHotKey() {
        listener.registerHotKeyForLogging(JIntellitype.MOD_WIN, (int) 'A');
    }
}

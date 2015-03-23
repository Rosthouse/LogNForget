package rosthouse.lognforget;

import com.melloware.jintellitype.JIntellitype;
import java.io.IOException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import rosthouse.lognforget.shortcut.ShortCutListener;
import rosthouse.lognforget.util.ModKeyMapping;

@Ignore
public class ShortCutListenerTest {

    ShortCutListener listener;

    @Before
    public void setup() throws IOException {
        listener = new ShortCutListener();
    }

    @Test
    public void testRegisterHotKey() {
        listener.registerHotKeyForLogging(ModKeyMapping.WIN, (int) 'A');
    }
}

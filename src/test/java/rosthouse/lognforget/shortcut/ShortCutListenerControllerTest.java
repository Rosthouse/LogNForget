package rosthouse.lognforget.shortcut;

import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import rosthouse.lognforget.shortcut.loggers.Logger;

@RunWith(MockitoJUnitRunner.class)
public class ShortCutListenerControllerTest {

    public static final String TESTSTRING = "TESTSTRING";

//    @InjectMocks
    ShortCutListenerController controller;
    @Mock
    TextField field;
    @Mock
    ShortCutListener listener;
    @Mock
    Logger logger;

    @Before
    public void setUp() {
        when(field.getText()).thenReturn(TESTSTRING);
        Mockito.doNothing().when(field).requestFocus();
        controller = new ShortCutListenerController();
        controller.initialize(null, null);
        controller.setLogger(logger);
        controller.setCloseListener(listener);
    }

    @Test
    public void testOnEnter() {
        controller.onEnter();
        verify(logger).logText(TESTSTRING);
        verify(listener).closeStage();
    }

}

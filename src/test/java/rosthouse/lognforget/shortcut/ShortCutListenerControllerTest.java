/**
 * $HeadURL$
 */
package rosthouse.lognforget.shortcut;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO
 *
 * @author Patrick Joos <patrick.joos@wuerth-itensis.ch>
 * @copyright (c) 2007-2015, Wuerth ITensis AG
 * @created 10.04.2015 10:55:50
 *
 * @$Revision$
 *
 * @$LastChangedBy$
 * @$LastChangedDate$
 * @$Id$
 */
public class ShortCutListenerControllerTest {

    ShortCutListenerController controller;

    @Before
    public void setUp() {
        controller = new ShortCutListenerController();
    }

    @Test
    public void testGetText() {
        String text = "+20m Meeting with John";
        assertThat(controller.removeModifierFromText(text), equalTo("Meeting with John"));
    }

    @Test
    public void testGetDuration() {
        String text = "+20m Meeting with John";
        Long minutes = 20l;
        assertThat(controller.getDurationFromNowUntilReminder(text).toMinutes(), equalTo(minutes));
    }

}

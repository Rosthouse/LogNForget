/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rosthouse.lognforget;

import java.time.Duration;
import static org.hamcrest.Matchers.equalTo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rosthouse.lognforget.reminder.LogEvent;
import rosthouse.lognforget.util.WindowManager;

/**
 *
 * @author Rosthouse <rosthouse@gmail.com>
 */
public class ReminderManagerTest {

    public ReminderManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetText() {
        String text = "+20m Meeting with John";
        assertThat(new ReminderManager().removeModifierFromText(text), equalTo("Meeting with John"));
    }

    @Test
    public void testGetDuration() {
        String text = "+20m Meeting with John";
        Long minutes = 20l;
        assertThat(new ReminderManager().getDurationFromNowUntilReminder(text).toMinutes(), equalTo(minutes));
    }

}

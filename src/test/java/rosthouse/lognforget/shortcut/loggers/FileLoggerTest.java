package rosthouse.lognforget.shortcut.loggers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class FileLoggerTest {

    FileLogger logger;

    @Before
    public void setUp() {
        logger = new FileLogger();
    }

    @Test
    public void testWriteToFile() throws IOException {
        File tempFile = File.createTempFile("TEST", ".log");
        tempFile.deleteOnExit();
        logger.logTextToFile("Test", tempFile);
        List<String> logEntries = Files.readAllLines(tempFile.toPath());
        assertThat(logEntries.size(), equalTo(1));
    }

    @Test
    public void getTimeString() {
        String format = logger.getCurrentTimeString();
        SimpleDateFormat formater = new SimpleDateFormat(FileLogger.LOG_DATE_FORMAT);
        try {
            Date parsedDate = formater.parse(format);
        } catch (ParseException ex) {
            fail("The format is not correct");
        }
    }

    @Test
    public void testCreateFile() {
        String path = System.getProperty("java.io.tmpdir") + File.separator + "testFile.log";
        File createdFile = logger.openOrCreateFile(path);
        assertThat(createdFile.exists(), equalTo(true));
        createdFile.deleteOnExit();
    }
}

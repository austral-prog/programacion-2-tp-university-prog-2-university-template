import com.university.app.App;
import com.university.service.ManagerRecord;
import com.university.inOut.CSVManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestApp {

    private static final String INPUT_PATH_1 = "src/main/resources/input.csv";
    private static final String EXPECTED_PATH_1 = "src/main/resources/expected.csv";
    private static final String OUTPUT_PATH_1 = "src/main/resources/output.csv";

    private static final String INPUT_PATH_2 = "src/main/resources/input_2.csv";
    private static final String EXPECTED_PATH_2 = "src/main/resources/expected_2.csv";
    private static final String OUTPUT_PATH_2 = "src/main/resources/output_2.csv";

    private static final String INPUT_PATH_3 = "src/main/resources/input_3.csv";
    private static final String EXPECTED_PATH_3 = "src/main/resources/expected_3.csv";
    private static final String OUTPUT_PATH_3 = "src/main/resources/output_3.csv";

    private final CSVManager csvManager = new CSVManager();
    private final App app = new App();
    private final ManagerRecord managerRecord = new ManagerRecord();

    @Test
    public void testTask1() {

        app.task(1, INPUT_PATH_1, OUTPUT_PATH_1, managerRecord);

        List<String[]> actualOutput = csvManager.read(OUTPUT_PATH_1, false);
        List<String[]> expectedOutput = csvManager.read(EXPECTED_PATH_1, false);

        for (int i = 0; i < expectedOutput.size(); i++) {
            assertArrayEquals(expectedOutput.get(i), actualOutput.get(i), "Entry no: " + i + " does not match");
        }
    }

    @Test
    public void testTask2() {
        app.task(2, INPUT_PATH_2, OUTPUT_PATH_2, managerRecord);

        List<String[]> actualOutput = csvManager.read(OUTPUT_PATH_2, false);
        List<String[]> expectedOutput = csvManager.read(EXPECTED_PATH_2, false);

        for (int i = 0; i < expectedOutput.size(); i++) {
            assertArrayEquals(expectedOutput.get(i), actualOutput.get(i), "Entry no: " + i + " does not match");
        }
    }

    @Test
    public void testTask3() {
        app.task(3, INPUT_PATH_3, OUTPUT_PATH_3, managerRecord);

        List<String[]> actualOutput = csvManager.read(OUTPUT_PATH_3, false);
        List<String[]> expectedOutput = csvManager.read(EXPECTED_PATH_3, false);

        for (int i = 0; i < expectedOutput.size(); i++) {
            assertArrayEquals(expectedOutput.get(i), actualOutput.get(i), "Entry no: " + i + " does not match");
        }
    }
}

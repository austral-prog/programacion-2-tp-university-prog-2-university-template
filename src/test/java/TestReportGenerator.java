
import com.university.service.Logic.DataMapper;
import com.university.service.Logic.ReportGenerator;
import com.university.service.ManagerRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestReportGenerator {
    @Test
    public void testReportGeneratorFirst() {

        ReportGenerator reportGenerator = new ReportGenerator();
        ManagerRecord record = new ManagerRecord();

        //Classroom,Subject,Student_Name,Student_Email,Subject_Teacher
        String[] inputLine = new String[]{"500", "Math", "John", "JohnMail@gmail.com", "Mr. Smith" };

        ArrayList<String[]> inputData = new ArrayList<>();
        inputData.add(inputLine);

        List<String[]> result = reportGenerator.firstReport(inputData, record);

        assertEquals(new String[]{"Student_Name","Course_Count"}, result.get(0));
        assertEquals(new String[]{"John", "1"}, result.get(1));
    }

    @Test
    public void testReportGeneratorSecond() {

        ReportGenerator reportGenerator = new ReportGenerator();
        ManagerRecord record = new ManagerRecord();

        //Student_Name,Subject_Name,Evaluation_Type,Evaluation_Name,Exercise_Name,Grade
        String[] inputLine = new String[]{"John", "Math", "WRITTEN_EXAM", "Final", "Ej. 6", "10" };

        ArrayList<String[]> inputData = new ArrayList<>();
        inputData.add(inputLine);

        List<String[]> result = reportGenerator.secondReport(inputData, record);

        assertEquals(new String[]{"Subject_Name","Evaluation_Name","Student_Name", "Grade"}, result.get(0));
        assertEquals(new String[]{"Math", "Final", "John", "10"}, result.get(1));
    }

    @Test
    public void testReportGeneratorThird() {

        ReportGenerator reportGenerator = new ReportGenerator();
        ManagerRecord record = new ManagerRecord();

                                    //Subject_Name,Criteria_Type,Criteria_Value,Evaluation_Name
        String[] inputLine = new String[]{"Math", "AVERAGE_ABOVE_VALUE", "4", "Final" };

        ArrayList<String[]> inputData = new ArrayList<>();
        inputData.add(inputLine);

        String[] DATA_TO_CREATE_EVALUATION = {"John", "Math", "WRITTEN_EXAM", "Final", "Ej. 6", "10"};

        DataMapper.secondDataPoint(DATA_TO_CREATE_EVALUATION, record);

        List<String[]> result = reportGenerator.thirdReport(inputData, record);

        // evaluation_name, evaluation_grade, evaluation_type, evaluation_criteria, criteria_value, passed/failed

        assertEquals(new String[]{"Evaluation_Name","Relevant_Grade","Evaluation_Type","Criteria","Criteria_Value","Passed/Failed"}, result.get(0));
        assertEquals(new String[]{"Final","10","WRITTEN_EXAM","AVERAGE_ABOVE_VALUE","4","Passed"}, result.get(1));
    }
}

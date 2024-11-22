import com.university.model.*;
import com.university.model.Evaluations.Evaluation;
import com.university.service.Logic.DataMapper;
import com.university.service.ManagerRecord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestDataMapper {

    @Test
    public void testDataReceiverFirst() {

        ManagerRecord record = new ManagerRecord();

        String[] exampleInputLine = {"1", "Math", "John", "john@gmail.com", "Mr. Smith"};

        DataMapper.firstDataPoint(exampleInputLine, record);

        Teacher teacher = record.teacherManager().entities.iterator().next();
        Classroom classroom = record.classroomManager().entities.iterator().next();
        Student student = record.studentManager().entities.iterator().next();
        Subject subject = record.subjectManager().entities.iterator().next();

        assertEquals("Math", subject.name());
        assertEquals("Mr. Smith", teacher.name());
        assertEquals("1", String.valueOf(classroom.getClassroomID()));
        assertEquals("John", student.name());
        assertEquals("john@gmail.com", student.getEmail());
    }

    @Test
    public void testDataReceiverSecond() {

        ManagerRecord record = new ManagerRecord();

        String[] exampleInputLine = {"John", "Math", "WRITTEN_EXAM", "Final", "Ej. 6", "10"};

        DataMapper.secondDataPoint(exampleInputLine, record);

        Student student = record.studentManager().entities.iterator().next();
        Subject subject = record.subjectManager().entities.iterator().next();
        Evaluation evaluation = record.evaluationManager().entities.iterator().next();
        Exercise exercise = evaluation.getExercises().getFirst();

        assertEquals("Math", subject.name());
        assertEquals("John", student.name());
        assertEquals("WRITTEN_EXAM", evaluation.getEvaluationType());
        assertEquals("Final", evaluation.name());
        assertEquals("Ej. 6", exercise.name());
        assertEquals(10, exercise.getGrade());
        assertNull(student.getEmail());
    }

    @Test
    public void testDataReceiverThird() {

        ManagerRecord record = new ManagerRecord();
                                //Subject_Name,Criteria_Type,Criteria_Value,Evaluation_Name
        String[] exampleInputLine = {"Biology", "AVERAGE_ABOVE_VALUE", "4", "Tp University"};

        String[] examplePreviousNecessaryLine = {"Mati", "Biology", "WRITTEN_EXAM", "Tp University", "Ej. 1", "10"};

        DataMapper.secondDataPoint(examplePreviousNecessaryLine, record);

        DataMapper.thirdDataPoint(exampleInputLine, record);

        Evaluation evaluation = record.evaluationManager().entities.iterator().next();
        Exercise exercise = evaluation.getExercises().getFirst();

        assertEquals("Tp University", evaluation.name());
        assertEquals("WRITTEN_EXAM", evaluation.getEvaluationType());
        assertEquals("Ej. 1", exercise.name());
        assertEquals(10, exercise.getGrade());
        assertTrue(evaluation.isPassed());
    }

}

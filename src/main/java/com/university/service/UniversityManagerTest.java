package TpUniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniversityManagerTest {

    UniversityManager manager;

    @BeforeEach
    public void setUp() {
        manager = new UniversityManager();
    }

    @Test
    public void testFirstTaskLogic() {
        // Sample input data (CSV format): Classroom,Subject,Student_Name,Student_Email,Subject_Teacher
        List<String[]> inputData = new ArrayList<>();
        // logic expects a header
        inputData.add(new String[]{"Classroom","Subject","Student_Name","Student_Email","Subject_Teacher"});
        inputData.add(new String[]{"10", "Art", "John Doe", "genericMail@gmai.com", "Juan"});
        inputData.add(new String[]{"10", "Biology", "John Doe", "genericMail@gmai.com", "Juan"});
        inputData.add(new String[]{"15", "Art", "Pepe Aurelio", "genericMail@hotmail.com", "Juan"});
        inputData.add(new String[]{"15", "Biology", "Pepe Aurelio", "genericMail@hotmail.com", "Juan"});

        // Run the logic
        List<String[]> outputData = manager.firstTaskLogic(inputData);

        // Output the results for visual validation
        System.out.println("Test FirstTaskLogic:");
        for (String[] line : outputData) {
            System.out.println(String.join(", ", line));
        }

        // Validate the results
        assertEquals("John Doe", outputData.get(1)[0], "Student John should be first after sorting");
        assertEquals("2", outputData.get(1)[1], "John should have 2 courses");

        assertEquals("Pepe Aurelio", outputData.get(2)[0], "Student Pepe should be second after sorting");
        assertEquals("2", outputData.get(2)[1], "Pepe should have 2 courses");
    }

    @Test
    public void testSecondTaskLogic() {
        // Sample input data (CSV format): Student,Subject,Evaluation_Type,Evaluation_Name,Exercise_Name,Grade
        List<String[]> inputData = new ArrayList<>();
        // logic expects a header
        inputData.add(new String[]{"Student","Subject","Evaluation_Type","Evaluation_Name","Exercise_Name","Grade"});
        inputData.add(new String[]{"Juan Pablo", "Algebra", "Final", "Final Algebra", "Parte 1", "7.0"});
        inputData.add(new String[]{"Juan Pablo", "Algebra", "Final", "Final Algebra", "Parte 2", "8.0"});
        inputData.add(new String[]{"Juan Pablo", "Algebra", "Final", "Final Algebra", "Parte 3", "9.0"});
        inputData.add(new String[]{"Matias PM", "Prog", "Homework", "Regex Homework", "1a", "3.0"});
        inputData.add(new String[]{"Matias PM", "Prog", "Homework", "Regex Homework", "1b", "4.0"});
        inputData.add(new String[]{"Matias PM", "Prog", "Homework", "Regex Homework", "1c", "5.0"});


        // Run the logic
        List<String[]> outputData = manager.secondTaskLogic(inputData);

        // Output the results for visual validation
        System.out.println("\nTest SecondTaskLogic:");
        for (String[] line : outputData) {
            System.out.println(String.join(", ", line));
        }

        // Validate the results (Subject_Name,Evaluation_Name,Student_Name,Grade)
        // Validate the first entry
        assertEquals("Algebra", outputData.get(1)[0]);
        assertEquals("Final Algebra", outputData.get(1)[1]);
        assertEquals("Juan Pablo", outputData.get(1)[2]);
        assertEquals("8.0", outputData.get(1)[3]);

        // Validate the second entry (should be Math, Exam 1, Pepito Aurelio, 90.0)
        assertEquals("Prog", outputData.get(2)[0], "Second subject should be Math");
        assertEquals("Regex Homework", outputData.get(2)[1], "Second evaluation should be Exam 1");
        assertEquals("Matias PM", outputData.get(2)[2], "Pepito Aurelio should be listed second for Math Exam 1");
        assertEquals("4.0", outputData.get(2)[3], "Pepito Aurelio's grade should be 90.0 for Math Exam 1");
    }
}
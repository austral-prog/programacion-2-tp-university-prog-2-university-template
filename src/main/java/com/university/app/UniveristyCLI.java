package com.university.app;

import com.university.CLI;
import com.university.CRUDRepository;

import com.university.inOut.IncompatibleEntity;
import com.university.model.*;
import com.university.model.Evaluations.Evaluation;
import com.university.service.EntityManager;
import com.university.service.DataReceiver;

import java.util.Map;
import java.util.Scanner;

import static com.university.service.ManagerHolder.*;

public class UniveristyCLI implements CLI {

    private final Scanner scanner;

    public UniveristyCLI() {
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {

        App app = new App();
        app.runTasks();

        CRUDRepository<?>[] crudInterfaces = new CRUDRepository<?>[]{
                StudentManager, TeacherManager, ClassroomManager,
                SubjectManager, EvaluationManager, ExerciseManager
        };
        // Run the CLI
        UniveristyCLI universityCLI = new UniveristyCLI();
        universityCLI.runCLI(crudInterfaces);
    }

    @Override
    public void runCLI(CRUDRepository<?>[] crudInterfaces) {
        while (true) {
            System.out.println("\nSelect an entity type to manage:");
            for (int i = 0; i < crudInterfaces.length; i++) {
                System.out.printf("%d. %s%n", i + 1, crudInterfaces[i].getIdentifier(true));
            }
            System.out.println("0. Exit");

            int entityChoice = getUserChoice(crudInterfaces.length);
            if (entityChoice == 0) break;

            CRUDRepository<?> selectedRepository = crudInterfaces[entityChoice - 1];
            handleEntityOperations(selectedRepository);
        }

        scanner.close();
        System.out.println("Exiting CLI...");
    }

    private void handleEntityOperations(CRUDRepository<?> repository) {
        while (true) {
            System.out.printf("\nManaging %s entities. Choose an operation:%n", repository.getIdentifier(true));
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete"); // todo if passed true getIdentifier returns prural
            System.out.println("5. View all "+ repository.getIdentifier(true));
            System.out.println("6. View all entities");
            System.out.println("7. Back to main menu");

            int operationChoice = getUserChoice(7);
            if (operationChoice == 7) break;

            switch (operationChoice) {
                case 1 -> createEntity(repository);
                case 2 -> {
                    System.out.print("Enter entity ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Entity entity = repository.read(id);
                    if (entity != null) {
                        System.out.println(entity.toString());
                    } else {
                        System.out.println("Entity not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter entity ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Entity entity = repository.read(id);
                    if (entity != null) {
                        updateEntity(entity, repository);
                    } else {
                        System.out.println("Entity not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter entity ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    boolean flag = repository.delete(id);
                    System.out.println(flag ? "Entity deleted successfully." : "Entity not found.");
                }
                case 5 -> viewAllEntities(repository);
                case 6 -> viewALLEntities();
            }
        }
    }


    private void updateEntity(Entity entity, CRUDRepository<?> repository) {
        String className = repository.getIdentifier(false);
        switch (className) {
            case "Student" -> updateStudent((Student) entity, repository);
            case "Subject" -> updateSubject((Subject) entity, repository);
            case "Teacher" -> updateTeacher((Teacher) entity, repository);
            case "Evaluation" -> updateEvaluation((Evaluation) entity, repository); // HAD TO IMPORT ONLY EVALUATION
            case "Exercise" -> updateExercise((Exercise) entity, repository);
            case "Classroom" -> updateClassroom((Classroom) entity, repository);
            default -> throw new IncompatibleEntity("Invalid entity type");
        }

    }

    private void viewAllEntities(CRUDRepository<?> repository) {
        EntityManager<?> entityManager = (EntityManager<?>) repository;
        Map<Integer, Entity> entities = entityManager.getEntityMap();
        if (entities.isEmpty()) {
            System.out.println("No entities found.");
        } else { //todo implementation name in Entity
            entities.forEach((id, entity) -> System.out.println("ID: " + id + ", Entity: " + entity.name()));
        }
    }
    private void viewALLEntities() {
    }

    private void createEntity(CRUDRepository<?> repository) {
        String className = repository.getIdentifier(true);
        switch (className) {
            case "Student" -> createStudent(repository);
            case "Subject" -> createSubject(repository);
            case "Teacher" -> createTeacher(repository);
            case "Evaluation" -> createEvaluation(repository);
            case "Exercise" -> createExercise(repository);
            case "Classroom" -> createClassroom(repository);
            default -> throw new IncompatibleEntity("Invalid entity type");

        }
    }

    private void createStudent(CRUDRepository<?> repository) {
        String studentName;
        String studentEmail;
        System.out.print("Enter student name: ");
        studentName = scanner.nextLine();
        System.out.print("Enter student email: ");
        studentEmail = scanner.nextLine();
        Student student = DataReceiver.rawStudent(studentName, studentEmail);
        System.out.println("Student created successfully.");
        System.out.println("Student name: " + studentName + " Student ID " + student.getName());
    }

    private void createSubject(CRUDRepository<?> repository) {
        String subjectName;
        System.out.print("Enter subject name: ");
        subjectName = scanner.nextLine();
        DataReceiver.rawSubject(subjectName);
        System.out.println("Subject created successfully.");
    }

    private void createTeacher(CRUDRepository<?> repository) {
        String teacherName;
        System.out.print("Enter teacher name: ");
        teacherName = scanner.nextLine();
        DataReceiver.rawTeacher(teacherName);
        System.out.println("Teacher created successfully.");
    }

    private void createEvaluation(CRUDRepository<?> repository) {
        String evaluationName;
        int subjectID;
        int studentID;
        String evaluationType;
        System.out.print("Enter evaluation name: ");
        evaluationName = scanner.nextLine();
        System.out.print("Enter subject ID: ");
        subjectID = scanner.nextInt();
        System.out.print("Enter student ID: ");
        studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter evaluation type: ");
        evaluationType = scanner.nextLine();
        DataReceiver.rawEvaluation(evaluationName, subjectID, studentID, evaluationType);
        System.out.println("Evaluation created successfully.");
    }

    private void createExercise(CRUDRepository<?> repository) {
        String exerciseName;
        double grade;
        int evaluationID;
        System.out.print("Enter exercise name: ");
        exerciseName = scanner.nextLine();
        System.out.print("Enter grade: ");
        grade = scanner.nextDouble();
        System.out.print("Enter evaluation ID: ");
        evaluationID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        DataReceiver.rawExercise(exerciseName, grade, evaluationID);
        System.out.println("Exercise created successfully.");
    }

    private void createClassroom(CRUDRepository<?> repository) {
        int classroomID;
        System.out.print("Enter classroom ID: ");
        classroomID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        DataReceiver.rawClassroom(classroomID);
        System.out.println("Classroom created successfully.");
    }

    private int getUserChoice(int maxOption) {
        int choice;
        while (true) {
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            if (choice >= 0 && choice <= maxOption) break;
            System.out.println("Invalid choice. Please try again.");
        }
        return choice;
    }
}
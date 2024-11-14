package com.university.app;

import com.university.CLI;
import com.university.CRUDRepository;

import com.university.inOut.IncompatibleEntity;
import com.university.model.*;
import com.university.model.Evaluations.Evaluation;
import com.university.service.EntityManager;
import com.university.service.DataReceiver;

import java.util.HashSet;
import java.util.Scanner;

import static com.university.service.ManagerHolder.*;

public class UniversityCLI implements CLI {

    private final Scanner scanner;

    public UniversityCLI() {
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
        UniversityCLI universityCLI = new UniversityCLI();
        universityCLI.runCLI(crudInterfaces);
    }

    @Override
    public void runCLI(CRUDRepository<?>[] crudInterfaces) {
        while (true) {
            System.out.println("\nSelect an entity type to manage:");
            for (int i = 0; i < crudInterfaces.length; i++) {
                System.out.printf("%d. %s%n", i +1, crudInterfaces[i].getIdentifier()+"s");
            }
            System.out.println((crudInterfaces.length +1) + ". Exit");

            int entityChoice = getUserChoice(crudInterfaces.length +1); //checks if input is correct, if not asks again

            if (entityChoice == crudInterfaces.length +1){closeCLI();break;} //exit choice

            CRUDRepository<?> selectedRepository = crudInterfaces[entityChoice - 1];
            try {
                handleEntityOperations(selectedRepository, crudInterfaces);
            } catch (IncompatibleEntity e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
                handleEntityOperations(selectedRepository, crudInterfaces);
            }
        }
    }

    private void closeCLI() {
        scanner.close();
        System.out.println("Exiting CLI...");
    }

    private void handleEntityOperations(CRUDRepository<?> repository, CRUDRepository<?>[] crudInterfaces) {
        while (true) {
            System.out.printf("\nManaging %s entities. Choose an operation:%n", repository.getIdentifier() +"s");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. View all " + repository.getIdentifier().toLowerCase() + "s");
            System.out.println("6. View all entities");
            System.out.println("7. Back to main menu");

            int operationChoice = getUserChoice(8);
            if (operationChoice == 7) break;

            switch (operationChoice) {
                case 1 -> createEntity(repository);

                case 2 -> {
                    System.out.print("Enter entity ID to read: ");
                    int id = Integer.parseInt(getUserString());
                    Entity entity = repository.read(id);
                    if (entity != null) {
                        System.out.println(entity);
                    } else {
                        System.out.println("Entity not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter entity ID to update: ");
                    int id = Integer.parseInt(getUserString());
                    Entity entity = repository.read(id);
                    if (entity != null) {
                        updateEntity(entity, repository);
                        System.out.println("Entity updated successfully.");
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
                case 6 -> viewEveryEntity(crudInterfaces);
            }
        }
    }


    private void updateEntity(Entity entity, CRUDRepository<?> repository) {
        String className = repository.getIdentifier();
        switch (className) {
            case "Student" -> updateStudent((Student) entity);
            case "Subject" -> updateSubject((Subject) entity);
            case "Teacher" -> updateTeacher((Teacher) entity);
            case "Evaluation" -> updateEvaluation((Evaluation) entity, repository); // HAD TO IMPORT ONLY EVALUATION
            case "Exercise" -> updateExercise((Exercise) entity);
            case "Classroom" -> updateClassroom((Classroom) entity);
            default -> throw new IncompatibleEntity("Invalid entity type");
        }

    }

    private void updateStudent(Student student) {
        System.out.println("Updating " + student.toString());
        System.out.println("Enter new student name: ");
        String studentName = getUserString();
        System.out.println("Enter new student email: ");
        String studentEmail = getUserString();
        student.setName(studentName);
        student.setEmail(studentEmail);
    }

    private void updateClassroom(Classroom entity) {
        System.out.println("Updating " + entity.toString());
        System.out.println("Enter new classroom ID: ");
        int classroomID = Integer.parseInt(getUserString());
        entity.setClassroomID(classroomID);
    }

    private void updateExercise(Exercise entity) {
        System.out.println("Updating " + entity.toString());
        System.out.println("Enter new exercise name: ");
        String exerciseName = getUserString();
        System.out.println("Enter new grade: ");
        double grade = Double.parseDouble(getUserString());
        entity.setExerciseName(exerciseName);
        entity.setGrade(grade);
    }

    private <E extends Entity> void updateEvaluation(Evaluation entity, CRUDRepository<E> repository) {
        EntityManager<E> entityManager = (EntityManager<E>) repository;
        System.out.println("Updating " + entity.toString());
        System.out.println("Enter new evaluation name: ");
        String evaluationName = getUserString();
        entity.setEvaluationName(evaluationName);
        System.out.println("Would you like to update the evaluations exercises? (Y/N)");
        String choice = getUserString();
        if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Choose an exercise to update:");
            entity.getExercises().forEach((exercise) -> System.out.println(exercise.toString()));
            System.out.print("Enter exercise ID to update: ");
            int id = Integer.parseInt(getUserString());
            if (entityManager.getEntityMap().get(id) instanceof Exercise exercise) {
                updateExercise(exercise);
            } else {
                throw new IncompatibleEntity("Invalid ID caused entity incompatibilities.");
            }
        }
    }

    private void updateTeacher(Teacher entity) {
        System.out.println("Updating " + entity.toString());
        System.out.println("Enter new teacher name: ");
        String teacherName = getUserString();
        entity.setName(teacherName);
    }

    private void updateSubject(Subject entity) {
        System.out.println("Updating " + entity.toString());
        System.out.println("Enter new subject name: ");
        String subjectName = getUserString();
        entity.setSubjectName(subjectName);
    }

    private <E extends Entity> void  viewAllEntities(CRUDRepository<E> repository) {
        EntityManager<E> entityManager = (EntityManager<E>) repository;
        HashSet<E> entities = entityManager.getEntities();
        if (entities.isEmpty()) {
            System.out.println("No entities found.");
        } else {
            entities.forEach((entity -> System.out.println(entity.toString())));
        }
    }
    private void viewEveryEntity(CRUDRepository<?>[] crudInterfaces) {
        for (CRUDRepository<?> repository : crudInterfaces) {
            viewAllEntities(repository);
        }
    }

    private void createEntity(CRUDRepository<?> repository) {
        String className = repository.getIdentifier();
        Entity entity = switch (className) {
            case "Student" -> createStudent();
            case "Subject" -> createSubject();
            case "Teacher" -> createTeacher();
            case "Evaluation" -> createEvaluation();
            case "Exercise" -> createExercise();
            case "Classroom" -> createClassroom();
            default -> throw new IncompatibleEntity("Invalid entity type");
        };
        System.out.println(className + " created successfully.");
        System.out.println(entity.toString());
    }

    private Student createStudent() {
        String studentName;
        String studentEmail;

        System.out.println("Enter student name: ");
        studentName = getUserString();
        System.out.println("Enter student email: ");
        studentEmail = getUserString();
        return DataReceiver.rawStudent(studentName, studentEmail);
    }

    private Subject createSubject() {
        System.out.print("Enter subject name: ");
        String subjectName = getUserString();
        return DataReceiver.rawSubject(subjectName);
    }

    private Teacher createTeacher() {
        System.out.print("Enter teacher name: ");
        String teacherName = getUserString();
        return DataReceiver.rawTeacher(teacherName);
    }

    private Evaluation createEvaluation() {
        String evaluationName;
        int subjectID;
        int studentID;
        String evaluationType;
        System.out.print("Enter evaluation name: ");
        evaluationName = getUserString();
        System.out.print("Enter subject ID: ");
        subjectID = Integer.parseInt(getUserString());
        System.out.print("Enter student ID: ");
        studentID = Integer.parseInt(getUserString());
        System.out.print("Enter evaluation type: (WRITTEN_EXAM, ORAL_EXAM, FINAL_PRACTICAL_WORK, PRACTICAL_WORK): ");
        evaluationType = getUserString();
        return DataReceiver.rawEvaluation(evaluationName, subjectID, studentID, evaluationType);
    }

    private Exercise createExercise() {
        String exerciseName;
        double grade;
        int evaluationID;
        System.out.print("Enter exercise name: ");
        exerciseName = getUserString();
        System.out.print("Enter grade: ");
        grade = Double.parseDouble(getUserString());
        System.out.print("Enter evaluation ID: ");
        evaluationID = Integer.parseInt(getUserString());
        return DataReceiver.rawExercise(exerciseName, grade, evaluationID);
    }

    private Classroom createClassroom() {
        int classroomID;
        System.out.print("Enter classroom ID: ");
        classroomID = Integer.parseInt(getUserString());
        return DataReceiver.rawClassroom(classroomID);
    }
    private int getUserChoice(int maxOption) {
        while (true) {
            System.out.print("Select an option: ");
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline only after a valid integer input
                if (choice >= 1 && choice <= maxOption) return choice;
            } else {
                scanner.nextLine(); // Consume invalid input
            }
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private String getUserString() {
        while (true) {
            String input = scanner.nextLine();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}
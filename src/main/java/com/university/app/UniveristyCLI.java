package com.university.app;

import com.university.CLI;
import com.university.CRUDRepository;

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
        CRUDRepository<?>[] crudInterfaces = new CRUDRepository<?>[]{
                StudentManager, TeacherManager, ClassroomManager, SubjectManager, EvaluationManager, ExerciseManager
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
                System.out.printf("%d. %s%n", i + 1, crudInterfaces[i].getIdentifier());
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
            System.out.printf("\nManaging %s entities. Choose an operation:%n", repository.getIdentifier());
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. View all entities");
            System.out.println("0. Back to main menu");

            int operationChoice = getUserChoice(5);
            if (operationChoice == 0) break;

            switch (operationChoice) {
                case 1 -> createEntity(repository);
                case 2 -> {
                    System.out.print("Enter entity ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Entity entity = repository.read(id);
                    if (entity != null) {
                        System.out.println(entity);
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
                        // Implement update logic based on entity type
                        System.out.println("Update functionality not yet implemented for " + repository.getIdentifier());
                    } else {
                        System.out.println("Entity not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter entity ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    repository.delete(id);
                    System.out.println("Entity deleted (if it existed).");
                }
                case 5 -> viewAllEntities(repository);
            }
        }
    }

    private void viewAllEntities(CRUDRepository<?> repository) {
        EntityManager<?> entityManager = (EntityManager<?>) repository;
        Map<Integer, Entity> entities = entityManager.getAllEntities();
        if (entities.isEmpty()) {
            System.out.println("No entities found.");
        } else {
            entities.forEach((id, entity) -> System.out.println("ID: " + id + ", Entity: " + entity));
        }
    }

    private void createEntity(CRUDRepository<?> repository) {
        Entity entity = createNewEntity(repository.getEntityClass());
        if (entity != null) {
            if (entity instanceof Evaluation) {
                // Prompt for related entities' IDs
                System.out.print("Enter Subject ID: ");
                int subjectID = scanner.nextInt();
                System.out.print("Enter Student ID: ");
                int studentID = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                // Call the DataReceiver with the data
                DataReceiver.rawEvaluation(((Evaluation) entity).getName(), subjectID, studentID, ((Evaluation) entity).getEvaluationType());
            }
            ((CRUDRepository<Entity>) repository).create(entity);
            System.out.println("Entity created successfully.");
        } else {
            System.out.println("Failed to create entity.");
        }
    }

    private <T extends Entity> T createNewEntity(Class<T> entityClass) {
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            System.out.println("Creating a new entity of type: " + entityClass.getSimpleName());

            // Prompts for each entity type
            if (entity instanceof Student) {
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                ((Student) entity).setName(name);
            } else if (entity instanceof Subject) {
                System.out.print("Enter subject name: ");
                String name = scanner.nextLine();
                ((Subject) entity).setName(name);
            } else if (entity instanceof Teacher) {
                System.out.print("Enter teacher name: ");
                String name = scanner.nextLine();
                ((Teacher) entity).setName(name);
            } else if (entity instanceof Evaluation) {
                System.out.print("Enter evaluation name: ");
                String evaluationName = scanner.nextLine();
                System.out.print("Enter evaluation type: ");
                String evaluationType = scanner.nextLine();
                ((Evaluation) entity).setEvaluationName(evaluationName);
                ((Evaluation) entity).setEvaluationType(evaluationType);
            } else if (entity instanceof Exercise) {
                System.out.print("Enter exercise name: ");
                String name = scanner.nextLine();
                System.out.print("Enter grade: ");
                double grade = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                ((Exercise) entity).setName(name);
                ((Exercise) entity).setGrade(grade);
            } else if (entity instanceof Classroom) {
                System.out.print("Enter Classroom ID: ");
                int id = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                ((Classroom) entity).setClassroomId(id);
            }

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
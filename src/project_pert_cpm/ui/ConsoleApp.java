package ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private static List<Project> projects = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    static class Project {
        String name;
        String description;
        List<Task> tasks = new ArrayList<>();

        Project(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    static class Task {
        String name;
        int duration;

        Task(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }
    }

    public static void main(String[] args) {
        System.out.println(" Планировщик проектов - Консольная версия");
        System.out.println("===========================================");


        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createProject();
                    break;
                case "2":
                    addTaskToProject();
                    break;
                case "3":
                    showProjects();
                    break;
                case "4":
                    calculateCriticalPath();
                    break;
                case "0":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println(" Неверный выбор!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n Меню:");
        System.out.println("1. Создать проект");
        System.out.println("2. Добавить задачу в проект");
        System.out.println("3. Показать все проекты");
        System.out.println("4. Рассчитать критический путь");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void createProject() {
        System.out.print("Введите название проекта: ");
        String name = scanner.nextLine();

        System.out.print("Введите описание проекта: ");
        String description = scanner.nextLine();

        Project project = new Project(name, description);
        projects.add(project);

        System.out.println(" Проект '" + name + "' создан!");
    }

    private static void addTaskToProject() {
        if (projects.isEmpty()) {
            System.out.println(" Сначала создайте проект!");
            return;
        }

        System.out.println("Выберите проект:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).name);
        }

        System.out.print("Номер проекта: ");
        int projectIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (projectIndex < 0 || projectIndex >= projects.size()) {
            System.out.println(" Неверный номер проекта!");
            return;
        }

        System.out.print("Введите название задачи: ");
        String taskName = scanner.nextLine();

        System.out.print("Введите длительность (в днях): ");
        int duration = Integer.parseInt(scanner.nextLine());

        Task task = new Task(taskName, duration);
        projects.get(projectIndex).tasks.add(task);

        System.out.println(" Задача '" + taskName + "' добавлена в проект '" +
                projects.get(projectIndex).name + "'!");
    }

    private static void showProjects() {
        if (projects.isEmpty()) {
            System.out.println(" Проектов пока нет");
            return;
        }

        System.out.println("\n Ваши проекты:");
        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            System.out.println((i + 1) + ". " + project.name + " - " + project.description);
            System.out.println("   Задачи: " + project.tasks.size() + " шт.");

            for (int j = 0; j < project.tasks.size(); j++) {
                Task task = project.tasks.get(j);
                System.out.println("   • " + task.name + " (" + task.duration + " дней)");
            }
            System.out.println();
        }
    }

    private static void calculateCriticalPath() {
        if (projects.isEmpty()) {
            System.out.println(" Нет проектов для расчета!");
            return;
        }

        System.out.println(" Расчет критического пути...");

        for (Project project : projects) {
            if (!project.tasks.isEmpty()) {
                int totalDuration = project.tasks.stream().mapToInt(t -> t.duration).sum();
                String longestTask = project.tasks.stream()
                        .max((t1, t2) -> Integer.compare(t1.duration, t2.duration))
                        .get().name;

                System.out.println("\n📊 Проект: " + project.name);
                System.out.println("• Общая длительность: " + totalDuration + " дней");
                System.out.println("• Самая длительная задача: " + longestTask);
                System.out.println("• Количество задач: " + project.tasks.size());
            }
        }
    }


}
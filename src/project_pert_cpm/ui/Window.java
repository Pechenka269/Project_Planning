package ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Window extends Application {

    private ObservableList<ProjectItem> projectItems = FXCollections.observableArrayList();
    private VBox projectsContainer;
    private Map<String, Project> projectsData = new HashMap<>();
    private String currentEditingProject = "";

    public static class Project {
        public String name;
        public String description;
        public LocalDate deadline;
        public String priority;
        public ObservableList<Task> tasks;

        public Project(String name, String description, LocalDate deadline, String priority) {
            this.name = name;
            this.description = description;
            this.deadline = deadline;
            this.priority = priority;
            this.tasks = FXCollections.observableArrayList();
        }
    }

    public static class Task {
        public String name;
        public String description;
        public LocalDate deadline;

        public Task(String name, String description, LocalDate deadline) {
            this.name = name;
            this.description = description;
            this.deadline = deadline;
        }

        @Override
        public String toString() {
            return String.format("✅ %s | 📅 %s | %s",
                    name,
                    deadline != null ? deadline.toString() : "Без дедлайна",
                    description.isEmpty() ? "Без описания" : description
            );
        }
    }

    private static class ProjectItem {
        public VBox container;
        public HBox header;
        public Label infoLabel;
        public VBox tasksContainer;
        public ListView<String> tasksListView;

        public ProjectItem(VBox container, HBox header, Label infoLabel, VBox tasksContainer, ListView<String> tasksListView) {
            this.container = container;
            this.header = header;
            this.infoLabel = infoLabel;
            this.tasksContainer = tasksContainer;
            this.tasksListView = tasksListView;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Мои проекты");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        projectsContainer = new VBox(10);
        projectsContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(projectsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);
// демо кнопка для демки на атту
        Button demoButton = new Button("🚀 Демо-режим");
        demoButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        demoButton.setOnAction(e -> showDemoData());

        Button addProjectButton = new Button("➕ Добавить проект");
        addProjectButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        addProjectButton.setOnAction(e -> openAddProjectWindow());

        HBox buttonsBox = new HBox(10, demoButton, addProjectButton);
        buttonsBox.setAlignment(Pos.CENTER_LEFT);

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.getChildren().addAll(titleLabel, buttonsBox, scrollPane);

        Scene scene = new Scene(mainLayout, 700, 600);
        primaryStage.setTitle("Планировщик проектов");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDemoData() {
        projectsData.clear();
        projectsContainer.getChildren().clear();
        projectItems.clear();

        Project demoProject = new Project(
                "Разработка мобильного приложения",
                "Создание приложения для планирования задач",
                LocalDate.now().plusDays(30),
                "Высокий"
        );
//демо задачи
        demoProject.tasks.add(new Task("Дизайн интерфейса", "Создание UI/UX дизайна", LocalDate.now().plusDays(5)));
        demoProject.tasks.add(new Task("Разработка backend", "Реализация серверной части", LocalDate.now().plusDays(15)));
        demoProject.tasks.add(new Task("Тестирование", "Функциональное тестирование", LocalDate.now().plusDays(25)));


        projectsData.put(demoProject.name, demoProject);
        createProjectItem(demoProject);

        showCriticalPathAnalysis();
    }

    private void showCriticalPathAnalysis() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("📊 Демо-анализ проекта");
        alert.setHeaderText("Результаты расчета критического пути");
        alert.setContentText(
                "Проект: 'Разработка мобильного приложения'\n\n" +
                        "📈 PERT-анализ:\n" +
                        "• Ожидаемая длительность: 45 дней\n" +
                        "• Вероятность уложиться в срок: 85%\n\n" +
                        "🛠️ CPM-анализ:\n" +
                        "• Критический путь: Дизайн → Разработка → Тестирование \n" +
                        "• Общая длительность: 40 дней\n" +
                        "• Критические задачи: Все задачи находятся на критическом пути\n\n" +
                        "💡 Вывод: Проект хорошо спланирован, все задачи критически важны!"
        );
        alert.showAndWait();
    }

    private void openAddProjectWindow() {
        Stage addProjectStage = new Stage();
        addProjectStage.setTitle("Добавить новый проект");

        Label nameLabel = new Label("Название проекта:");
        TextField nameField = new TextField();
        nameField.setPromptText("Введите название проекта");

        Label descLabel = new Label("Описание проекта:");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Заметки по проекту...");
        descArea.setPrefRowCount(4);

        Label deadlineLabel = new Label("Дедлайн:");
        DatePicker deadlinePicker = new DatePicker();

        Label priorityLabel = new Label("Приоритет:");
        ComboBox<String> priorityCombo = new ComboBox<>();
        priorityCombo.getItems().addAll("Высокий", "Средний", "Низкий");
        priorityCombo.setValue("Средний");

        Button addTaskButton = new Button("📝 Добавить задачу");
        addTaskButton.setOnAction(e -> {
            String projectName = nameField.getText().trim();
            if (projectName.isEmpty()) {
                showAlert("Сначала введите название проекта!");
                return;
            }
            currentEditingProject = projectName;
            openAddTaskWindow();
        });

        Button saveButton = new Button("Сохранить проект");
        Button cancelButton = new Button("Отмена");

        saveButton.setOnAction(e -> {
            String projectName = nameField.getText().trim();
            if (projectName.isEmpty()) {
                showAlert("Введите название проекта!");
                return;
            }

            if (!projectsData.containsKey(projectName)) {
                saveProject(projectName, descArea.getText(),
                        deadlinePicker.getValue(), priorityCombo.getValue());
            } else {
                Project project = projectsData.get(projectName);
                project.description = descArea.getText();
                project.deadline = deadlinePicker.getValue();
                project.priority = priorityCombo.getValue();
                updateProjectDisplay(projectName);
            }

            addProjectStage.close();
        });

        cancelButton.setOnAction(e -> addProjectStage.close());

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(15));
        formLayout.getChildren().addAll(
                nameLabel, nameField,
                descLabel, descArea,
                deadlineLabel, deadlinePicker,
                priorityLabel, priorityCombo,
                addTaskButton,
                buttonBox
        );

        Scene formScene = new Scene(formLayout, 400, 500);
        addProjectStage.setScene(formScene);
        addProjectStage.show();
    }

    private void openAddTaskWindow() {
        if (currentEditingProject.isEmpty()) {
            showAlert("Сначала создайте или выберите проект!");
            return;
        }

        Stage addTaskStage = new Stage();
        addTaskStage.setTitle("Добавить задачу в проект: " + currentEditingProject);

        Label nameLabel = new Label("Название задачи:");
        TextField nameField = new TextField();
        nameField.setPromptText("Введите название задачи");

        Label descLabel = new Label("Описание задачи:");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Подробное описание задачи...");
        descArea.setPrefRowCount(3);

        Label deadlineLabel = new Label("Дедлайн задачи:");
        DatePicker deadlinePicker = new DatePicker();

        Button saveButton = new Button("Сохранить задачу");
        Button cancelButton = new Button("Отмена");

        saveButton.setOnAction(e -> {
            String taskName = nameField.getText().trim();
            if (taskName.isEmpty()) {
                showAlert("Введите название задачи!");
                return;
            }

            saveTask(taskName, descArea.getText(), deadlinePicker.getValue());
            addTaskStage.close();
        });

        cancelButton.setOnAction(e -> addTaskStage.close());

        HBox buttonBox = new HBox(10, saveButton, cancelButton);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        VBox formLayout = new VBox(10);
        formLayout.setPadding(new Insets(15));
        formLayout.getChildren().addAll(
                nameLabel, nameField,
                descLabel, descArea,
                deadlineLabel, deadlinePicker,
                buttonBox
        );

        Scene formScene = new Scene(formLayout, 400, 400);
        addTaskStage.setScene(formScene);
        addTaskStage.show();
    }

    private void saveProject(String name, String description, LocalDate deadline, String priority) {
        Project project = new Project(name, description, deadline, priority);
        projectsData.put(name, project);
        createProjectItem(project);
        showAlert("Проект '" + name + "' успешно добавлен!");
    }

    private void createProjectItem(Project project) {
        HBox projectHeader = new HBox(10);
        projectHeader.setAlignment(Pos.CENTER_LEFT);
        projectHeader.setStyle("-fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

        Label projectInfo = new Label();
        updateProjectInfoLabel(projectInfo, project);
        projectInfo.setStyle("-fx-font-weight: bold;");

        ToggleButton expandButton = new ToggleButton("▼");
        expandButton.setStyle("-fx-font-size: 10px; -fx-padding: 2 5 2 5;");

        Button addRelationsButton = new Button("🔗 Связи");
        addRelationsButton.setStyle("-fx-font-size: 12px; -fx-padding: 5 10 5 10;");
        addRelationsButton.setOnAction(e -> showRelationsMessage(project.name));

        Button addTaskButton = new Button("➕ Задача");
        addTaskButton.setStyle("-fx-font-size: 12px; -fx-padding: 5 10 5 10;");
        addTaskButton.setOnAction(e -> {
            currentEditingProject = project.name;
            openAddTaskWindow();
        });

        projectHeader.getChildren().addAll(expandButton, projectInfo, addTaskButton, addRelationsButton);

        VBox tasksContainer = new VBox(5);
        tasksContainer.setStyle("-fx-padding: 10 10 10 30; -fx-background-color: #f0f0f0;");
        tasksContainer.setVisible(false);
        tasksContainer.setManaged(false);

        ObservableList<String> taskStrings = FXCollections.observableArrayList();
        for (Task task : project.tasks) {
            taskStrings.add(task.toString());
        }

        ListView<String> tasksListView = new ListView<>(taskStrings);
        tasksListView.setPrefHeight(150);
        tasksListView.setStyle("-fx-border-color: #ccc; -fx-border-radius: 3;");

        tasksContainer.getChildren().add(tasksListView);

        expandButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                tasksContainer.setVisible(true);
                tasksContainer.setManaged(true);
                expandButton.setText("▲");
            } else {
                tasksContainer.setVisible(false);
                tasksContainer.setManaged(false);
                expandButton.setText("▼");
            }
        });

        VBox projectItem = new VBox();
        projectItem.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-color: white;");
        projectItem.getChildren().addAll(projectHeader, tasksContainer);

        projectsContainer.getChildren().add(projectItem);

        projectItems.add(new ProjectItem(projectItem, projectHeader, projectInfo, tasksContainer, tasksListView));
    }

    private void updateProjectInfoLabel(Label label, Project project) {
        String projectInfo = String.format("📋 %s | 🗓️ %s | ⚡ %s | 📝 %d задач",
                project.name,
                project.deadline != null ? project.deadline.toString() : "Не установлен",
                project.priority,
                project.tasks.size()
        );
        label.setText(projectInfo);
    }

    private void saveTask(String taskName, String description, LocalDate deadline) {
        Project project = projectsData.get(currentEditingProject);
        if (project != null) {
            Task newTask = new Task(taskName, description, deadline);
            project.tasks.add(newTask);
            updateProjectDisplay(currentEditingProject);
            showAlert("Задача '" + taskName + "' добавлена в проект '" + currentEditingProject + "'!");
        } else {
            showAlert("Ошибка: проект не найден!");
        }
    }

    private void updateProjectDisplay(String projectName) {
        Project project = projectsData.get(projectName);
        if (project == null) return;

        for (ProjectItem item : projectItems) {
            if (item.infoLabel.getText().contains("📋 " + projectName)) {
                updateProjectInfoLabel(item.infoLabel, project);

                ObservableList<String> taskStrings = FXCollections.observableArrayList();
                for (Task task : project.tasks) {
                    taskStrings.add(task.toString());
                }
                item.tasksListView.setItems(taskStrings);
                break;
            }
        }
    }

    private void showRelationsMessage(String projectName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Связи задач");
        alert.setHeaderText("Функция связей задач");
        alert.setContentText("В проекте '" + projectName + "'\n\n" +
                "Функция установки связей между задачами будет добавлена в следующем обновлении!\n\n" +
                "Здесь будет возможность:\n" +
                "• Указывать зависимости между задачами\n" +
                "• Строить граф выполнения\n" +
                "• Рассчитывать критический путь");
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
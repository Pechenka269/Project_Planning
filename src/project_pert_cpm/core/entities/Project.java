package core.entities;

import data_structures.interfaces.Graph;
import data_structures.interfaces.List;
import data_structures.implementations.AdjacencyListGraph;
import data_structures.implementations.ArrayList;


public class Project {
    private String name;
    private Graph<Task> taskGraph;

    public Project(String name) {
        this.name = name;
        this.taskGraph = new AdjacencyListGraph<>();
    }


    public void addTask(Task task) {
        taskGraph.addVertex(task);
    }


    public void addDependency(Task from, Task to) {
        if (!taskGraph.hasVertex(from)) {
            addTask(from);
        }
        if (!taskGraph.hasVertex(to)) {
            addTask(to);
        }
        taskGraph.addEdge(from, to);
    }


    public List<Task> getAllTasks() {
        return taskGraph.getAllVertices();
    }


    public List<Task> getDependentTasks(Task task) {
        return taskGraph.getNeighbors(task);
    }


    public List<Task> getDependencies(Task task) {
        return taskGraph.getIncomingNeighbors(task);
    }


    public boolean hasDependency(Task from, Task to) {
        return taskGraph.hasEdge(from, to);
    }


    public List<Task> getStartTasks() {
        List<Task> startTasks = new ArrayList<>();
        List<Task> allTasks = getAllTasks();
        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            if (getDependencies(task).isEmpty()) {
                startTasks.add(task);
            }
        }
        return startTasks;
    }

    public List<Task> getEndTasks() {
        List<Task> endTasks = new ArrayList<>();
        List<Task> allTasks = getAllTasks();
        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            if (getDependentTasks(task).isEmpty()) {
                endTasks.add(task);
            }
        }
        return endTasks;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Graph<Task> getTaskGraph() {
        return taskGraph;
    }

    public int getTaskCount() {
        return getAllTasks().size();
    }


    public boolean isValid() {
        // нужно добавить проверку на циклы
        return getTaskCount() > 0;
    }

    @Override
    public String toString() {
        return "Project: " + name + " (Tasks: " + getTaskCount() + ")";
    }
}
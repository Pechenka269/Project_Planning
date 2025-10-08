package core.algorithms;

import core.entities.Project;
import core.entities.Task;
import data_structures.interfaces.List;
import data_structures.interfaces.Stack;
import data_structures.implementations.ListStack;
import data_structures.implementations.ArrayList;


public class PertCpmCalculator {

    public void calculate(Project project) {
        if (!project.isValid()) {
            throw new IllegalArgumentException("Проект невалиден");
        }

        calculateEarlyTimes(project);
        calculateLateTimes(project);
        calculateSlack(project);
    }


    private void calculateEarlyTimes(Project project) {
        List<Task> startTasks = project.getStartTasks();
        Stack<Task> stack = new ListStack<>();


        for (int i = 0; i < startTasks.size(); i++) {
            Task task = startTasks.get(i);
            task.setEarlyStart(0);
            task.setEarlyFinish(task.getDuration());
            stack.push(task);
        }


        while (!stack.isEmpty()) {
            Task current = stack.pop();
            List<Task> dependentTasks = project.getDependentTasks(current);

            for (int i = 0; i < dependentTasks.size(); i++) {
                Task dependent = dependentTasks.get(i);


                if (dependent.getEarlyStart() < current.getEarlyFinish()) {
                    dependent.setEarlyStart(current.getEarlyFinish());
                    dependent.setEarlyFinish(dependent.getEarlyStart() + dependent.getDuration());
                }

                stack.push(dependent);
            }
        }
    }


    private void calculateLateTimes(Project project) {
        List<Task> endTasks = project.getEndTasks();

        int projectDuration = 0;
        for (int i = 0; i < endTasks.size(); i++) {
            Task task = endTasks.get(i);
            if (task.getEarlyFinish() > projectDuration) {
                projectDuration = task.getEarlyFinish();
            }
        }

        for (int i = 0; i < endTasks.size(); i++) {
            Task task = endTasks.get(i);
            task.setLateFinish(projectDuration);
            task.setLateStart(task.getLateFinish() - task.getDuration());
        }

        List<Task> allTasks = project.getAllTasks();
        for (int i = allTasks.size() - 1; i >= 0; i--) {
            Task current = allTasks.get(i);
            List<Task> dependencies = project.getDependencies(current);

            for (int j = 0; j < dependencies.size(); j++) {
                Task dependency = dependencies.get(j);
                if (dependency.getLateFinish() == 0 ||
                        dependency.getLateFinish() > current.getLateStart()) {
                    dependency.setLateFinish(current.getLateStart());
                    dependency.setLateStart(dependency.getLateFinish() - dependency.getDuration());
                }
            }
        }
    }


    private void calculateSlack(Project project) {
        List<Task> allTasks = project.getAllTasks();
        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            int slack = task.getLateStart() - task.getEarlyStart();
            task.setSlack(slack);
        }
    }


    public List<Task> getCriticalPath(Project project) {
        List<Task> criticalPath = new ArrayList<>();
        List<Task> allTasks = project.getAllTasks();

        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            if (task.getSlack() == 0) {
                criticalPath.add(task);
            }
        }

        return criticalPath;
    }


    public int getProjectDuration(Project project) {
        List<Task> endTasks = project.getEndTasks();
        int maxDuration = 0;

        for (int i = 0; i < endTasks.size(); i++) {
            Task task = endTasks.get(i);
            if (task.getEarlyFinish() > maxDuration) {
                maxDuration = task.getEarlyFinish();
            }
        }

        return maxDuration;
    }
}
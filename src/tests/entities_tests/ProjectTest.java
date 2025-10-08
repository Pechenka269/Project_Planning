package entities_tests;

import core.entities.Project;
import core.entities.Task;
import data_structures.interfaces.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    public void testCreateProject() {
        Project project = new Project("Website Development");
        assertEquals("Website Development", project.getName());
        assertEquals(0, project.getTaskCount());
        assertTrue(project.getAllTasks().isEmpty());
    }

    @Test
    public void testAddTasks() {
        Project project = new Project("Test Project");

        Task task1 = new Task("1", "Design", 5);
        Task task2 = new Task("2", "Develop", 10);

        project.addTask(task1);
        project.addTask(task2);

        assertEquals(2, project.getTaskCount());
        assertTrue(project.getAllTasks().contains(task1));
        assertTrue(project.getAllTasks().contains(task2));
    }

    @Test
    public void testAddDependencies() {
        Project project = new Project("Test Project");

        Task design = new Task("1", "Design", 5);
        Task develop = new Task("2", "Develop", 10);
        Task test = new Task("3", "Test", 3);

        project.addDependency(design, develop);
        project.addDependency(develop, test);

        assertTrue(project.hasDependency(design, develop));
        assertTrue(project.hasDependency(develop, test));
        assertFalse(project.hasDependency(design, test));

        assertEquals(1, project.getDependencies(develop).size());
        assertEquals(design, project.getDependencies(develop).get(0));

        assertEquals(1, project.getDependentTasks(design).size());
        assertEquals(develop, project.getDependentTasks(design).get(0));
    }

    @Test
    public void testStartAndEndTasks() {
        Project project = new Project("Test Project");

        Task a = new Task("1", "A", 2);
        Task b = new Task("2", "B", 3);
        Task c = new Task("3", "C", 1);

        project.addDependency(a, b);
        project.addDependency(b, c);

        List<Task> startTasks = project.getStartTasks();
        assertEquals(1, startTasks.size());
        assertEquals(a, startTasks.get(0));

        List<Task> endTasks = project.getEndTasks();
        assertEquals(1, endTasks.size());
        assertEquals(c, endTasks.get(0));
    }

    @Test
    public void testProjectValidity() {
        Project project = new Project("Valid Project");

        Task task = new Task("1", "Task", 5);
        project.addTask(task);

        assertTrue(project.isValid());
    }
}
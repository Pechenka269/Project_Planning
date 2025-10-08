package algorithms_tests;

import core.entities.Project;
import core.entities.Task;
import core.algorithms.PertCpmCalculator;
import data_structures.interfaces.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PertCpmCalculatorTest {

    @Test
    public void testSimpleProject() {
        Project project = new Project("Test Project");

        Task a = new Task("1", "A", 2);
        Task b = new Task("2", "B", 3);
        Task c = new Task("3", "C", 1);

        project.addDependency(a, b);
        project.addDependency(b, c);

        PertCpmCalculator calculator = new PertCpmCalculator();
        calculator.calculate(project);


        assertEquals(6, calculator.getProjectDuration(project));


        List<Task> criticalPath = calculator.getCriticalPath(project);
        assertEquals(3, criticalPath.size());
        assertEquals(a, criticalPath.get(0));
        assertEquals(b, criticalPath.get(1));
        assertEquals(c, criticalPath.get(2));
    }

    @Test
    public void testTaskTimings() {
        Project project = new Project("Test Project");

        Task a = new Task("1", "A", 5);
        Task b = new Task("2", "B", 3);

        project.addDependency(a, b);

        PertCpmCalculator calculator = new PertCpmCalculator();
        calculator.calculate(project);

        // Проверяем ранние сроки
        assertEquals(0, a.getEarlyStart());
        assertEquals(5, a.getEarlyFinish());
        assertEquals(5, b.getEarlyStart());
        assertEquals(8, b.getEarlyFinish());

        // Проверяем резервы
        assertEquals(0, a.getSlack());
        assertEquals(0, b.getSlack());
    }
}
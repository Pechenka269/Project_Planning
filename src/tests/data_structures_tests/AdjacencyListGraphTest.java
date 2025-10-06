package data_structures_tests;

import core.entities.Task;
import data_structures.implementations.AdjacencyListGraph;
import data_structures.interfaces.Graph;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import data_structures.interfaces.List;

public class AdjacencyListGraphTest {

    @Test
    public void testAddVertexAndEdges() {
        Graph<String> graph = new AdjacencyListGraph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");

        assertTrue(graph.hasVertex("A"));
        assertTrue(graph.hasVertex("B"));
        assertEquals(3, graph.getVertexCount());

        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");

        assertTrue(graph.hasEdge("A", "B"));
        assertTrue(graph.hasEdge("B", "C"));
        assertTrue(graph.hasEdge("A", "C"));
        assertFalse(graph.hasEdge("C", "A"));
        assertEquals(3, graph.getEdgeCount());
    }

    @Test
    public void testNeighbors() {
        Graph<String> graph = new AdjacencyListGraph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "C");

        List<String> neighborsOfA = graph.getNeighbors("A");
        assertEquals(2, neighborsOfA.size());
        assertTrue(neighborsOfA.contains("B"));
        assertTrue(neighborsOfA.contains("C"));

        List<String> incomingToC = graph.getIncomingNeighbors("C");
        assertEquals(2, incomingToC.size());
        assertTrue(incomingToC.contains("A"));
        assertTrue(incomingToC.contains("B"));
    }

    @Test
    public void testTaskGraph() {
        Graph<Task> taskGraph = new AdjacencyListGraph<>();

        Task design = new Task("1","Design", 5);
        Task develop = new Task("2","Develop", 10);
        Task test = new Task("3", "Test", 3);

        taskGraph.addEdge(design, develop);
        taskGraph.addEdge(develop, test);

        assertTrue(taskGraph.hasEdge(design, develop));
        assertTrue(taskGraph.hasEdge(develop, test));

        List<Task> developDependencies = taskGraph.getIncomingNeighbors(develop);
        assertEquals(1, developDependencies.size());
        assertEquals(design, developDependencies.get(0));
    }
}
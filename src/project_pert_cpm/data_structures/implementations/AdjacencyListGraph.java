package data_structures.implementations;

import data_structures.interfaces.Graph;
import data_structures.interfaces.List;

public class AdjacencyListGraph<T> implements Graph<T> {
    private List<VertexInfo<T>> adjacencyList;

    private int edgeCount;

    private static class VertexInfo<T> {
        T vertex;
        List<T> neighbors;

        VertexInfo(T vertex) {
            this.vertex = vertex;
            this.neighbors = new ArrayList<>();
        }
    }

    public AdjacencyListGraph() {
        this.adjacencyList = new ArrayList<>();
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(T vertex) {
        if (hasVertex(vertex)) {
            return;
        }

        VertexInfo<T> vertexInfo = new VertexInfo<>(vertex);
        adjacencyList.add(vertexInfo);
    }

    @Override
    public boolean hasVertex(T vertex) {
        for (int i = 0; i < adjacencyList.size(); i++) {
            VertexInfo<T> info = adjacencyList.get(i);
            if (info.vertex.equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<T> getAllVertices() {
        List<T> vertices = new ArrayList<>();
        for (int i = 0; i < adjacencyList.size(); i++) {
            vertices.add(adjacencyList.get(i).vertex);
        }
        return vertices;
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    @Override
    public void addEdge(T from, T to) {
        addVertex(from);
        addVertex(to);

        VertexInfo<T> fromInfo = findVertexInfo(from);
        if (fromInfo == null) {
            throw new IllegalStateException("Вершина 'from' не найдена");
        }

        fromInfo.neighbors.add(to);
        edgeCount++;
    }

    @Override
    public boolean hasEdge(T from, T to) {
        VertexInfo<T> fromInfo = findVertexInfo(from);
        if (fromInfo == null) {
            return false;
        }

        return fromInfo.neighbors.contains(to);
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        VertexInfo<T> vertexInfo = findVertexInfo(vertex);
        if (vertexInfo == null) {
            return new ArrayList<>();
        }

        List<T> neighborsCopy = new ArrayList<>();
        for (int i = 0; i < vertexInfo.neighbors.size(); i++) {
            neighborsCopy.add(vertexInfo.neighbors.get(i));
        }
        return neighborsCopy;
    }

    @Override
    public List<T> getIncomingNeighbors(T vertex) {
        List<T> incoming = new ArrayList<>();

        for (int i = 0; i < adjacencyList.size(); i++) {
            VertexInfo<T> info = adjacencyList.get(i);
            if (info.neighbors.contains(vertex)) {
                incoming.add(info.vertex);
            }
        }
        return incoming;
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    private VertexInfo<T> findVertexInfo(T vertex) {
        for (int i = 0; i < adjacencyList.size(); i++) {
            VertexInfo<T> info = adjacencyList.get(i);
            if (info.vertex.equals(vertex)) {
                return info;
            }
        }
        return null;
    }
}
package data_structures.interfaces;


public interface Graph<T> {
    void addVertex(T vertex);
    boolean hasVertex(T vertex);
    List<T> getAllVertices();

    void addEdge(T from, T to);
    boolean hasEdge(T from, T to);
    List<T> getNeighbors(T vertex);

    List<T> getIncomingNeighbors(T vertex);
    int getVertexCount();
    int getEdgeCount();
}
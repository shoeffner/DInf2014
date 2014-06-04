/**
 * 
 */
package main;

/**
 * @author Sebastian Höffner
 *
 */
public class Vertex {

    private final String name;
    private boolean visited = false;
    
    /**
     * Creates a vertex.
     * @param name name of the vertex
     */
    public Vertex(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vertex: " + name;
    }
    
    /**
     * Returns the vertex' name.
     * @return vertex' name
     */
    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vertex) {
            return ((Vertex) obj).name.equals(this.name);
        }
        return false;
    }
    
    /**
     * Sets the <tt>visited</tt> flag true.
     */
    public void visit() {
        visited = true;
    }
    
    /**
     * Sets the <tt>visited</tt> flag false.
     */
    public void unvisit() {
        visited = false;
    }
    
    /**
     * Returns the <tt>visited</tt> flag.
     * @return true if the vertex is marked as visited.
     */
    public boolean visited() {
        return visited;
    }
}

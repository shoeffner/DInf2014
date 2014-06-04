/**
 * 
 */
package main;

/**
 * @author Sebastian Höffner
 *
 */
public class Arc {

    private Vertex from;
    private Vertex to;
    
    private boolean hidden = false;
    
    /**
     * @param vertex
     * @param to
     */
    public Arc(Vertex from, Vertex to) {
        this.from = from;
        this.to   = to;
    }
    
    /**
     * Returns the vertex at which this arcs starts.
     * @return from vertex
     */
    public Vertex from() {
        return from;
    }
    
    /**
     * Returns the vertex at which this arc ends.
     * @return to vertex
     */
    public Vertex to() {
        return to;
    }
    
    /**
     * Returns <tt>{@link Arc#from()}.{@link Vertex#getName() getName()} -> {@link Arc#to()}.{@link Vertex#getName() getName()}</tt>.
     */
    public String toString() {
        return from.getName() + " -> " + to.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Arc) {
            return ((Arc) obj).from.equals(from) && ((Arc) obj).to.equals(to); 
        }
        return false;
    }

    /**
     * Returns the <tt>hidden</tt> flag.
     * @return true if the arc is marked as hidden
     */
    public boolean hidden() {
        return hidden;
    }
    
    /**
     * Sets the <tt>hidden</tt> flag true.
     */
    public void hide() {
        hidden = true;
    }

    /**
     * Sets the <tt>hidden</tt> flag false.
     */
    public void unhide() {
        hidden = false;
    }

    /**
     * Replaces the start vertex.
     * @param to new vertex
     */
    public void setTo(Vertex to) {
        this.to = to;
    }
    
    /**
     * Replaces the end vertex.
     * @param from new vertex
     */
    public void setFrom(Vertex from) {
        this.from = from;
    }
}

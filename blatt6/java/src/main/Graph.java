/**
 * 
 */
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sebastian Höffner, Andrea Suckro
 *
 */
public class Graph {
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private ArrayList<Arc> arcs = new ArrayList<Arc>();
    
    private List<Vertex> cycle = new ArrayList<Vertex>();

    /**
     * Creates a graph from a file.<br />
     * The file should contain only vertex names separated by spaces. You can either define single arcs, e.g.
     * <br /><tt>A B</tt><br />
     * for an arc from A to B, or longer chains, e.g.
     * <br /><tt>A B C D</tt><br />
     * for arcs from A to B, B to C, and C to D.<br />
     * The process will check if vertices are already in the graph and adapt the graph accordingly, i.e.
     * <br /><tt>A B
     * <br />A B</tt><br />
     * would be valid and only create one arc.<br />
     * All mentioned vertices will created if they don't exist in the graph yet.
     * 
     * @param filename the file name
     */
    public Graph(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            for(String line : br.lines().collect(Collectors.toList())) {
                String[] edge = line.split(" ");
                for(String vs : edge) {
                    if(vertices.stream().noneMatch(v -> v.getName().equals(vs))) {
                        Vertex v = new Vertex(vs);
                        vertices.add(v);
                    }
                }
                if(edge.length > 1) {
                    
                    for(int i = 0; i < edge.length-1; i++) {
                        final int idx = i;
                        Arc a = new Arc(vertices.stream().filter(v -> v.getName().equals(edge[idx])).findFirst().get(), 
                                        vertices.stream().filter(v -> v.getName().equals(edge[idx+1])).findFirst().get());
                        if(!arcs.contains(a)) {
                            arcs.add(a);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns all incoming arcs of Vertex v.
     * @param v Vertex to check.
     * @return the list of incoming arcs.
     */
    public List<Arc> getIngoingArcs(Vertex v) {
        return arcs.stream().filter(a -> a.to().equals(v) && !a.hidden()).collect(Collectors.toList());
    }
    
    /**
     * Returns all outgoing arcs of Vertex v.
     * @param v Vertex to check.
     * @return the list of outgoing arcs.
     */
    public List<Arc> getOutgoingArcs(Vertex v) {
        return arcs.stream().filter(a -> a.from().equals(v) && !a.hidden()).collect(Collectors.toList());
    }
    
    /**
     * Returns a list of all vertices in the graph.
     * @return a list of all vertices
     */
    public List<Vertex> getVertices() {
        return vertices;
    }
   
    /**
     * Contracts the cycle given as a parameter.
     * @param cycle a list which describes a cycle
     */
    public void contractCycle(List<Vertex> cycle) {
        
        // TODO contract cycle
        
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph: \n");
        arcs.forEach(a -> sb.append(" " + a + "\n"));
        return sb.toString();
    }

    /**
     * Returns a list containing the cycle, if one was found. Otherwise the list is empty.
     * @return the current found cycle
     */
    public List<Vertex> getCycle() {
        return cycle;
    }

    /**
     * Searches for a cycle in the graph. This can be retrieved with {@link Graph#getCycle()}.
     * @return true if a cycle was found
     */
    public boolean findCycles() {
        cycle.clear();

        // TODO Find Cycle(s)
        
        return hasCycle();
    }

    /**
     * Returns true if there is a cycle stored.
     * @return true if there is a cycle
     */
    public boolean hasCycle() {
        return cycle.size() > 0;
    }
    
}
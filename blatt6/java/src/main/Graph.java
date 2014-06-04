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
     * Contracts the cycle given as a parameter. Therefor a new vertex gets 
     * created and placed into the graph at the position of the cycle. <br />
     * <br />
     * The graph's <br />
     * <tt>A -> B -> D<br />
     * B -> C -> B</tt><br />
     * cycle <tt>B -> C -> B</tt> would get reduced to a vertex <tt>BC</tt> 
     * which get's inserted into the graph, resulting in:<br />
     * <tt>A -> BC -> D</tt>
     * 
     * @param cycle a list which describes the cycle
     */
    public void contractCycle(List<Vertex> cycle) {
        // generate vertex name
        String name = "";
        for(Vertex v : cycle) {
            name += v.getName();
        }
        
        // generate new Vertex
        Vertex replacement = new Vertex(name);

        // unhide arcs to work with them
        arcs.forEach(Arc::unhide);
        cycle.stream().forEach(v -> getIngoingArcs(v).forEach(a -> a.setTo(replacement)));
        cycle.stream().forEach(v -> getOutgoingArcs(v).forEach(a -> a.setFrom(replacement)));
        
        arcs.removeIf(a -> a.from().equals(a.to()));
        vertices.removeAll(cycle);
        vertices.add(replacement);
    }
    
    @Override
    public String toString() {
        // find single vertices
        arcs.forEach(Arc::unhide);
        List<Vertex> solo = vertices.stream().filter(v -> getIngoingArcs(v).size() == 0 && getOutgoingArcs(v).size() == 0).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append("Graph: \n");
        solo.forEach(v -> sb.append(" " + v + "\n"));
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
        
        // unhide and unvisit all vertices and arcs
        vertices.forEach(Vertex::unvisit);
        arcs.forEach(Arc::unhide);

        // "remove" vertices without either incoming or outgoing arcs until there are none left
        List<Vertex> edgeVertices;
        do {
            edgeVertices = vertices.stream()
                                   .filter(v -> !v.visited() 
                                             && (getIngoingArcs(v).size() == 0 
                                                 || getOutgoingArcs(v).size() == 0))
                                   .collect(Collectors.toList());
            // hide arcs and visit vertices
            for(Vertex v : edgeVertices) {
                getIngoingArcs(v).forEach(Arc::hide);
                getOutgoingArcs(v).forEach(Arc::hide);
                v.visit();
            }
        } while(edgeVertices.size() > 0);
        
        // pick one node at random, follow the path until you find a circle
        List<Vertex> remainingVertices = vertices.stream().filter(v -> !v.visited()).collect(Collectors.toList());
        
        // no cycles:
        if(remainingVertices.size() == 0) {
            return false;
        }
        
        // start traversing until you find a cycle
        ArrayList<Vertex> visited = new ArrayList<Vertex>();
        
        Vertex current = remainingVertices.get(0);
        while(!visited.contains(current)) {
            visited.add(current);
            current = getOutgoingArcs(current).get(0).to();
        }

        cycle = visited.subList(visited.indexOf(current), visited.size());
        
        return true;
    }

    /**
     * Returns true if there is a cycle stored.
     * @return true if there is a cycle
     */
    public boolean hasCycle() {
        return cycle.size() > 0;
    }
    
}
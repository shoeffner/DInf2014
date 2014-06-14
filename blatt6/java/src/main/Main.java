/**
 * 
 */
package main;

/**
 * @author Sebastian Höffner, Andrea Suckro
 * 
 */
public class Main {
    public static void main(String[] args) {
        Graph[] graphs = new Graph[]{
                new Graph("singleVertex.txt"),
                new Graph("noCycle.txt"),
                new Graph("simpleCycle.txt"),
                new Graph("complexCycle.txt"),
                new Graph("multiCycle.txt"),
                new Graph("aufg6_4.txt")
        };
        
        for(Graph g : graphs) {
            System.out.println(g);
            g.reduce();
            System.out.println("Reduced " + g);
        }
    }
}

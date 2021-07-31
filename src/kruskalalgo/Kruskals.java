
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Kruskals {

    public static class Edge implements Comparable<Edge> {

        int x;
        int y;
        int weight;

        public Edge(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        public String toString() {
            return this.x + " " + this.y + " " + this.weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }
    /**
     * @param args the command line arguments
     */
    static HashMap<String, Integer> vertices = new HashMap();
    private static ArrayList<ArrayList<Integer>> adj;
    static int sumOfAllDistances = 0;
    static PriorityQueue<Edge> edgesQueue = new PriorityQueue<Edge>();
    static ArrayList dsets;

    public static void main(String[] args) throws IOException {
        
        adj = new ArrayList<>(21);
        for (int i = 0; i < 21; i++) {
            adj.add(new ArrayList<Integer>());
        }

        String line = "";
        try {
            Scanner sc = new Scanner(new File("assn9_data.csv"));

            sc.useDelimiter("\n");
            int countVertices = 1;
            while (sc.hasNext()) {
                String graphVertices[] = sc.next().split(",");
                int count = 0;
                String vertex = graphVertices[count];
                if (!vertices.containsKey(vertex)) {
                    vertices.put(vertex, vertices.size());
                }
                for (int i = 1; i <= 3; i++) {
                    count++;

                    String vertex1 = graphVertices[count];
                    if (vertex1.equals("")) {
                        break;
                    }
                    if (!vertices.containsKey(vertex1)) {
                        vertices.put(vertex1, vertices.size());
                    }
                    count++;
                    int weight = Integer.parseInt(graphVertices[count]);

                    adj.get((int) vertices.get(vertex1)).add((int) vertices.get(vertex));
                    adj.get((int) vertices.get(vertex)).add((int) vertices.get(vertex1));

                    Edge edge = new Edge(vertices.get(vertex), vertices.get(vertex1), weight);
                    edgesQueue.add(edge);
                    
                }
            }
            Iterator iter = edgesQueue.iterator();
            while(iter.hasNext()){
                Edge ed = (Edge)iter.next();
                sumOfAllDistances += ed.weight;
            }
            kruskalsAlgo(vertices.size(), edgesQueue.size(), edgesQueue, adj);
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void kruskalsAlgo(int numVertices, int numEdges, PriorityQueue<Edge> edges, ArrayList<ArrayList<Integer>> graph) {
        dsets = new ArrayList<>(graph.size());

        int[][] mst = new int[numVertices][numVertices];
     
        for (int vertex = 1; vertex < graph.size(); vertex++) {
          
            dsets.add(new DisjSets(graph.size()));

        }

        int edgeCounter = 0;
        int edgedTaken = 1;
        
        while (edgedTaken <= numVertices - 1) {
            Edge e = edgesQueue.poll();
            edgeCounter++;
            
            if (isCyclic(e.x, e.y, (DisjSets) dsets.get(edgeCounter-1))) {
                continue;
            }
          
            DisjSets dset = (DisjSets) dsets.get(edgeCounter-1);
            dset.union(dset.find(e.x), dset.find(e.y));
            mst[e.x][e.y] = e.weight;
            edgedTaken++;
        }

        /* tree display */
        int mstSum = 0;
        for (int u = 1; u < mst.length; u++) {
            for (int v = 0; v < mst.length; v++) {
                if (mst[u][v] != 0) {
                    System.out.println(getKeysByValue(vertices, u) + " " + getKeysByValue(vertices, v) + " " + mst[u][v]);
                    mstSum += mst[u][v];
                }
            }
        }
        System.out.println("Sum of all distances in tree :" + mstSum);

    }

    public static boolean isCyclic(int u, int v, DisjSets parents) {
      
        return parents.find(u) == parents.find(v);
    }



    // Method for getting name of vertex key by using vertex node value

    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {

        Set<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
}

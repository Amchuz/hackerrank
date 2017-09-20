import java.io.*;
import java.util.*;

public class Solution {

    private int nodes;
    private int edges;
    private LinkedList<Integer>[] adjacencyLists;

    public Solution(int nodes, int edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.adjacencyLists = new LinkedList[nodes];
        for (int i = 0; i < nodes; i++) {
            adjacencyLists[i] = new LinkedList<Integer>();
        }
    }

    public void addEdge(int nodeA, int nodeB) {
        if (!this.adjacencyLists[nodeA].contains(nodeB)) {
            this.adjacencyLists[nodeA].add(nodeB);
        }
        if (!this.adjacencyLists[nodeB].contains(nodeA)) {
            this.adjacencyLists[nodeB].add(nodeA);
        }
    }

    public String distancesFrom(int startNode) {
        Map<Integer, Integer> visited = breadthFirstSearch(startNode);
        List<Integer> distancesFrom = new ArrayList<Integer>();
        for (int i = 0; i < this.nodes; i++) {
            if (i == startNode) {
                continue;
            } else if (visited.containsKey(i)) {
                distancesFrom.add(visited.get(i));
            } else {
                distancesFrom.add(-1);
            }
        }
        return spaceDelimit(distancesFrom);
    }

    public String spaceDelimit(List<Integer> list) {
        StringJoiner joiner = new StringJoiner(" ");
        for (Integer i : list) {
            joiner.add(i.toString());
        }
        return joiner.toString();
    }

    private Map<Integer, Integer> breadthFirstSearch(int startNode) {
        Map<Integer, Integer> visited = new HashMap<Integer, Integer>();
        Queue<Integer> nodesToVisit = new LinkedList<Integer>();
        Queue<Integer> nodesToVisitDepth = new LinkedList<Integer>();

        nodesToVisit.add(startNode);
        nodesToVisitDepth.add(0);

        while (nodesToVisit.size() != 0) {
            int currentNode = nodesToVisit.poll();
            int currentDepth = nodesToVisitDepth.poll();
            for (int neighbor : this.adjacencyLists[currentNode]){
                if (!visited.containsKey(neighbor)) {
                    nodesToVisit.add(neighbor);
                    nodesToVisitDepth.add(currentDepth + 6);
                }
            }
            if (!visited.containsKey(currentNode) || visited.get(currentNode) > currentDepth) {
                visited.put(currentNode, currentDepth);
            }
        }
        return visited;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int queries = in.nextInt();
        for (int query = 0; query < queries; query++) {
            int nodes = in.nextInt();
            int edges = in.nextInt();
            Solution graph = new Solution(nodes, edges);
            for (int edge = 0; edge < edges; edge++) {
                int nodeA = in.nextInt();
                int nodeB = in.nextInt();
                graph.addEdge(nodeA - 1, nodeB - 1);
            }
            int startNode = in.nextInt();
            System.out.println(graph.distancesFrom(startNode - 1));
        }
    }
}

package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dijkstra {
    private Map<String, Node> nodes = new HashMap<>();
    private int numVertices, numEdges;
    private File file;
    private String ShortPath = "";
    private double ShortPathWeight = 0;
    List<Edge> edges = new ArrayList<>();
    String[] shortestPathNodes;



    public void read(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(file);
        String d= scanner.nextLine().trim();
        String[] arr= d.split(",");
        numVertices = Integer.parseInt(arr[0].trim());
        numEdges = Integer.parseInt(arr[1].trim());
        for (int i = 0; i < numVertices; i++) {
            String line = scanner.nextLine().trim();
            String [] arr1 = line.split(",");
            String name = arr1[0].trim();
            double x = Double.parseDouble(arr1[1].trim());
            double y = Double.parseDouble(arr1[2].trim());

            Node node = new Node(name, x, y);
            nodes.put(name, node);
        }

        for (int i = 0; i < numEdges; i++) {
            String line = scanner.nextLine().trim();
            String [] arr2 = line.split(",");
            String from = arr2[0].trim();
            String to = arr2[1].trim();
            Double wht = Double.parseDouble(arr2[2].trim());
            Node fromNode = nodes.get(from);
            Node toNode = nodes.get(to);

            fromNode.addEdge(toNode, wht);
            edges.add(new Edge(fromNode, toNode, wht));
        }




    }

    public void ClacDijkstra(String source, String destination) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        //// A map to store the previous node of every node in the shortest path from the source node
        PriorityQueue<Node> queue = new PriorityQueue<>((Node n1, Node n2) -> Double.compare(distances.get(n1), distances.get(n2)));
        Map<Node, Boolean> visited = new HashMap<>();

        Node sourceNode = nodes.get(source);
        if (sourceNode == null) {
            System.out.println("Error: Source node not found in the graph");
            return;
        }

        distances.put(sourceNode, 0.0);
        queue.add(sourceNode);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visited.put(current, true);

            if (current.name.equals(destination)) {
                break;
            }

            for (Edge edge : current.edges) {
                Node neighbor = edge.to;
                if (!visited.containsKey(neighbor)) {
                    distances.put(neighbor, Double.POSITIVE_INFINITY);
                    previous.put(neighbor, null);
                    visited.put(neighbor, false);

                }

                double newDistance = distances.get(current) + edge.weight;
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        Node destNode = nodes.get(destination);
        if (destNode == null) {
            System.out.println("Error: Destination node not found in the graph");
            return;
        }

        Node current = destNode;
        Stack<String> stack = new Stack<>();
        while (current != null) {
            if (current != null) {
                stack.push(current.name);
            }
            current = previous.get(current);
        }
        shortestPathNodes = new String[stack.size()];
        for (int i = 0; i < shortestPathNodes.length; i++) {
            shortestPathNodes[i] = stack.pop();
        }

        for(int i =0; i < shortestPathNodes.length; i++){
            if(i == shortestPathNodes.length - 1){
                ShortPath += shortestPathNodes[i]+"(end)";
            }
            else{
                ShortPath += shortestPathNodes[i] + " -> \n";
            }

        }

        this.ShortPathWeight = distances.get(destNode);

        distances.clear();
        previous.clear();
        visited.clear();
    }
//public void runDijkstra(String source, String destination) {
//    Map<Node, Double> distances = new HashMap<>();
//    Map<Node, Node> previous = new HashMap<>();
//    PriorityQueue<Node> queue = new PriorityQueue<>((Node n1, Node n2) -> Double.compare(distances.get(n1), distances.get(n2)));
//    Map<Node, Boolean> visited = new HashMap<>();
//
//    Node sourceNode = nodes.get(source);
//    if (sourceNode == null) {
//        System.out.println("Error: Source node not found in the graph");
//        return;
//    }
//
//    // Set the initial distance for the source node to 0
//    distances.put(sourceNode, 0.0);
//    queue.add(sourceNode);
//    while (!queue.isEmpty()) {
//        Node current = queue.poll();
//        visited.put(current, true);
//
//        if (current.name.equals(destination)) {
//            break;
//        }
//
//        for (Edge edge : current.edges) {
//            Node neighbor = edge.to;
//            if (!visited.containsKey(neighbor)) {
//                distances.put(neighbor, Double.POSITIVE_INFINITY);
//                previous.put(neighbor, null);
//                visited.put(neighbor, false);
//            }
//
//            double newDistance = distances.get(current) + edge.weight;
//            if (newDistance < distances.get(neighbor)) {
//                distances.put(neighbor, newDistance);
//                previous.put(neighbor, current);
//                queue.add(neighbor);
//            }
//        }
//    }
//
//    Node destNode = nodes.get(destination);
//    if (destNode == null) {
//        System.out.println("Error: Destination node not found in the graph");
//        return;
//    }
//
//    Node current = destNode;
//    Stack<String> stack = new Stack<>();
//    while (current != null) {
//        stack.push(current.name);
//        current = previous.get(current);
//    }
//
//    shortestPathNodes = new String[stack.size()];
//    for (int i = 0; i < shortestPathNodes.length; i++) {
//        shortestPathNodes[i] = stack.pop();
//    }
//
//    for(int i = 0; i < shortestPathNodes.length; i++){
//        if(i == shortestPathNodes.length - 1){
//            ShortPath += shortestPathNodes[i];
//        }
//        else{
//            ShortPath += shortestPathNodes[i] + " -> ";
//        }
//    }
//    this.ShortPathWeight = distances.get(destNode);
//
//}
//


    public Map<String, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void setNumEdges(int numEdges) {
        this.numEdges = numEdges;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getShortPath() {
        return ShortPath;
    }

    public void setShortPath(String shortPath) {
        ShortPath = shortPath;
    }

    public double getShortPathWeight() {
        return ShortPathWeight;
    }

    public void setShortPathWeight(double shortPathWeight) {
        ShortPathWeight = shortPathWeight;
    }


}
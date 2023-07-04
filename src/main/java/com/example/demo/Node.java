package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Node {
    String name;
    double x;
    double y;
    List<Edge> edges;

    public Node(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        edges = new ArrayList<>();
    }

    public void addEdge(Node to, double weight) {
        edges.add(new Edge(this, to, weight));
    }

}

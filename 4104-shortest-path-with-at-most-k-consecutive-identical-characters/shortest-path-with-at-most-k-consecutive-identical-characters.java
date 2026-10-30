import java.util.*;

class Solution {

    static class Edge {
        int node;
        int weight;

        Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    static class State {
        int node;
        int count;
        long distance;

        State(int node, int count, long distance) {
            this.node = node;
            this.count = count;
            this.distance = distance;
        }
    }

    public int shortestPath(int n, int[][] edges, String labels, int k) {

        // Step 1: Create adjacency list
        ArrayList<Edge>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {

            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];

            graph[u].add(new Edge(v, w));
        }

        // Step 2: Distance array
        long[][] dist = new long[n][k + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        // Step 3: Priority Queue
        PriorityQueue<State> pq = new PriorityQueue<>(
            (a, b) -> Long.compare(a.distance, b.distance)
        );

        // Starting node has one occurrence of its character
        dist[0][1] = 0;

        pq.offer(new State(0, 1, 0));

        // Step 4: Dijkstra Algorithm
        while (!pq.isEmpty()) {

            State current = pq.poll();

            int u = current.node;
            int count = current.count;
            long d = current.distance;

            // Ignore outdated states
            if (d != dist[u][count]) {
                continue;
            }

            // Destination reached
            if (u == n - 1) {
                return (int) d;
            }

            // Explore neighbours
            for (Edge edge : graph[u]) {

                int v = edge.node;
                int w = edge.weight;

                int newCount;

                // Same consecutive character
                if (labels.charAt(u) == labels.charAt(v)) {
                    newCount = count + 1;
                }

                // Different character
                else {
                    newCount = 1;
                }

                // Invalid transition
                if (newCount > k) {
                    continue;
                }

                long newDistance = d + w;

                // Relaxation
                if (newDistance < dist[v][newCount]) {

                    dist[v][newCount] = newDistance;

                    pq.offer(
                        new State(v, newCount, newDistance)
                    );
                }
            }
        }

        // No valid path exists
        return -1;
    }
}
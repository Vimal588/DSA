import java.util.*;

class Solution {

List<Integer>[] adj;
int[] tin, tout, depth;
int[][] up;
int[] bit;
char[] chars;
int timer;

public List<Boolean> palindromePath(
        int n, int[][] edges, String s, String[] queries) {

    adj = new ArrayList[n];

    for (int i = 0; i < n; i++) {
        adj[i] = new ArrayList<>();
    }

    for (int[] edge : edges) {
        adj[edge[0]].add(edge[1]);
        adj[edge[1]].add(edge[0]);
    }

    chars = s.toCharArray();

    tin = new int[n];
    tout = new int[n];
    depth = new int[n];

    int LOG = 1;
    while ((1 << LOG) <= n) {
        LOG++;
    }

    up = new int[n][LOG];
    bit = new int[n + 2];

    timer = 0;

    // Iterative DFS to calculate Euler Tour intervals.
    int[] parent = new int[n];
    int[] iter = new int[n];
    int[] stack = new int[n];

    Arrays.fill(parent, -1);

    int top = 0;
    stack[0] = 0;
    parent[0] = 0;

    tin[0] = ++timer;

    while (top >= 0) {

        int u = stack[top];

        if (iter[u] < adj[u].size()) {

            int v = adj[u].get(iter[u]++);

            if (v == parent[u]) {
                continue;
            }

            parent[v] = u;
            depth[v] = depth[u] + 1;

            up[v][0] = u;

            for (int j = 1; j < LOG; j++) {
                up[v][j] = up[up[v][j - 1]][j - 1];
            }

            tin[v] = ++timer;
            stack[++top] = v;

        } else {

            tout[u] = timer;
            top--;
        }
    }

    // IMPORTANT: Initialize both subtree boundaries.
    for (int u = 0; u < n; u++) {

        int mask = 1 << (chars[u] - 'a');

        update(tin[u], mask);
        update(tout[u] + 1, mask);
    }

    List<Boolean> answer = new ArrayList<>();

    for (String q : queries) {

        String[] parts = q.split(" ");

        if (parts[0].equals("update")) {

            int u = Integer.parseInt(parts[1]);
            char c = parts[2].charAt(0);

            int oldMask = 1 << (chars[u] - 'a');
            int newMask = 1 << (c - 'a');

            int change = oldMask ^ newMask;

            if (change != 0) {

                update(tin[u], change);
                update(tout[u] + 1, change);

                chars[u] = c;
            }

        } else {

            int u = Integer.parseInt(parts[1]);
            int v = Integer.parseInt(parts[2]);

            int lca = findLCA(u, v);

            int pathMask =
                    query(tin[u])
                    ^ query(tin[v])
                    ^ (1 << (chars[lca] - 'a'));

            answer.add((pathMask & (pathMask - 1)) == 0);
        }
    }

    return answer;
}

// Fenwick Tree: XOR update
void update(int index, int value) {

    while (index < bit.length) {
        bit[index] ^= value;
        index += index & -index;
    }
}

// Prefix XOR
int query(int index) {

    int result = 0;

    while (index > 0) {
        result ^= bit[index];
        index -= index & -index;
    }

    return result;
}

// Lowest Common Ancestor using Binary Lifting
int findLCA(int u, int v) {

    if (depth[u] < depth[v]) {
        int temp = u;
        u = v;
        v = temp;
    }

    int diff = depth[u] - depth[v];

    for (int j = 0; j < up[0].length; j++) {
        if ((diff & (1 << j)) != 0) {
            u = up[u][j];
        }
    }

    if (u == v) {
        return u;
    }

    for (int j = up[0].length - 1; j >= 0; j--) {

        if (up[u][j] != up[v][j]) {
            u = up[u][j];
            v = up[v][j];
        }
    }

    return up[u][0];
}


}

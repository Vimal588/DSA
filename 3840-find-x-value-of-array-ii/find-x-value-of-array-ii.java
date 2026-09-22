class Solution {

    static class Node {
        int prod;
        int[] cnt;

        Node(int k) {
            cnt = new int[k];
        }
    }

    int k;
    Node[] tree;
    int n;

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.k = k;
        this.n = nums.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Persistent update
            update(1, 0, n - 1, index, value);

            // Get information for [start, n - 1]
            Node ans = query(1, 0, n - 1, start, n - 1);

            result[q] = ans.cnt[x];
        }

        return result;
    }

    // Build segment tree
    private void build(int node, int left, int right, int[] nums) {

        if (left == right) {

            tree[node] = new Node(k);

            int value = nums[left] % k;

            tree[node].prod = value;

            // The single element itself is one possible prefix
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid, nums);
        build(node * 2 + 1, mid + 1, right, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Point update
    private void update(int node, int left, int right,
                        int index, int value) {

        if (left == right) {

            tree[node] = new Node(k);

            value %= k;

            tree[node].prod = value;
            tree[node].cnt[value] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index, value);
        } else {
            update(node * 2 + 1, mid + 1, right, index, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Merge two segments
    private Node merge(Node a, Node b) {

        Node res = new Node(k);

        // Product of complete segment
        res.prod = (a.prod * b.prod) % k;

        // Prefixes completely inside A
        for (int r = 0; r < k; r++) {
            res.cnt[r] += a.cnt[r];
        }

        // Prefixes that contain all of A
        // and then some prefix of B
        for (int r = 0; r < k; r++) {

            int newRemainder = (a.prod * r) % k;

            res.cnt[newRemainder] += b.cnt[r];
        }

        return res;
    }

    // Query range [ql, qr]
    private Node query(int node, int left, int right,
                       int ql, int qr) {

        // Completely outside
        if (qr < left || right < ql) {
            return null;
        }

        // Completely inside
        if (ql <= left && right <= qr) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        Node a = query(node * 2, left, mid, ql, qr);
        Node b = query(node * 2 + 1, mid + 1, right, ql, qr);

        if (a == null) {
            return b;
        }

        if (b == null) {
            return a;
        }

        return merge(a, b);
    }
}
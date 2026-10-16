class Solution {
    static final int MOD = 1_000_000_007;

    public int sumCounts(int[] nums) {
        int n = nums.length;

        long[] count = new long[4 * n];
        long[] sum = new long[4 * n];
        long[] lazy = new long[4 * n];

        int[] last = new int[100001];
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {
            prev[i] = last[nums[i]] - 1;
            last[nums[i]] = i + 1;
        }

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int left = prev[i] + 1;

            update(1, 0, n - 1, left, i, 1, count, sum, lazy);

            ans = (ans + sum[1]) % MOD;
        }

        return (int) ans;
    }

    private void update(
        int node, int start, int end,
        int l, int r, long val,
        long[] count, long[] sum, long[] lazy
    ) {
        if (r < start || end < l) {
            return;
        }

        if (l <= start && end <= r) {
            apply(node, start, end, val, count, sum, lazy);
            return;
        }

        push(node, start, end, count, sum, lazy);

        int mid = (start + end) / 2;

        update(node * 2, start, mid, l, r, val, count, sum, lazy);
        update(node * 2 + 1, mid + 1, end, l, r, val, count, sum, lazy);

        count[node] = (count[node * 2] + count[node * 2 + 1]) % MOD;
        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
    }

    private void apply(
        int node, int start, int end, long val,
        long[] count, long[] sum, long[] lazy
    ) {
        long len = end - start + 1L;

        sum[node] = (sum[node] + 2 * val % MOD * count[node] % MOD
                     + len % MOD * val % MOD * val % MOD) % MOD;

        count[node] = (count[node] + len % MOD * val) % MOD;

        lazy[node] = (lazy[node] + val) % MOD;
    }

    private void push(
        int node, int start, int end,
        long[] count, long[] sum, long[] lazy
    ) {
        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = (start + end) / 2;

        apply(node * 2, start, mid, lazy[node], count, sum, lazy);
        apply(node * 2 + 1, mid + 1, end, lazy[node], count, sum, lazy);

        lazy[node] = 0;
    }
}
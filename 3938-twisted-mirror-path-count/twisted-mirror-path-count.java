class Solution {
    public int uniquePaths(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1000000007;

        long[][] right = new long[m][n];
        long[][] down = new long[m][n];

        // Robot starts at (0,0)
        long[][] dp = new long[m][n];

        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                // Destination reached
                if (i == m - 1 && j == n - 1) {
                    return (int)((dp[i][j] + right[i][j] + down[i][j]) % MOD);
                }

                long ways = dp[i][j] + right[i][j] + down[i][j];
                ways %= MOD;

                // Starting cell is treated as an empty cell.
                if ((i == 0 && j == 0) || grid[i][j] == 0) {

                    // Move right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + ways) % MOD;
                    }

                    // Move down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + ways) % MOD;
                    }

                } else {

                    // Mirror: entering from left -> move down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + right[i][j]) % MOD;
                    }

                    // Mirror: entering from above -> move right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + down[i][j]) % MOD;
                    }
                }
            }
        }

        return 0;
    }
}

class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero = i;
                break;
            }
        }

        boolean inc = true;
        boolean dec = true;

        // Check increasing and decreasing cyclic order
        for (int i = 1; i < n; i++) {
            int right = (zero + i) % n;
            int left = (zero - i + n) % n;

            if (nums[right] != i) {
                inc = false;
            }

            if (nums[left] != i) {
                dec = false;
            }
        }

        int ans = Integer.MAX_VALUE;

        if (inc) {
            // Rotate left directly
            ans = Math.min(ans, zero);

            // Reverse, rotate, reverse again
            ans = Math.min(ans, n - zero + 2);
        }

        if (dec) {
            // Rotate, then reverse
            ans = Math.min(ans, zero + 2);

            // Reverse, then rotate
            ans = Math.min(ans, n - zero);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}


class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {

        int n = nums1.length;
        long[] diff = new long[n];

        long totalOperations = (long) k1 + k2;
        long maxDiff = 0;
        long sumDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            sumDiff += diff[i];
        }

        // If all differences can become zero
        if (totalOperations >= sumDiff) {
            return 0;
        }

        // Binary search for the maximum allowed difference
        long low = 0;
        long high = maxDiff;

        while (low < high) {
            long mid = (low + high) / 2;

            long operations = 0;

            for (int i = 0; i < n; i++) {
                if (diff[i] > mid) {
                    operations += diff[i] - mid;
                }
            }

            if (operations <= totalOperations) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        // Reduce every difference to at most low
        long answer = 0;
        long usedOperations = 0;

        for (int i = 0; i < n; i++) {
            if (diff[i] > low) {
                usedOperations += diff[i] - low;
                diff[i] = low;
            }
        }

        // Calculate the squared sum after reducing differences
        for (int i = 0; i < n; i++) {
            answer += diff[i] * diff[i];
        }

        // Any remaining operations should reduce the largest differences
        long remaining = totalOperations - usedOperations;

        for (int i = 0; i < n && remaining > 0; i++) {
            if (diff[i] == low && low > 0) {
                diff[i]--;
                answer -= 2 * low - 1;
                remaining--;
            }
        }

        return answer;
    }
}

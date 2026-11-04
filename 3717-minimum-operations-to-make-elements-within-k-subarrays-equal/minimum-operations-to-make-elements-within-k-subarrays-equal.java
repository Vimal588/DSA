import java.util.*;

class Solution {

    TreeMap<Integer, Integer> low = new TreeMap<>();
    TreeMap<Integer, Integer> high = new TreeMap<>();

    int lowSize = 0;
    int highSize = 0;

    long lowSum = 0;
    long highSum = 0;

    // Add element to low
    void addLow(int val) {
        low.put(val, low.getOrDefault(val, 0) + 1);
        lowSize++;
        lowSum += val;
    }

    // Add element to high
    void addHigh(int val) {
        high.put(val, high.getOrDefault(val, 0) + 1);
        highSize++;
        highSum += val;
    }

    // Remove element from low
    void removeLow(int val) {
        int count = low.get(val);

        if (count == 1) {
            low.remove(val);
        } else {
            low.put(val, count - 1);
        }

        lowSize--;
        lowSum -= val;
    }

    // Remove element from high
    void removeHigh(int val) {
        int count = high.get(val);

        if (count == 1) {
            high.remove(val);
        } else {
            high.put(val, count - 1);
        }

        highSize--;
        highSum -= val;
    }

void balance(int x) {

    int target = (x + 1) / 2;

    while (lowSize > target) {

        int val = low.lastKey();

        removeLow(val);
        addHigh(val);
    }

    while (lowSize < target && highSize > 0) {

        int val = high.firstKey();

        removeHigh(val);
        addLow(val);
    }
}

    // Insert element
    void add(int val, int x) {

        if (low.isEmpty() || val <= low.lastKey()) {
            addLow(val);
        } else {
            addHigh(val);
        }

        balance(x);
    }

    // Delete element
    void remove(int val, int x) {

        if (low.containsKey(val)) {
            removeLow(val);
        } else {
            removeHigh(val);
        }

        balance(x);
    }

    // Calculate minimum operations for current window
    long getCost() {

        int median = low.lastKey();

        return (long) median * lowSize - lowSum
                + highSum - (long) median * highSize;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Step 1: Calculate cost of every window
        long[] cost = new long[n - x + 1];

        low.clear();
        high.clear();

        lowSize = 0;
        highSize = 0;
        lowSum = 0;
        highSum = 0;

        // First window
        for (int i = 0; i < x; i++) {
            add(nums[i], x);
        }

        cost[0] = getCost();

        // Remaining windows
        for (int i = 1; i <= n - x; i++) {

            remove(nums[i - 1], x);

            add(nums[i + x - 1], x);

            cost[i] = getCost();
        }

        // Step 2: Dynamic Programming

        long INF = Long.MAX_VALUE / 2;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        // Select 0 subarrays: cost is 0
        Arrays.fill(prev, 0);

        for (int j = 1; j <= k; j++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Choice 1: Skip current element
                curr[i] = curr[i - 1];

                // Choice 2: Select a window of size x
                if (i >= x) {

                    long candidate =
                            prev[i - x] + cost[i - x];

                    curr[i] = Math.min(
                            curr[i],
                            candidate
                    );
                }
            }

            // Move to next DP state
            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}
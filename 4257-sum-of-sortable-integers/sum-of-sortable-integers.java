class Solution {
public int sortableIntegers(int[] nums) {
int n = nums.length;

    int[] sorted = nums.clone();
    java.util.Arrays.sort(sorted);

    int ans = 0;

    for (int k = 1; k <= n; k++) {
        if (n % k != 0) {
            continue;
        }

        boolean valid = true;

        for (int start = 0; start < n; start += k) {
            if (!isRotation(nums, sorted, start, k)) {
                valid = false;
                break;
            }
        }

        if (valid) {
            ans += k;
        }
    }

    return ans;
}

private boolean isRotation(int[] nums, int[] sorted,
                           int start, int k) {

    // Build the KMP pattern from the sorted block.
    int[] pattern = new int[k];

    for (int i = 0; i < k; i++) {
        pattern[i] = sorted[start + i];
    }

    // Build the KMP LPS array.
    int[] lps = new int[k];
    int len = 0;
    int i = 1;

    while (i < k) {
        if (pattern[i] == pattern[len]) {
            lps[i] = ++len;
            i++;
        } else if (len > 0) {
            len = lps[len - 1];
        } else {
            lps[i] = 0;
            i++;
        }
    }

    // Search for the sorted block in the circular original block.
    // A rotation must appear in the block repeated twice.
    int j = 0;

    for (i = 0; i < 2 * k - 1; i++) {
        int current = nums[start + (i % k)];

        while (j > 0 && current != pattern[j]) {
            j = lps[j - 1];
        }

        if (current == pattern[j]) {
            j++;
        }

        if (j == k) {
            return true;
        }
    }

    return false;
}

}

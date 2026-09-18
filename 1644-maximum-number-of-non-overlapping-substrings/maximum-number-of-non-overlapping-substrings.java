class Solution {
    public List<String> maxNumOfSubstrings(String s) {

        List<String> result = new ArrayList<>();

        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        // Find first and last occurrence
        for (int i = 0; i < n; i++) {

            int index = s.charAt(i) - 'a';

            first[index] = Math.min(first[index], i);
            last[index] = i;
        }

        // Find valid intervals
        List<int[]> intervals = new ArrayList<>();

        for (int c = 0; c < 26; c++) {

            if (last[c] == -1) {
                continue;
            }

            int left = first[c];
            int right = last[c];

            boolean valid = true;

            for (int i = left; i <= right; i++) {

                int index = s.charAt(i) - 'a';

                if (first[index] < left) {
                    valid = false;
                    break;
                }

                right = Math.max(right, last[index]);
            }

            if (valid) {
                intervals.add(new int[]{left, right});
            }
        }

        // Sort by ending position
        intervals.sort((a, b) -> a[1] - b[1]);

        int prevEnd = -1;

        // Greedy selection
        for (int[] interval : intervals) {

            int left = interval[0];
            int right = interval[1];

            if (left > prevEnd) {

                result.add(s.substring(left, right + 1));

                prevEnd = right;
            }
        }

        return result;
    }
}
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();

        backtrack(ans, "", 0, 0, n);

        return ans;
    }

    private void backtrack(List<String> ans, String current,
                            int open, int close, int n) {

        // Complete valid combination
        if (current.length() == 2 * n) {
            ans.add(current);
            return;
        }

        // Add '('
        if (open < n) {
            backtrack(ans, current + "(", open + 1, close, n);
        }

        // Add ')' only when it is valid
        if (close < open) {
            backtrack(ans, current + ")", open, close + 1, n);
        }
    }
}
class Solution {
    public int longestValidParentheses(String s) {

        Stack<Integer> st = new Stack<>();

        // Base index
        st.push(-1);

        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (ch == '(') {
                st.push(i);
            } 
            else {
                st.pop();

                if (st.empty()) {
                    // Current ')' cannot be matched
                    st.push(i);
                } 
                else {
                    // Length of valid substring
                    int length = i - st.peek();
                    maxLength = Math.max(maxLength, length);
                }
            }
        }

        return maxLength;
    }
}
class Solution {
    public String reverseParentheses(String s) {

        Stack<String> stack = new Stack<>();
        StringBuilder current = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Save the string before '('
                stack.push(current.toString());

                // Start a new substring
                current.setLength(0);

            } else if (ch == ')') {

                // Reverse the substring inside parentheses
                current.reverse();

                // Get the string before '('
                String previous = stack.pop();

                // Combine both
                current = new StringBuilder(previous + current);

            } else {
                // Normal character
                current.append(ch);
            }
        }

        return current.toString();
    }
}
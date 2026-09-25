class Solution {

    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {

        s = expression;
        index = 0;

        Set<String> result = parse();

        List<String> ans = new ArrayList<>(result);

        Collections.sort(ans);

        return ans;
    }

    // Parse an expression
    private Set<String> parse() {

        Set<String> result = new HashSet<>();

        // First part of concatenation
        Set<String> current = new HashSet<>();
        current.add("");

        while (index < s.length() && s.charAt(index) != '}') {

            char ch = s.charAt(index);

            if (ch == ',') {

                // Union
                result.addAll(current);

                current.clear();
                current.add("");

                index++;

            } else {

                // Parse next expression
                Set<String> next = parsePart();

                // Concatenation
                Set<String> temp = new HashSet<>();

                for (String a : current) {
                    for (String b : next) {
                        temp.add(a + b);
                    }
                }

                current = temp;
            }
        }

        // Add the last concatenation part
        result.addAll(current);

        // If we are at }, consume it
        if (index < s.length() && s.charAt(index) == '}') {
            index++;
        }

        return result;
    }

    // Parse one part:
    // letter OR {expression}
    private Set<String> parsePart() {

        Set<String> result = new HashSet<>();

        char ch = s.charAt(index);

        if (ch == '{') {

            // Skip '{'
            index++;

            result = parse();

        } else {

            // Single lowercase letter
            result.add(String.valueOf(ch));

            index++;
        }

        return result;
    }
}
class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {

        HashMap<String, String> map = new HashMap<>();

        // Store key-value pairs
        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder ans = new StringBuilder();

        int i = 0;

        while (i < s.length()) {

            // Start of bracket pair
            if (s.charAt(i) == '(') {

                int j = i + 1;

                // Find closing bracket
                while (s.charAt(j) != ')') {
                    j++;
                }

                // Extract key
                String key = s.substring(i + 1, j);

                // Check key in map
                if (map.containsKey(key)) {
                    ans.append(map.get(key));
                } else {
                    ans.append("?");
                }

                // Move after ')'
                i = j + 1;

            } else {

                // Normal character
                ans.append(s.charAt(i));
                i++;
            }
        }

        return ans.toString();
    }
}
class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            int alphabetPosition = s.charAt(i) - 'a' + 1;
            int reversePosition = 27 - alphabetPosition;

            sum += reversePosition * (i + 1);
        }

        return sum;
    }
}
class Solution {
    public int closestTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {
                int distance = Math.abs(i - startIndex);
                distance = Math.min(distance, n - distance);
                min = Math.min(min, distance);
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }
}
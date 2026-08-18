import java.util.*;

class Solution {
    public boolean canPartitionGrid(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        long total = 0;
        Map<Integer, Integer> totalMap = new HashMap<>();

        for (int[] row : grid) {
            for (int x : row) {
                total += x;
                totalMap.put(x, totalMap.getOrDefault(x, 0) + 1);
            }
        }

        Map<Integer, Integer> topMap = new HashMap<>();
        long topSum = 0;

        for (int i = 0; i < m - 1; i++) {
            for (int j = 0; j < n; j++) {
                int x = grid[i][j];
                topSum += x;
                topMap.put(x, topMap.getOrDefault(x, 0) + 1);
                totalMap.put(x, totalMap.get(x) - 1);
            }

            long bottomSum = total - topSum;

            if (topSum == bottomSum) {
                return true;
            }

            if (topSum > bottomSum) {
                long diff = topSum - bottomSum;

                if (canRemove(grid, 0, i, 0, n - 1, diff, topMap)) {
                    return true;
                }
            } else {
                long diff = bottomSum - topSum;

                if (canRemove(grid, i + 1, m - 1, 0, n - 1, diff, totalMap)) {
                    return true;
                }
            }
        }

        topMap.clear();
        totalMap.clear();

        for (int[] row : grid) {
            for (int x : row) {
                totalMap.put(x, totalMap.getOrDefault(x, 0) + 1);
            }
        }

        long leftSum = 0;

        for (int j = 0; j < n - 1; j++) {
            for (int i = 0; i < m; i++) {
                int x = grid[i][j];
                leftSum += x;
                topMap.put(x, topMap.getOrDefault(x, 0) + 1);
                totalMap.put(x, totalMap.get(x) - 1);
            }

            long rightSum = total - leftSum;

            if (leftSum == rightSum) {
                return true;
            }

            if (leftSum > rightSum) {
                long diff = leftSum - rightSum;

                if (canRemove(grid, 0, m - 1, 0, j, diff, topMap)) {
                    return true;
                }
            } else {
                long diff = rightSum - leftSum;

                if (canRemove(grid, 0, m - 1, j + 1, n - 1, diff, totalMap)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canRemove(int[][] grid, int r1, int r2,
                              int c1, int c2, long diff,
                              Map<Integer, Integer> map) {

        if (diff > Integer.MAX_VALUE) {
            return false;
        }

        int value = (int) diff;

        if (!map.containsKey(value) || map.get(value) <= 0) {
            return false;
        }

        int height = r2 - r1 + 1;
        int width = c2 - c1 + 1;

        if (height > 1 && width > 1) {
            return true;
        }

        if (height == 1) {
            return grid[r1][c1] == value || grid[r1][c2] == value;
        }

        if (width == 1) {
            return grid[r1][c1] == value || grid[r2][c1] == value;
        }

        return false;
    }
}
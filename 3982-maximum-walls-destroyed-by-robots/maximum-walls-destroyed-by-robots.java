import java.util.*;

class Solution {
    int[][] robot;
    int[] walls;
    Integer[][] dp;
    int n;

    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        n = robots.length;
        robot = new int[n][2];

        for (int i = 0; i < n; i++) {
            robot[i][0] = robots[i];
            robot[i][1] = distance[i];
        }

        Arrays.sort(robot, (a, b) -> Integer.compare(a[0], b[0]));
        Arrays.sort(walls);

        this.walls = walls;
        dp = new Integer[n][2];

        return solve(n - 1, 1);
    }

    private int solve(int i, int nextDir) {
        if (i < 0) {
            return 0;
        }

        if (dp[i][nextDir] != null) {
            return dp[i][nextDir];
        }

        int pos = robot[i][0];
        int dist = robot[i][1];

        int left = pos - dist;

        if (i > 0) {
            left = Math.max(left, robot[i - 1][0] + 1);
        }

        int l = lowerBound(left);
        int r = lowerBound(pos + 1);

        int ans = solve(i - 1, 0) + r - l;

        int right = pos + dist;

        if (i + 1 < n) {
            if (nextDir == 0) {
                right = Math.min(
                    right,
                    robot[i + 1][0] - robot[i + 1][1] - 1
                );
            } else {
                right = Math.min(
                    right,
                    robot[i + 1][0] - 1
                );
            }
        }

        l = lowerBound(pos);
        r = lowerBound(right + 1);

        ans = Math.max(
            ans,
            solve(i - 1, 1) + r - l
        );

        return dp[i][nextDir] = ans;
    }

    private int lowerBound(int target) {
        int low = 0;
        int high = walls.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (walls[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }
}
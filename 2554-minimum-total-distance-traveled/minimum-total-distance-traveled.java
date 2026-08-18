import java.util.*;

class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);

        Arrays.sort(factory, (a, b) -> Integer.compare(a[0], b[0]));

        int n = robot.size();
        long[][] dp = new long[factory.length + 1][n + 1];

        for (int i = 0; i <= factory.length; i++) {
            Arrays.fill(dp[i], Long.MAX_VALUE / 2);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= factory.length; i++) {
            int pos = factory[i - 1][0];
            int limit = factory[i - 1][1];

            for (int j = 0; j <= n; j++) {
                dp[i][j] = dp[i - 1][j];

                long cost = 0;

                for (int k = 1; k <= Math.min(limit, j); k++) {
                    cost += Math.abs((long) robot.get(j - k) - pos);

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - 1][j - k] + cost
                    );
                }
            }
        }

        return dp[factory.length][n];
    }
}
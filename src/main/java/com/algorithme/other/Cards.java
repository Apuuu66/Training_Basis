package com.algorithme.other;

import org.junit.Test;

/*这是一道很明显的动态规划题
用F[l][r]表示先选的人能拿到的最高分
用S[l][r]来表示后选的人能拿到的最高分
如对于一组从0,1,2，...,n-1的数
对于先选者，他有两种选法
若先选者选A[0],则对于后面的1，...,n-1数组，他就变成了后选者，此时能拿到的分为A[0]+S[1][n-1]
若先选者选A[n-1],则对于前面的数组0，...，n-2,同样变为后选者，此时能拿到得分为A[n-1]+S[0][n-2];
所以 F[0][n-1]=max(A[0]+S[1][n-1],A[n-1]+S[0][n-2])
对于后选者，他能能到的最高分是受先选者控制的，即他只能选到先选者留给他的最小值，将其转化为数学形式就是
S[l][r]=min(F[l+1][r],F[l][r-1]);
这里的最小值是先选者留给他的，他只能拿到最小值，打个比方，我是先选者，我若选A[0]，剩下的留给你选，这个时候主动权在你
所以你能得到的最大分必为F[1][n-1],我若选A[n-1]，剩下的留给你选，这个时候主动权在你
所以你能得到的分必为F[0][n-2],我肯定是要把能得到的分少的那个留给你，所以你只能得到Min(F[1][n-1],F[0][n-2]);
*/
public class Cards {
    public int cardGame(int[] A, int n) {
        int[][] F = new int[n][n];
        int[][] S = new int[n][n];
        for (int r = 0; r < n; r++) {
            F[r][r] = A[r];
            S[r][r] = 0;
            for (int l = r - 1; l >= 0; l--) {
                F[l][r] = Math.max(A[l] + S[l + 1][r], A[r] + S[l][r - 1]);
                S[l][r] = Math.min(F[l + 1][r], F[l][r - 1]);
            }
        }
        return Math.max(F[0][n - 1], S[0][n - 1]);
    }


    public boolean PredictTheWinner(int[] nums) {
        int[][] dp = new int[nums.length + 1][nums.length];
        for (int s = nums.length; s >= 0; s--) {
            for (int e = s + 1; e < nums.length; e++) {
                int a = nums[s] - dp[s + 1][e];
                int b = nums[e] - dp[s][e - 1];
                dp[s][e] = Math.max(a, b);
            }
        }
        return dp[0][nums.length - 1] >= 0;

    }

    @Test
    public void f32() {
        int[] arr = {1, 5, 2};
//        System.out.println(cardGame(arr, arr.length));
        System.out.println(PredictTheWinner(arr));
    }
}
import java.util.Arrays;

class Solution56 {
    public int[][] merge(int[][] intervals) {
        int n = intervals.length;
        int[][] ans = new int[n][2];
        int size = 0;
        
        Arrays.sort(intervals, (a, b) -> {
                return Integer.compare(a[0], b[0]); 
        });
        int[] temp = intervals[0];
        for (int i = 1; i < n; ++i) {
            
            if (intervals[i][0] <= temp[1]) { // тем самым мы решаем проблему теста [1,4],[2,3]
                temp[1] = Math.max(temp[1], intervals[i][1]);
            } else {
                ans[size++] = temp;
                temp = intervals[i];
            }           
        }
        ans[size++] = temp;

        return Arrays.copyOf(ans, size);
    }
}
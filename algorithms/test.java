
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class test {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            adj.add(new ArrayList<>());
        }

        int[] inDegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            adj.get(edge[1]).add(edge[0]);
            inDegree[edge[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; ++i) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] ans = new int[numCourses];
        int index = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            ans[index++] = u;
            for (int v : adj.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        if (index == numCourses) {
            return ans;
        } else {
            return new int[0];
        }
    }
}

import java.util.LinkedList;
import java.util.Queue;

/*
 * кратко как тут BFS
 * очередь, в очередь мы добавляем точку старта
 * алгоритм
 * * достаем точку из очереди 0
 * * проверяем все точки вокруг 1 2 3 4
 * * добавляем в очередь ту, которая подошла по условию(пусть типо все)
 * 1 2 3 4
 * достаем след
 * 2 3 4
 * вокруг
 * 2 3 4 1.1 1.2 1.3 1.4
 * и вот теперь явно видно, что мы пойдем каким-то образом в глубину
 * а реально -- в ширину 
 */

class Solution733_BFS {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int startColor = image[sr][sc];

        if (startColor == color) {
            return image;
        }

        int rows = image.length;
        int columns = image[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sr, sc});
        while (!queue.isEmpty()) {
            int[] currentPixel = queue.poll();
            int r = currentPixel[0];
            int c = currentPixel[1];

            image[r][c] = color;

            int[][] directions = {{0, 1},{0, -1},{1, 0},{-1, 0}};
            for (int[] direction : directions) {
                int r_ = r + direction[0];
                int c_ = c + direction[1];

                if (r_ >= 0 && r_ < rows && c_ >= 0 && c_ < columns && image[r_][c_] == startColor) {
                    queue.add(new int[]{r_, c_});
                }
            }
        }

        return image;
    }
}
import java.util.ArrayList;
import java.util.List;
/*
 * идея проста как никогда
 * просто DFS/BFS
 */
class Solution547 {
    public int findCircleNum(int[][] isConnected) {
        int ans = 0;
        int n = isConnected.length;
        List<Boolean> visited = new ArrayList<>(n); 
        for (int i = 0; i < n; i++) {
            visited.add(false);
        }
        
        // проходимся по каждому городу
        for (int city = 0; city < n; ++city) {
            if (visited.get(city) == false) { // если он не посещен
                visited.set(city, true); // то мы его уже посетили
                dfs(city, isConnected, visited); // и просамтриваем откуда мы от него может дойти
                // он в любом случае поситит города, которые с ним в провинции
                ans++; // поэтому -- тем самым мы высчитали целую провинцуию
                // все города провинции, в которой находится сам city, посещены в результате DFS/BFS
            }
        }
        return ans;
    }
    private void dfs(int city, int[][] isConnected, List<Boolean> visited) {
        for (int cityNeighbor = 0; cityNeighbor < isConnected.length; ++cityNeighbor) { // проходим по всем городам, с которыми связан город
            if (isConnected[city][cityNeighbor] == 1 && visited.get(cityNeighbor) == false) { // если связаны и не был там
                visited.set(cityNeighbor, true); // то он уже там был
                dfs(cityNeighbor, isConnected, visited); // ну и так как в глубину -- проверяем уже его соседние 
            }
        }
    }
}
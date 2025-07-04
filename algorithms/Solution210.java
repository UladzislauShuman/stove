import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Solution210 {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // список смежности
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; ++i) {
            adj.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];

        // заполняем
        for (int[] edge : prerequisites) { // edge = [a,b]
            adj.get(edge[1]).add(edge[0]); // b -> a 
            inDegree[edge[0]]++;
        }

        // алгоритм Кана
        /* тут типо топологическая сортировка, и алгоритм работает примерно так
         * те вершины, в кого  НЕ ВХОДЯТ (in), они "убираются"
         * (они отсортированы)
         * удаляем из них ВЫХОДЯЩИЕ(out) дуги (для кого-то они ВХОДЯШИЕ)
         * и так идем, пока не уберем всё
         * 
         * если index = количеству вершин И нет тех вершин, в которых никто НЕ ВХОДИТ -- все выполнено
         * если index < количества -- есть контур(ну или цикл) -- сортировка не может выполниться
         * 
         * теперь -- как это выглядит в коде
         */ 
        
        // согласно алгоритму
        Queue<Integer> queue = new LinkedList<>(); // мне было по началу не понятно, почему тут очередь, но со временем думаю уляжится
        for (int i = 0; i < inDegree.length; ++i) {
            if (inDegree[i] == 0) { // заносим все невходщие
                queue.offer(i); // в очередь
            }
        }

        // сортируем
        int[] topoSort = new int[numCourses]; // вот сюда мы скиыдываем отсортированные
        int index = 0; // тот самый index

        while (!queue.isEmpty()) { // пока очередь не пустая
            int u = queue.poll(); // вытаскиваем
            topoSort[index++] = u; // добавляем как отсортировнный
            // он First зашел, когды в него никто не входит, ну и мы бы его на рисунке положили бы в 
            // отсортированные
            // ну и он -- First вышел и добавился -- как мы и хотели 

            // все -- мы забрали вершину и закинулы в отсортированные
            // надо удалить ребра
            // в нашей реализации значит -- уменьшить степени Входящих ВВВ вершину, к которой доходила u
            for (int v : adj.get(u)) { // u -> v
                inDegree[v]--;
                if (inDegree[v] == 0) { // если пустой -- пусть идет ждет своей очереди за мороженным
                    queue.offer(v);
                }
            } 
        }

        // как мы указали в а. Кана: когда отсортированно, а когда проблемы (цикл)
        if (index == numCourses) {
            return topoSort;
        } else {
            return new int[0];
        }
    }
}
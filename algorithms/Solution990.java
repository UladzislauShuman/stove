class Solution990 {
    /*
     * в отличии от других задач подобной тематики, которые мы рассматривали в этом блоке этой недели
     * тут явно используется DSU
     * при чем -- можно обойтись и без всяких усовершенствованний 
     * * объединение по размеру
     * * сжатие пути
     * да и в find просто рекурсивно вызывать функцию
     * 
     * множества -- маленькие буквы латинского алфавита
     * их связывает (связь с единным множеством) -- "=="
     * 
     * алгоритм прост
     * соединяем всех тех, кто ==
     * проходимся второй раз и проверяем на соответсвие !=
     */ 
    public boolean equationsPossible(String[] equations) {
        DSU dsu = new DSU();
        
        for (String e : equations) {
            if (e.charAt(1) == '=') {
                dsu.union(e.charAt(0), e.charAt(3));
            }
        }
        
        for (String e: equations) {
            if (e.charAt(1) == '!' && 
                dsu.find(e.charAt(0)) == dsu.find(e.charAt(3))) {
                return false;
            }
        }
        return true;
    }

    public class DSU {
        public int[] dsu;
        public DSU() {
            dsu = new int[26];
            for (int i = 0; i < 26; ++i) {
                dsu[i] = i;
            }
        }

        public int find(char x) {
            return findInt(x - 'a');
        }

        private int findInt(int x) {
            return dsu[x] == x ? x : (dsu[x] = findInt(dsu[x]));
        }

        public void union(char a, char b) {
            unionInt(a - 'a', b - 'a');
        }

        private void unionInt(int a, int b) {
            dsu[findInt(a)] = findInt(b);
        }
    }
}
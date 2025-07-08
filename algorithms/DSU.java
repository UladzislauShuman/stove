public class DSU {
        public int[] dsu;
        public DSU(int n) {
            dsu = new int[n + 1];
            for (int i = 0; i <= n; ++i) {
                dsu[i] = i;
            }
        }

        public int find(int x) {
            return dsu[x] == x ? x : (dsu[x] = find(dsu[x]));
        }

        public void union(int a, int b) {
            dsu[find(a)] = find(b);
        }
    }
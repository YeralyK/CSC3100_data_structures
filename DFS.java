import java.util.*;
import java.io.*;

public class Main {

    static final int WHITE = 0, GRAY = 1, BLACK = 2;

    static boolean BALABALA = false;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        List<List<Integer>> spells = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            spells.add(null);
        }

        for (int j = 0; j < m; j++) {
            int x = sc.nextInt();
            int y = sc.nextInt();

            if (spells.get(y) == null) {
                spells.set(y, new ArrayList<>());
            }

            spells.get(y).add(x);
        }

        int[] color = new int[n];

        for (int u = 0; u < n; u++) {
            if (color[u] == WHITE) {
                dfs(spells, color, u);
            }
        }

        if (BALABALA) {
            System.out.println("Fake");
        }
        else {
            System.out.println("BALABALA");
        }
    }

    static void dfs(List<List<Integer>> G, int[] color, int u) {
        color[u] = GRAY;

        List<Integer> adj = G.get(u);
        if (adj != null) {
            for (int v : adj) {
                if (color[v] == GRAY) {
                    BALABALA = true;
                    return;
                }
                if (color[v] == WHITE) {
                    dfs(G, color, v);
                    if (BALABALA) return;
                }
            }
        }

        color[u] = BLACK;
    }
}

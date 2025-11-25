import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        FastReader scanner = new FastReader();
        
        int N = scanner.nextInt();
        
        TSL res = new TSL(N);

        for (int k = 0; k < N - 1; k++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            long w = scanner.nextLong();
            long c = scanner.nextLong();
            
            res.edge(u, v, w, c);
        }

        long result = res.tsl();
        
        System.out.println(result);
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
        public FastReader() { br = new BufferedReader(new InputStreamReader(System.in)); }
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try { 
                    st = new StringTokenizer(br.readLine()); 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                }
            }
            return st.nextToken();
        }
        int nextInt() { 
            return Integer.parseInt(next()); 
        }
        long nextLong() { 
            return Long.parseLong(next()); 
        }
    }
}

class TSL {
    
    int N;
    int[] parent;
    long[] w_cost;
    long[] c_cost;
    List<List<Integer>> adj;

    public TSL(int n) {
        this.N = n;
        
        parent = new int[N + 1];
        w_cost = new long[N + 1];
        c_cost = new long[N + 1];
        adj = new ArrayList<>(N + 1);
        for (int i = 0; i <= N; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void edge(int u, int v, long w, long c) {
        parent[v] = u;
        w_cost[v] = w;
        c_cost[v] = c;
        adj.get(u).add(v);
        adj.get(v).add(u);
    }

    private static class NodeLatency {
        int node;
        long latency;
        NodeLatency(int n, long l) { 
            node = n; 
            latency = l; 
        }
    }

    public long tsl() {
        long total_lat = 0;
        
        for (int i = 1; i <= N; i++) {
            
            
            long[] latencies = new long[N + 1];
            Queue<NodeLatency> Q = new LinkedList<>();
            boolean[] visited = new boolean[N + 1];

            visited[i] = true;
            Q.offer(new NodeLatency(i, 0));
            latencies[i] = 0;

            while (!Q.isEmpty()) {
                NodeLatency curr = Q.poll();
                int u = curr.node;
                long curr_L = curr.latency;

                for (int v : adj.get(u)) {
                    if (!visited[v]) {
                        visited[v] = true;
                        
                        long dir_cost;
                        if (parent[v] == u) {
                            dir_cost = w_cost[v];
                        }
                        else {
                            dir_cost = c_cost[u]; 
                        }
                        
                        latencies[v] = curr_L ^ (dir_cost + i);
                        Q.offer(new NodeLatency(v, curr_L ^ (dir_cost + i)));
                    }
                }
            }

            for (int j = i + 1; j <= N; j++) {
                total_lat = (total_lat + latencies[j]) % 1000000007L;
            }
        }
        return total_lat;
    }
}
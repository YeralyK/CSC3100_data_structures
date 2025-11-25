import java . io .*;
import java . util .*;

public class Main {
    static final class MT19937_64 {
        static final int NN =312 , MM =156 , RR =31;
        static final long AA =0xB5026F5AA96619E9L ;
        static final int UU =29 , SS =17 , TT =37 , LL =43;
        static final long DD =0x5555555555555555L ;
        static final long BB =0x71D67FFFEDA60000L ;
        static final long CC =0xFFF7EEE000000000L ;
        static final long FF =6364136223846793005L ;
        static final long MASK = -1L , LOWER =((1L << RR ) -1) , UPPER =( MASK ^ LOWER );
        long [] mt = new long [ NN ]; int idx = NN ;
        MT19937_64 ( long seed ){ seed ( seed ); }
        void seed ( long seed ){ mt [0]= seed ;
            for ( int i =1; i < NN ;++ i ){ long x = mt[i -1]^( mt[i -1] >>>62); mt [i ]= FF *x+ i; }
            idx = NN ;
        }
        void twist (){ for( int i =0; i < NN ;++ i ){
            long x =( mt [i ]& UPPER )|( mt [( i +1)% NN ]& LOWER );
            long xA =( x >>>1); if (( x &1L )!=0) xA ^= AA ;
            mt [i ]= mt [( i + MM )% NN ]^ xA ; } idx =0;
        }
        long nextLong (){ if( idx >= NN ) twist (); long y= mt [ idx ++];
            y ^=( y >>> UU )& DD ; y ^=( y << SS )& BB ; y ^=( y << TT )& CC ; y ^=( y >>> LL ); return y; }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
        public FastReader() { br = new BufferedReader(new InputStreamReader(System.in)); }
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try { st = new StringTokenizer(br.readLine()); } 
                catch (IOException e) { e.printStackTrace(); }
            }
            return st.nextToken();
        }
        int nextInt() { return Integer.parseInt(next()); }
        long nextLong() { return Long.parseLong(next()); }
    }
    public static void main ( String [] args ) throws Exception {
        FastReader sc = new FastReader();
        int Y = sc.nextInt();
        long best_bottleneck = Long.MAX_VALUE;
        for ( int t =0; t <Y ;++ t ){
            long L = sc.nextLong();
            int N = sc.nextInt();
            int M = sc.nextInt();
            long s = sc.nextLong();
            long P = sc.nextLong();
            MT19937_64 rng = new MT19937_64 ( s );
            long [] D= new long [N + 2]; 
            D[0] = 0L;
            for ( int j =1; j <= N ;++ j ){
                long gap = Long . remainderUnsigned ( rng . nextLong () , P) + 1L;
                D[j ]= D[j -1]+ gap;
            }
            D[N + 1] = L;
            
            if (t > 0) {
                int removed = 0;
                int pointer = 0;
                for (int i = 1; i < D.length; i++) {
                    if (D[i] - D[pointer] < best_bottleneck) {
                        removed++;
                    } else {
                        pointer = i;
                    }
                    if (removed > M) {
                        break; 
                    }
                }
                boolean feasibility = (removed <= M);
                if (feasibility) {
                    continue; 
                }
            }

            long low = 1L;
            long high;
            if (t == 0) {
                high = L;
            }
            else {
                high = best_bottleneck - 1;
            }
            long best = 0L;

            while (low <= high){
                long mid = low + (high - low) / 2;
                int removed = 0;
                int pointer = 0;

                for (int i = 1; i < D.length; i++) {
                if (D[i] - D[pointer] < mid) {
                    removed++;
                } 
                else {
                    pointer = i;
                }
                if (removed > M) {
                    break;
                }
                }
                boolean feasibility = (removed <= M);

                if (feasibility) {
                    best = mid;
                    low = mid + 1;
                } 
                else {
                    high = mid - 1;
                }
            }
            if (best_bottleneck > best){
                best_bottleneck = best;
            }
        }
        System.out.println(best_bottleneck);
        
    }
}

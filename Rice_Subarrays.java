import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> list = new ArrayList<>();

        while (st.hasMoreTokens()) {
            list.add(Integer.parseInt(st.nextToken()));
        }

        int size = list.size();
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = list.get(i);
        }

        int count = 0;

        for (int left = 0; left <= size - 3; left++){
            int middle = numbers[left + 1];

            for (int right = left + 2; right < size; right++){
                middle = Math.max(numbers[right - 1], middle);

                if (middle >= numbers[left]) {
                    break;
                }
                
                if (numbers[left] > middle && numbers[right] > middle){
                    count += 1;
                }
            }
        }

        System.out.println(count);
        
    }
}
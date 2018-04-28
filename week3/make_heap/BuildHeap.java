import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.lang.Math;

public class BuildHeap {
    private int[] data;
    private List<Swap> swaps;
    private int n;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new BuildHeap().solve();
    }

    private void readData() throws IOException {
        n = in.nextInt();
        data = new int[n];
        for (int i = 0; i < n; ++i) {
          data[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        out.println(swaps.size());
        for (Swap swap : swaps) {
          out.println(swap.index1 + " " + swap.index2);
        }
    }

    private void generateSwaps() {
      swaps = new ArrayList<Swap>();
      // The following naive implementation just sorts 
      // the given sequence using selection sort algorithm
      // and saves the resulting sequence of swaps.
      // This turns the given array into a heap, 
      // but in the worst case gives a quadratic number of swaps.
      //
      // TODO: replace by a more efficient implementation
      
      int i,min,temp = 0,parent,k = 0;
      for(i = 0;i < n;i++)
      {
        temp = i;
        while(smallestchild(temp) < n && data[smallestchild(temp)] < data[temp])
            temp = smallestchild(temp);

        if(i != temp)
        {
            swaps.add(new Swap(temp,i));
            int tmp = data[i];
            data[i] = data[temp];
            data[temp] = tmp;

            shiftUp(i);

        }
        else if(i == temp)
        {
            shiftUp(i);
        }

      } 
         
         
    }

    public int smallestchild(int i)
    {
        if(2*i + 2 < n)
        {
            if(data[2*i + 2] < data[2*i + 1])
                return 2*i + 2;
            else
                return 2*i + 1;
        }
        else if(2*i + 1 < n)
        {
            return 2*i + 1;
        }
        else 
            return n; 
    }   

    public void shiftUp(int i)
    {
        int parent  = (int)Math.ceil((double)i/(double)2) - 1;
        while(parent >= 0 && data[i] < data[parent]){
            swaps.add(new Swap(i,parent));
            int tmp = data[i];
            data[i] = data[parent];
            data[parent] = tmp;

            i = parent;
            parent  = (int)Math.ceil((double)i/(double)2) - 1;
        }
    }     

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        generateSwaps();
        writeResponse();
        out.close();
    }

    static class Swap {
        int index1;
        int index2;

        public Swap(int index1, int index2) {
            this.index1 = index1;
            this.index2 = index2;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

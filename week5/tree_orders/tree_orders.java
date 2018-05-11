import java.util.*;
import java.io.*;

public class tree_orders {
    class FastScanner {
		StringTokenizer tok = new StringTokenizer("");
		BufferedReader in;

		FastScanner() {
			in = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() throws IOException {
			while (!tok.hasMoreElements())
				tok = new StringTokenizer(in.readLine());
			return tok.nextToken();
		}
	
		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}

	public class TreeOrders {
		int n;
		int[] key, left, right;
		
		void read() throws IOException {
			FastScanner in = new FastScanner();
			n = in.nextInt();
			key = new int[n];
			left = new int[n];
			right = new int[n];
			for (int i = 0; i < n; i++) { 
				key[i] = in.nextInt();
				left[i] = in.nextInt();
				right[i] = in.nextInt();
			}
		}

		void inOrder(int i) 
		{
			ArrayList<Integer> dummy = new ArrayList<Integer>();
			dummy.add(i);

			int j = i;
			while(this.left[i] != -1)
			{
				i = this.left[i];
				dummy.add(i);
			}

			while(dummy.size() > 0)
			{
				System.out.print(this.key[dummy.get(dummy.size() - 1)]+" ");

				if(this.right[dummy.get(dummy.size() - 1)] != -1)
					this.inOrder(this.right[dummy.get(dummy.size() - 1)]);

				dummy.remove(dummy.size() - 1);
			}	
            return;           
		}

		void preOrder(int i) 
		{
			ArrayList<Integer> dummy = new ArrayList<Integer>();
			int j = i;
            dummy.add(i);
            System.out.print(this.key[i]+" ");
			while(this.left[i] != -1)
			{
				i = this.left[i];
				dummy.add(i);
				System.out.print(this.key[i]+" ");
			}
			while(dummy.size() > 0 )
			{
				if(this.right[dummy.get(dummy.size() - 1)] != -1)
					this.preOrder(this.right[dummy.get(dummy.size() - 1)]);
				dummy.remove(dummy.size() - 1);
			}	
             return;             
                        
		}

		void postOrder(int i) 
		{
			ArrayList<Integer> dummy = new ArrayList<Integer>();
			int j = i;
            dummy.add(i);
            while(this.left[i] != -1)  //..go deep down to the left
			{
				i = this.left[i];
				dummy.add(i);
			}
			while(dummy.size() > 0)
			{
				if(this.right[dummy.get(dummy.size() - 1)] != -1)
					this.postOrder(this.right[dummy.get(dummy.size() - 1)]);
				System.out.print(this.key[dummy.get(dummy.size() - 1)]+" ");
				dummy.remove(dummy.size() - 1);
			}	

                        
			return;
		}
	}

	static public void main(String[] args) throws IOException {
            new Thread(null, new Runnable() {
                    public void run() {
                        try {
                            new tree_orders().run();
                        } catch (IOException e) {
                        }
                    }
                }, "1", 1 << 26).start();
	}

	public void print(List<Integer> x) {
		for (Integer a : x) {
			System.out.print(a + " ");
		}
		System.out.println();
	}

	public void run() throws IOException {
		TreeOrders tree = new TreeOrders();
		tree.read();
		//print(tree.inOrder(0));
		tree.inOrder(0);
		System.out.println();
		tree.preOrder(0);
		System.out.println();
		tree.postOrder(0);
	}
}

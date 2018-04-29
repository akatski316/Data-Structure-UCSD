import java.io.*;
import java.util.*;

public class MergingTables {
    private final InputReader reader;
    private final OutputWriter writer;

    public MergingTables(InputReader reader, OutputWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public static void main(String[] args) {
        InputReader reader = new InputReader(System.in);
        OutputWriter writer = new OutputWriter(System.out);
        new MergingTables(reader, writer).run();
        writer.writer.flush();
    }

    class Table {
        Table parent;
        long rank;
        long numberOfRows;

        Table(long numberOfRows) {
            this.numberOfRows = numberOfRows;
            rank = 0;
            parent = this;
        }
        Table getParent() {
            // find super parent and compress path
        	List<Table> intermedtables = new ArrayList<Table>();
        	Table temp = this;
        	while(temp.parent != temp){
        		intermedtables.add(temp);
        		temp = temp.parent;
        	}

        	//System.out.println("root is having rows "+temp.numberOfRows);

        	for(int i = 0;i < intermedtables.size();i++)
        		intermedtables.get(i).parent = temp;

        	return temp;
           /* Table temp = this;
			while(temp.parent != temp) 
				temp = temp.parent;

			return temp;	       
        	*/
        }
    }

    long maximumNumberOfRows = -1;
    Table[] tables;
    int n;
    void merge(Table destination, Table source) {
        Table realDestination = destination.getParent();
        Table realSource = source.getParent();
        if (realDestination == realSource) {
            return;
        }
        realDestination.numberOfRows += realSource.numberOfRows;
        realSource.parent = realDestination;
        realSource.numberOfRows = 0;
        maximumNumberOfRows = Math.max(maximumNumberOfRows, realDestination.numberOfRows);
       /* for(int i = 0;i < n;i++)
        {
        	System.out.print(tables[i].numberOfRows+" ");
        }
        System.out.println("maximumNumberOfRows = "+maximumNumberOfRows);
        */
        // merge two components here
        // use rank heuristic
        // update maximumNumberOfRows
    }

    public void run() {
        n = reader.nextInt();
        int m = reader.nextInt();
        tables = new Table[n];
        for (int i = 0; i < n; i++) {
            long numberOfRows = reader.nextInt();
            tables[i] = new Table(numberOfRows);
            maximumNumberOfRows = Math.max(maximumNumberOfRows, numberOfRows);
        }
        for (int i = 0; i < m; i++) {
            int destination = reader.nextInt() - 1;
            int source = reader.nextInt() - 1;
            merge(tables[destination], tables[source]);
            writer.printf("%d\n", maximumNumberOfRows);
        }
    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

    static class OutputWriter {
        public PrintWriter writer;

        OutputWriter(OutputStream stream) {
            writer = new PrintWriter(stream);
        }

        public void printf(String format, Object... args) {
            writer.print(String.format(Locale.ENGLISH, format, args));
        }
    }
}

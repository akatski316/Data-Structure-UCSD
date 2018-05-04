import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;


public class HashSubstring {

	public static long y = 1;
	public static long p = 1000000007;
	public static long x = (long)(Math.random()*(p))+1;
    private static FastScanner in;
    private static PrintWriter out;

    public static void main(String[] args) throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        printOccurrences(getOccurrences(readInput()));
        out.close();
    }

    public static boolean AreEqual(String pattern,String substring)
    {
    	for(int i = 0;i < pattern.length();i++)
    	{
    		if(pattern.charAt(i) != substring.charAt(i))
    			return false;
    	}
    	return true;
    }

    private static Data readInput() throws IOException {
        String pattern = in.next();
        String text = in.next();
        return new Data(pattern, text);
    }

    private static void printOccurrences(List<Integer> ans) throws IOException {
        for (Integer cur:ans) 
        {
            out.print(cur);
            out.print(" ");
        }
    }

    private static List<Integer> getOccurrences(Data input) {
        String s = input.pattern, t = input.text;
        int m = s.length(), n = t.length();
        int i;
        long lasthash = hashFunc(t.substring(t.length() - s.length(),t.length())),phash = hashFunc(s);
        List<Integer> occurrences = new ArrayList<Integer>();

        //for (int i = 1; i <= m; i++) {
		//	y = (y * x) % p;
		//}

        for ( i = n - m; i > 0; i--)
        { 
		    if(phash != lasthash){	

		   		lasthash = ((x*lasthash)  + (long)t.charAt(i-1) - ((long)t.charAt(i - 1 +s.length()) * y))%p;
		    	if(lasthash < 0)
		    		lasthash = ((lasthash)+p)%p;

		    	continue;
		    }
		    if(AreEqual(s,t.substring(i,i+m)))    							//if(s.equals(t.substring(i,i+m)))
		    {
		    	occurrences.add(0,i);	
			    lasthash = ((x*lasthash)  + (long)t.charAt(i-1) - ((long)t.charAt(i - 1 +s.length()) * y))%p;
			    if(lasthash < 0)
		    		lasthash = ((lasthash)+p)%p;
		    }
		    
		}
		if(phash == lasthash && s.equals(t.substring(0,0+m)))
		{
			occurrences.add(0,0);
		}	
        return occurrences;
    }

    private static List<Integer> getOccurrencesold(Data input) {
		String s = input.pattern, t = input.text;
		int m = s.length(), n = t.length();
		List<Integer> occurrences = new ArrayList<Integer>();
		
		long pHash = hashFunc(input.pattern);
		
		long[] H = precomputeHashes(input);
		
		for (int i = 0; i + m <= n; ++i) {
			if (pHash != H[i]) {
				continue;
			}
			if (AreEqual(t.substring(i, i + m), s)) {
				occurrences.add(i);
			}
		}
		
		return occurrences;
	}

	private static long[] precomputeHashes(Data input) {
		int t = input.text.length();
		int m = input.pattern.length();
		
		// H <- array of length |T| - |P| + 1
		long[] H = new long[t-m+1];
		
		// S <- T[|T|-|P|..|T|-1]
		String s = input.text.substring(t - m);
		
		// H[|T|-|P|] <- PolyHash(S, p, x)
		H[t-m] = hashFunc(s);
		
		/*for (int i = 1; i <= m; i++) {
			y = (y * x) % p;
		}*/
		
		for (int i = t- m - 1; i >=0; i--) {
			long preHash = x * H[i + 1] + input.text.charAt(i) - y * input.text.charAt(i + m);
			while (preHash < 0) {
				preHash += p;
			}
			H[i] = preHash % p;
		}
		return H;
	}


    public static long easyhashFunc(long lasthash,String t,int ind,String s)
    {
    	long hash;
    	hash = ((x*lasthash)  + (long)t.charAt(ind) - ((long)t.charAt(ind+s.length()) * y)%p+p)%p;    
    	return hash;
    }

    public static long hashFunc(String s) {
        long hash = 0;
        if(y == 1)
	        for (int i = s.length() - 1; i >= 0; --i)
	        {
	            hash = ((hash * x) + s.charAt(i)) % p;
	            y = (y*x)%p;
	        }
	    else
	    	for (int i = s.length() - 1; i >= 0; --i)
	        {
	            hash = ((hash * x) + s.charAt(i)) % p;
	        }
	        
        return hash;
    }

    static class Data {
        String pattern;
        String text;
        public Data(String pattern, String text) {
            this.pattern = pattern;
            this.text = text;
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


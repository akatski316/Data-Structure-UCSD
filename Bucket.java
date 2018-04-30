import java.util.*;

public class Bucket
{
	List<String> elems = new ArrayList<String>();

	static int m;

	public static void main(String[]  args)
	{
		Scanner in = new Scanner(System.in);
		m= in.nextInt();
		int queries = in.nextInt();
		Bucket[] buckets = new Bucket[m];

		for(int i = 0;i  < queries;i++)
		{
			String type = in.next();
			if(type.equals("add"))
			{
				String entry = in.next();
				try
				{
					if(!buckets[hashfunc(entry)].elems.contains(entry) || buckets[hashfunc(entry)].elems.size() == 0)
						buckets[hashfunc(entry)].elems.add(0,entry);
					//System.out.println("hash index is "+hashfunc(entry));
				}
				catch(NullPointerException e)
				{
					buckets[hashfunc(entry)] = new Bucket();
					//System.out.println("hash index is "+hashfunc(entry));
					buckets[hashfunc(entry)].elems.add(0,entry);
				}	
			}
			else if(type.equals("find"))
			{
				String entry = in.next();
				try
				{
					if(buckets[hashfunc(entry)].elems.contains(entry))
						System.out.println("yes");
					else
						System.out.println("no");
				}	
				catch(NullPointerException e)
				{
					System.out.println("no");
				}
			}
			else if(type.equals("del"))
			{
				try
				{
					String entry = in.next();
					int index = buckets[hashfunc(entry)].elems.indexOf(entry);
					buckets[hashfunc(entry)].elems.remove(index);
				}
				catch(NullPointerException e)
				{
					continue;
				}
				catch(ArrayIndexOutOfBoundsException k)
				{
					continue;
				}	
			}
			else if(type.equals("check"))
			{
				try
				{	int index = in.nextInt();
					for(int j = 0;j < buckets[index].elems.size();j++)
						System.out.print(buckets[index].elems.get(j)+" ");
					System.out.println();
				}
				catch(NullPointerException e)
				{
					System.out.println();
				}	
			}
		}

	}

	public static int hashfunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * 263 + s.charAt(i)) % 1000000007;
        return (int)hash % m;
    }

}
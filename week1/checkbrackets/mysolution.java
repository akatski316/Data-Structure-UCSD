import java.util.*;

public class mysolution
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		String input;

		int j = 0,i;

		input = in.nextLine();

		if((input.length() == 1 && (input.charAt(0) == '[' || input.charAt(0) == '(' || input.charAt(0) == '{' || input.charAt(0) == ']' || input.charAt(0) == ')' || input.charAt(0) == '}')))
		{
			System.out.println("1");
			System.exit(0);
		}

		char[] a = new char[input.length()];
		int[] index = new int[input.length()];
 		
		for(i = 0;i < input.length();i++)
		{
			if(input.charAt(i) == '[' || input.charAt(i) == '(' || input.charAt(i) == '{')
			{
				a[j] = input.charAt(i);
				index[j] = i + 1; 
				//System.out.println("pushed "+input.charAt(i)+" on stack");
				j++; 
			}
			else if(j > 0)
			{
				if((input.charAt(i) == ']' && a[j - 1] == '[') || (input.charAt(i) == ')' && a[j - 1] == '(') || (input.charAt(i) == '}' && a[j - 1] == '{') )
				{
					//System.out.println("at "+i+" our element is "+input.charAt(i));
					j--;
				}
				else if((input.charAt(i) == ']' && a[j - 1] != '[') || (input.charAt(i) == ')' && a[j - 1] != '(') || (input.charAt(i) == '}' && a[j - 1] != '{'))
				{	
					System.out.println(i+1);
					System.exit(0);
				}
			}
			else if((j == 0 && input.charAt(i) == ']') || (j == 0 && input.charAt(i) == ')') || (j == 0 && input.charAt(i) == '}'))
			{
				System.out.println(i+1);
				System.exit(0);
			}
		}

		if(j > 0)
			System.out.println(index[j - 1]);
		else
			System.out.println("Success");
	}
}
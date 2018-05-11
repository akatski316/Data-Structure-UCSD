import java.util.*;
import java.io.*;

public class Tree
{
	public int key,left,right,parent = -1;
    public String patanahi = null;     //..this variable keeps track whether the node is a left or a 
                                        //,.right child of its parent
	public static int n;
    public static Tree t[];
	
    public static void main(String[] arfs)
    {
    	Scanner in = new Scanner(System.in);
    	int i ;
        n = in.nextInt();
    	t = new Tree[n];
    	for(i = 0;i < n;i++)
        {
            t[i] = new Tree();
        }

        for(i = 0;i < n;i++)
        {
            t[i].key = in.nextInt();
            t[i].left = in.nextInt();
            t[i].right = in.nextInt();
            if(t[i].left != -1)
            {
                t[t[i].left].parent = i;            //..since its a left child of its parent
                t[t[i].left].patanahi = "L";
            }  
            if(t[i].right != -1)
            {  
                t[t[i].right].parent = i;
                t[t[i].right].patanahi = "R";
            }
        }
        for(i = 1;i < n;i++)
        {
            if(t[i].patanahi == "L")
            {
                    if((t[t[i].parent].patanahi == "L" && (t[i].key < t[t[i].parent].key) && (t[i].key < t[t[t[i].parent].parent].key)) || (t[t[i].parent].patanahi == "R" && (t[i].key < t[t[i].parent].key) && (t[i].key >= t[t[t[i].parent].parent].key)))
                    {   
                        continue;
                    }
                    else if((t[t[i].parent].patanahi == null && (t[i].key < t[t[i].parent].key)) || (t[t[i].parent].patanahi == null && (t[i].key < t[t[i].parent].key)) )
                        continue;
                    else 
                    {
                        System.out.println("INCORRECT");
                        System.exit(0);
                    }
                

            }
            else if(t[i].patanahi == "R")
            {
                    if(  (t[t[i].parent].patanahi == "R" && (t[i].key >= t[t[i].parent].key) && (t[i].key >= t[t[t[i].parent].parent].key)) || (t[t[i].parent].patanahi == "L" && (t[i].key >= t[t[i].parent].key) && (t[i].key < t[t[t[i].parent].parent].key))  )
                    {

                        continue;
                    }
                    else if((t[t[i].parent].patanahi == null && (t[i].key >= t[t[i].parent].key)) || (t[t[i].parent].patanahi == null && (t[i].key >= t[t[i].parent].key)))
                        continue;
                    else //if(t[t[i].parent].patanahi == "L" && t[t[i].parent].patanahi == "R")
                    {
                        System.out.println("INCORRECT");
                        System.exit(0);
                    } 
            }
        }

        System.out.println("CORRECT");


    	
    }	
}
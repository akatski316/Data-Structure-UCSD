import java.util.*;
import java.io.*;

public class HeapJob
{
	public int assignedworker;
	public long starttime;
	public long job; 
	public static int n;
	public static int m;
	public static HeapJob[] jobs;


	public static void main(String[] args)
	{
		takeinput();
		solveit();
		for(int i = n;i < m;i++)
			System.out.println(jobs[i].assignedworker+" "+jobs[i].starttime);

	}

	public static void takeinput()
	{
		Scanner in = new Scanner(System.in); 
		n = in.nextInt();
		m = in.nextInt();
		jobs = new HeapJob[m];
		for(int i = 0;i < m;i++){
			jobs[i] = new HeapJob();
			jobs[i].job = in.nextLong();
			if(i < n)
			{
				jobs[i].assignedworker = i;
				jobs[i].starttime = 0;
				System.out.println(jobs[i].assignedworker+" "+jobs[i].starttime);

			}
		}
	}

	public static void solveit()
	{
		int i,min,temp = 0,parent,workeratroot = 0;

	//..heap formation of first n objects of array starts from here	
      for(i = 0;i < n;i++)
      {
        temp = i;
        while(smallestchild(temp) < n && jobs[smallestchild(temp)].job < jobs[temp].job)
            temp = smallestchild(temp);

        if(i != temp)
        {
            HeapJob tmp = jobs[i];
            jobs[i] = jobs[temp];
            jobs[temp] = tmp;

            shiftUp(i);

        }
        else if(i == temp)
        {
            shiftUp(i);
        }

      } 
    //....till here the first n objects of array are getting converted into heap 
      for(i = n;i < m;i++)
      {
      	jobs[i].starttime = jobs[0].job;
      	jobs[0].job += jobs[i].job;
      	jobs[i].assignedworker = jobs[0].assignedworker;
       	shiftDown(0); //...shift he root down
      }
	}

//...shifts the element at i down
	public static void shiftDown(int i)
    {
    	int temp = i;
    	while((jobs[temp].job > jobs[smallestchild(temp)].job && smallestchild(temp) < n) || (jobs[temp].job == jobs[smallestchild(temp)].job && smallestchild(temp) < n && jobs[temp].assignedworker > jobs[smallestchild(temp)].assignedworker))
    	{
    		int k = smallestchild(temp);
    		///..swapping with smallest child, swapping of pointers
    		HeapJob tmpjob = jobs[temp];                
    		jobs[temp] = jobs[smallestchild(temp)];
    		jobs[smallestchild(temp)] = tmpjob;
    		
    		temp = k;     //...cant write temp = smallestchild(temp);because swapping is done
    	}
    }

//...finds the index of smallest child of elememt at index i in a binary tree
    public static int smallestchild(int i)
    {
        if(2*i + 2 < n)
        {
            if(jobs[2*i + 2].job < jobs[2*i + 1].job)
                return 2*i + 2;
            //...if jobs are of same magnitude handle on the basis of index of workers
            else if(jobs[2*i + 2].job == jobs[2*i + 1].job && jobs[2*i + 2].assignedworker < jobs[2*i + 1].assignedworker)
            	return 2*i + 2;
            else
                return 2*i + 1;
        }
        else if(2*i + 1 < n) //... if only one child is present(leftchild) 
        {
            return 2*i + 1;
        }
        else 
            return n; 
    } 


//...shifts the node up 
    public static void shiftUp(int i)
    {
        int parent  = (int)Math.ceil((double)i/(double)2) - 1;
        while((parent >= 0 && jobs[i].job < jobs[parent].job) || (parent >= 0 && jobs[i].job == jobs[parent].job && jobs[i].assignedworker < jobs[parent].assignedworker))
        {
            HeapJob tmpjob = jobs[i];
            jobs[i] = jobs[parent];
            jobs[parent] = tmpjob;

            i = parent;
            parent  = (int)Math.ceil((double)i/(double)2) - 1;
        }
    }     

}
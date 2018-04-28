import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
    }

    public Response Process(Request request) {
        ///////////////...arraylist of object buffer is used as queue if it fills first element is deleted
        if(this.finish_time_.size() == this.size_)
    	{
    		
    		if(request.arrival_time < this.finish_time_.get(this.finish_time_.size() - 1) && request.arrival_time >= this.finish_time_.get(0))
    		{
    			this.finish_time_.remove(0);
    			this.finish_time_.add(this.finish_time_.get(this.finish_time_.size() - 1) + request.process_time);
    			return new Response(false, this.finish_time_.get(this.finish_time_.size() - 2));
    		}
    		else if(request.arrival_time < this.finish_time_.get(0))
    			return new Response(true,-1);
    		else if(request.arrival_time >= this.finish_time_.get(this.finish_time_.size() - 1))
    		{
      			this.finish_time_.add(request.arrival_time + request.process_time);
    			this.finish_time_.remove(0);
    			return new Response(false, request.arrival_time);
    		}
    	}
    	//..below two if statements are executed till our buffer fills for the first time.. 
    	if(this.finish_time_.size() < this.size_ && request.arrival_time >= this.finish_time_.get(this.finish_time_.size() - 1))
    	{
    		this.finish_time_.add(request.arrival_time + request.process_time);
    		return new Response(false, request.arrival_time);

    	}
    	if(this.finish_time_.size() < this.size_ && request.arrival_time < this.finish_time_.get(this.finish_time_.size() - 1))
    	{
       		this.finish_time_.add(request.process_time + this.finish_time_.get(finish_time_.size() - 1));
    		return new Response(false, this.finish_time_.get(finish_time_.size() - 2));
    	}

 		//..an unreachable statement to avoid compile error
    	return new Response(false, 0);
    }

    public int size_;
    public ArrayList<Integer> finish_time_;
}

class process_packages {

    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException 
    {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) 
        {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        responses.add(new Response(false, requests.get(0).arrival_time));
        buffer.finish_time_.add(requests.get(0).arrival_time + requests.get(0).process_time);
        for (int i = 1; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
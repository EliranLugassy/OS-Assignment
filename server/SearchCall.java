package server;

import java.net.Socket;

public class SearchCall implements Runnable{ //consider if will be better to use Callable!!!  

	Socket soc;
	int x;
	Integer y;
	
	CacheManager cache;
	ReadersManager db;
	
	public SearchCall(CacheManager c, ReadersManager d){
		cache = c;
		db = d;
		Socket soc = new Socket();
	}
	
	public void setSocketX(Socket s, int x){
		soc = s;
		this.x = x;
	}
	
	public void run(){
		
		y=-1;
		
		//####	check how it works	####//
		y = cache.getY(x);
		
		if(y != null){// y is in cache
			System.out.println("x="+x+" query found in cache with y="+"y");//check what to write correctly by the assignment
			
			return y;//trough the socket
		}
		else{
			y = db.getY(x);
			
			System.out.println("\n x="+x+" query found at the DB with y="+"y\n");//check what to write correctly by the assignment
			
			return y;//trough the socket
		}
						
	}
	
}

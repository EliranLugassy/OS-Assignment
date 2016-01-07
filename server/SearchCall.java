package server;

import java.util.concurrent.Callable;

public class SearchCall implements Callable<Integer>{ //consider if will be better to use Callable!!!  

	int x;
	CacheManager cache;
	ReadersManager db;
	
	public SearchCall(CacheManager c, ReadersManager d){
		cache = c;
		db = d;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public Integer call(){
		
		Integer y=-1;
		
		//####	check how it works	####//
		y = cache.getY(x);
		
		if(y != null){// y is in cache
			System.out.println("x="+x+" query found in cache with y="+"y");
			return y;
		}
		else{
			y = db.getY(x);
			
			System.out.println("\n x="+x+" query found at the DB with y="+"y\n");
			return y;
		}
						
	}
	
}

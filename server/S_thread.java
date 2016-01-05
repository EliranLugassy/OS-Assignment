package server;

public class S_thread extends Thread{ //consider if will be better to use Callable!!!  

	int x;
	Cache cache;
	
	public S_thread(){}
	
	public void conveyX(int x){
		this.x = x;
	}
	
	public void run(){
		
		//####	check how it works	####//
		int y = cache.getY(x);
				
				
	}
	
}

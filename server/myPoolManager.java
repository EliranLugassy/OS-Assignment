package server;

public class myPoolManager extends Thread{

	int size;
	SearchCall[] pool;
	
	public myPoolManager(int size, SearchCall[] pool) {
		super();
		this.size = size;
		this.pool = pool;
	}
	
	
	
	
	
}

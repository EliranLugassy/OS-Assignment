package server;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Cache extends Thread{

	private int size, threshHold, minimal_Value_In_Chache;
	
	private Thread[] pool;
	private HashMap<Integer, QueryUnit> map;
	ReentrantLock rlock;
	
	public Cache(int _C, int _M) {
		
		size = _C;
		threshHold = _M;
		minimal_Value_In_Chache=0;
		
		pool = new Thread[5];
		map = new HashMap<Integer, QueryUnit>();
		rlock= new ReentrantLock();
		
	}

	public int getY(int x) {

		rlock.lock();
		//***check if it would be better to search few together one by one***
		

	}
	
	

}

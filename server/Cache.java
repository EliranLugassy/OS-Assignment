package server;

import java.util.HashMap;

public class Cache extends Thread{

	private int size, threshHold, minimal_Value_In_Chache;
	
	private Thread[] pool;
	private HashMap<Integer, QueryUnit> map;
	
	public Cache(int _C, int _M) {
		
		size = _C;
		threshHold = _M;
		minimal_Value_In_Chache=0;
		
		pool = new Thread[5];
		map = new HashMap<Integer, QueryUnit>();
		
	}
	
	

}

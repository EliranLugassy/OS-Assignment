package server;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class Cache extends Thread{

	/////////////////////#####################		local variables		######################///////////////////////////

	private int size, threshHold, minZ, _rangeForY;
	public final int MAX_NUM_OF_CLIENTS = 5;

	//	private Thread[] pool;///   ??? or the next line??

	int[] updateList;

	public ExecutorService pool;

	private HashMap<Integer, QueryUnit> map;
	ReentrantLock rlock;

//	public int[] cacheXs, cacheYs;

	/////////////////////#####################		constructor		######################///////////////////////////

	public Cache(int C, int M, int L) {

		size = C;
		threshHold = M;
		_rangeForY = L;
		minZ=0;
		
		updateList = new int[10];

		pool = Executors.newFixedThreadPool(MAX_NUM_OF_CLIENTS);

		map = new HashMap<Integer, QueryUnit>(C);
		rlock= new ReentrantLock();

	}



	/////////////////////#####################		functions		######################///////////////////////////

	public void run(){

	}


	@SuppressWarnings("finally")
	public int getY(int x) {

		int y=-1;
		
		try {
			
			Future<Integer> result = pool.submit(new CacheCaller(x, map, rlock));
			
				y = result.get();
				
		} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
		 catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally{
			return y;
		}
	}



	public void add(QueryUnit q){

		rlock.lock();

		if(map.size()==size){
			map.remove(q.getX());//return null iff x isn't in map already
		}
		map.put(q.getX(),q);

		int z = q.getZ();
		if(z < minZ){
			minZ = z;
		}

		rlock.unlock();

	}

	int getMinZ(){
		return minZ;
	}


}

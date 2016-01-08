package server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class Cache extends Thread{

	/////////////////////#####################		local variables		######################///////////////////////////

	private int maxMapSize, threshHold, minZ, _rangeForY;
	public final int MAX_NUM_OF_CLIENTS = 5;

	//	private Thread[] pool;///   ??? or the next line??

	QueryUnit[] updateList;

	public ExecutorService pool;

	private HashMap<Integer, QueryUnit> map;
	ReentrantLock rlock;

//	public int[] cacheXs, cacheYs;

	/////////////////////#####################		constructor		######################///////////////////////////

	public Cache(int C, int M, int L) {

		maxMapSize = C;
		threshHold = M;
		_rangeForY = L;
		minZ=0;
		
		updateList = new QueryUnit[10];

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

		if(map.size() < maxMapSize){
			map.put(q.getX(),q);
		}
		else{
			map.remove(minZ);//return null iff x isn't in map already
		}

		int z = q.getZ();
		if(z < map.get(getMinZ()).getZ()){
			minZ = q.getX();
		}

		rlock.unlock();

	}

	int getMinZ(){
		
		Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        HashMap.Entry pair = (HashMap.Entry)it.next();
	        QueryUnit q = (QueryUnit) pair.getValue();
	        if(q.getZ()<map.get(minZ).getZ()){
	        	minZ = q.getX();
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		return map.get(minZ).getX();
	}

	
	
	
	
	
	/////////////////##########################		INNER CLASS TO UPDATING CACHE		########################//////////////////////
	
	class Update extends Thread{
		
		public void run(){
			
			checkForDuplicates(updateList);
			
			rlock.lock();
			
			for (int i = 0; i < updateList.length; i++) {
				 if(updateList[i]==null) continue;
				if( map.size()<maxMapSize || canEnter(updateList[i])){
						
					add(updateList[i]);
					
					
					
				}
				
			}
			
			rlock.unlock();
			
		}

		
		private void checkForDuplicates(QueryUnit[] quArr) {
			for(int i=0;i<quArr.length;i++){
				for(int j=i+1; j<quArr.length; j++){
					if(quArr[i].getX()==quArr[j].getX()){
						quArr[j].addZfrom(quArr[i]);
						quArr[i]=null;
					}
				}
			}
			
		}

		
	}






	public boolean canEnter(QueryUnit unit) {
		if(unit.getZ() > getMinZ()){
			return true;
		}
		return false;
	}
	
	

}

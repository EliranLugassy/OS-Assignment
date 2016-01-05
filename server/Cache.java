package server;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Cache extends Thread{

	private int size, threshHold, minimal_Value_In_Chache;
	
	private Thread[] pool;	///////////// 		needed?????????????		/////////////
	private HashMap<Integer, QueryUnit> map;
	ReentrantLock rlock;
	
	Updater updtr;
	int[] updateList;
	
	int minZ;
	
	public Cache(int C, int M) {
		
		size = C;
		threshHold = M;
		minimal_Value_In_Chache=0;
		
		pool = new Thread[5];
		map = new HashMap<Integer, QueryUnit>(C);
		rlock= new ReentrantLock();
		
		updtr = new Updater();
		updateList = new int[C];
	}

	
	public int getY(int x) {

		int y=-1;
		
		rlock.lock();
		//***check if it would be better to search few together one by one***
		QueryUnit qy = map.get(x);
		if(qy!=null){
			y = qy.getY();
		}
		rlock.unlock();
		
		return y;

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
	
	// edit require
	public void update(){
		
		updtr.setList(updateList);
		
	}

	/////////////////////////			INNER CLASS UPDATER			///////////////////
	
	class Updater extends Thread{
		
		public Updater(){
			
		}
		//ליצור תהליכון שרץ בלולאה אינסופית וישן זמן מסוים וכל פעם שהזמן נגמר הוא נועל את הקאש ואת המסד נתונים ומבצע עדכון של הקאש
	
		
		public void setList(int[] q){
			
		}
	}
}

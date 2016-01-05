package server;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Cache extends Thread{

	private int size, threshHold, minimal_Value_In_Chache;
	
	private Thread[] pool;	///////////// 		needed?????????????		/////////////
	private HashMap<Integer, QueryUnit> map;
	ReentrantLock rlock;
	
	Updater updtr;
	
	
	public Cache(int _C, int _M) {
		
		size = _C;
		threshHold = _M;
		minimal_Value_In_Chache=0;
		
		pool = new Thread[5];
		map = new HashMap<Integer, QueryUnit>();
		rlock= new ReentrantLock();
		
		updtr = new Updater();
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


	


	/////////////////////////			INNER CLASS UPDATER			///////////////////
	
	class Updater extends Thread{
		
		//ליצור תהליכון שרץ בלולאה אינסופית וישן זמן מסוים וכל פעם שהזמן נגמר הוא נועל את הקאש ואת המסד נתונים ומבצע עדכון של הקאש
	
		
	}
}

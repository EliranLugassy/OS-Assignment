package server;

import java.util.concurrent.Semaphore;

public class Reader extends Thread{ //consider if will be better to use Callable!!! 

	int[] readersIn;

	Semaphore Rmutex, Wmutex, Mutex2;//= 1;

	Semaphore Rdb, Wdb;

	ReadersManager db;

	public Reader(int[] readersIn, Semaphore rmutex, Semaphore wmutex, Semaphore mutex2, Semaphore rdb, Semaphore wdb, ReadersManager db) {

		this.readersIn = readersIn;
		this.db = db; 

		Rmutex = rmutex;
		Wmutex = wmutex;
		Mutex2 = mutex2;
		Rdb = rdb;
		Wdb = wdb;

	}



	/**
	 * reading Query from DB
	 * @return QueryUnit 
	 */
	public QueryUnit reader(int x){

		QueryUnit qu = null;
		
		while(true){
			
			try {
				Mutex2.acquire();

				Rdb.acquire();
				Rmutex.acquire();
				readersIn[0]+=1;
				
				if(readersIn[0] == 1)
					Wdb.acquire();;
					Rmutex.release();
					Rdb.release();
					Mutex2.release();

					qu = read_data_base(x);

					Rmutex.acquire();
					readersIn[0] -= 1;
					if(readersIn[0] == 0)
						Wdb.release();
					Rmutex.release();
					
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return qu;
		}
	}



	public QueryUnit read_data_base(int x) {

		QueryUnit qu=null;
		
		// implement
		
		return qu;

	}
	
}

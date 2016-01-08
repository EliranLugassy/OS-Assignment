package server;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ReadersManager {


	int[] readersIn;

	Semaphore Rmutex, Wmutex, Mutex2;//= 1;

	Semaphore Rdb, Wdb;//= 1; 
	

	private Reader[] reader;
	private Writer writer;
	protected int Y,L,M;

	HashMap<Integer,File> db;

	// DB db; -> ?

	public ReadersManager(int Y, int L, int M){

		reader = new Reader[Y];

		readersIn = new int[1];
		
		Rmutex = new Semaphore(1);
		Wmutex = new Semaphore(1);
		Mutex2 = new Semaphore(1);

		Rdb = new Semaphore(1);
		Wdb = new Semaphore(1);

		try {
			Rmutex.acquire(1);
			Wmutex.acquire(1);
			Rdb.acquire(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public int getY(int x) {
		// TODO Auto-generated method stub
		return 0;
	}





}

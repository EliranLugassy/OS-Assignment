package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PoolManager {

//	int numOfThreads;
	private S_Thread[] pool;
	private SearchCall[] _workers;
	
	private BlockingQueue<SearchCall> _queue;
	
	//may not need
	private Server _server;
	
	public static final int MAX_NUM_OF_CLIENTS=5;
	private int submitNumber = 0;
	
	/**
	 * main constructor of poolManager
	 * @param S size of threadPool, number of S_Threads 
	 * @param server the server that give Tasks to this poolManager
	 */
	public PoolManager(int S, Server server) {
		
//		numOfThreads = s;
		this.pool = new S_Thread[S];
		_workers = new SearchCall[MAX_NUM_OF_CLIENTS];
		_server = server;

		_queue = new ArrayBlockingQueue<SearchCall>(S);//check the efficient of the size per thread!
		
		//initial it once per server only
		for (int i = 0; i < _workers.length; i++) {
			_workers[i]=new SearchCall(_server.getCache(), _server.getReaders());
		}
		
		for (int i = 0; i < pool.length; i++) {
			pool[i] = new S_Thread(_queue);
		}
		
		for(S_Thread t:pool){
			t.start();
		}
		
	}
	
	
	
	public void setTask(Socket socket) throws IOException {
		
		//getting the x from socket to convey it to the callable search thread
		_workers[submitNumber].setSocket(socket);
		try {
			_queue.put(_workers[submitNumber]);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("cannot assign task to worker!");
		}
		finally{
			submitNumber = (submitNumber+1)%MAX_NUM_OF_CLIENTS;
		}
		
//		
//		pool[t] = new S_Thread(t,_workers[t], _queue);
//
//		pool[t].start();
//		
		 
	}

	

	
	/*
	public void setTask(int x){
		_queue.add(x);
	}
	 */

	
	

}

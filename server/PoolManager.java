package server;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class PoolManager {

/////////////////////#####################		local variables		######################///////////////////////////

	private S_Thread[] pool;
	private SearchTask[] _workers;
	
	private BlockingQueue<SearchTask> _queue;
	
	private Server _server;
	
	public static final int MAX_NUM_OF_CLIENTS=5;
	private int submitNumber = 0;
	
	ReentrantLock addLock;
	
/////////////////////#####################		constructor		######################////////////////////////////
	/**
	 * main constructor of poolManager
	 * @param S size of threadPool, number of S_Threads 
	 * @param server the server that give Tasks to this poolManager
	 */
	public PoolManager(int S, Server server) {
		
		this.pool = new S_Thread[S];
		
		_workers = new SearchTask[MAX_NUM_OF_CLIENTS];
		
		_server = server;

		_queue = new ArrayBlockingQueue<SearchTask>(MAX_NUM_OF_CLIENTS);
		
		addLock = new ReentrantLock();
		
		//initial them once per manager
		for (int i = 0; i < _workers.length; i++) {
			_workers[i]=new SearchTask(_server.getCache(), _server.getReaders());
		}
		
		for (int i = 0; i < pool.length; i++) {
			pool[i] = new S_Thread(_queue);
		}
		
		for(S_Thread t:pool){
			t.start();
		}
		
	}
	
	
/////////////////////#####################			functions			######################///////////////////////////	
	/**
	 * set a new task (number x) to execute when running
	 * @param socket where to pull the x for the execute from
	 */
	public void setTask(Socket socket) {
		
		addLock.lock();
		
		_workers[submitNumber].setSocket(socket);
		try {
			_queue.put(_workers[submitNumber]);
		} catch (InterruptedException e) {

			e.printStackTrace();
			System.err.println("cannot assign task to worker!");
		}
		finally{
			submitNumber = (submitNumber+1)%MAX_NUM_OF_CLIENTS;
			addLock.unlock();
		}
		
		 
	}

	

	
	/*
	public void setTask(int x){
		_queue.add(x);
	}
	 */

	
	

}

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
	
	private BlockingQueue<Integer> _queue;
	
	//may not need
	private Server _server;
	
	
	
	/**
	 * main constructor of poolManager
	 * @param S size of threadPool, number of S_Threads 
	 * @param server the server that give Tasks to this poolManager
	 */
	public PoolManager(int S, Server server) {
		
//		numOfThreads = s;
		this.pool = new S_Thread[S];
		_workers = new SearchCall[S];
		_server = server;

		_queue = new ArrayBlockingQueue<Integer>(5);//check the efficient of the size per thread!
		
		//*****	maybe not need	*****
		for (int i = 0; i < _workers.length; i++) {
			_workers[i]=new SearchCall(_server.getCache(), _server.getReaders());
		}//*****	until here	******
		
		for (int i = 0; i < pool.length; i++) {
			pool[i] = new S_Thread(i, _workers[i], _queue);
		}
		
		for(S_Thread t:pool){
			t.start();
		}
		
	}
	
	
	
	public void setTask(int x){
		_queue.add(x);
	}
	
	
	public void setTask(Socket socket, int t) {
		
		try {
					
			//getting the x from socket to convey it to the callable search thread
			_workers[t].setSocketX(socket, new DataInputStream(socket.getInputStream()).readInt());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		pool[t] = new S_Thread(t,_workers[t], _queue);

		pool[t].start();
		
		 
	}
	
	


}

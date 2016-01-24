package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread{

	//////////**********			server creating parameters 		***********//////////

	private PoolManager poolManage;	//  need/guaranteed to search the given X, size: S
	private Cache cache;	// the cache, (extends Thread???), storing freq. query's 
	private ReadersManager readers;	// the only Threads that can read from the DB
	
	
	//////////**********		other local variables 		**********//////////
	private ServerSocket _myServerSocket;
	private int _port;

	//////////##########			constructors			##########//////////
	/**
	 * the main and classic constructor - getting all necessary arguments
	 * 
	 */
	public Server(int S, int C, int M, int L, int Y, int port){

		_port = port;
		//		_S = number of allowed S-threads*/
		poolManage = new PoolManager(S,this);

		//		_C = size of the cache.*/
		//		_M = least number of times a query has requested to be allowed to enter the cache*/
		//		_L = the range to get random Y for each given X to search*/
		cache = new Cache(C,M,L);
//		cache.start();
		
		//		_Y = number of reader threads*/
		readers = new ReadersManager(Y, L, M);	//	TODO maybe made a DB object to manage all of Readers & Writers, they will inherit DB

	}


	
	///// *****			ctor for tests only!			*****/////
	/*
	public Server(int _L, int _port, Semaphore m) {

		this._L = _L;


		this._port = _port;

		_syncQustAns = m;

	}
	 */

	
	
/////////////////////#####################			run			######################///////////////////////////	
	
	/**
	 * run method to implement the main operation
	 * @Ovrride run from java class Thread
	 */
	public void run(){


		try {
			
			_myServerSocket = new ServerSocket(_port); 

			while(true){
				
				Socket soc = _myServerSocket.accept();

				poolManage.setTask(soc);

			}


		} catch (IOException e) {
			e.printStackTrace();

		}


	}


	//////////##########			functions for maintain			##########//////////



	Cache getCache(){
		return this.cache;
	}

	ReadersManager getReaders(){
		return this.readers;
	}
	
	
	public static void main(String[] args) {
		
		Server s = new Server(5, 5, 5, 5, 5,52334);
		
		s.start();
	}
}



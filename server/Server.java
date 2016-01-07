package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;


public class Server extends Thread{

	//*****	server creating parameters ******//
	private int _S, _C, _M, _L, _Y;

	private PoolManager poolMan;	//  need/guaranteed to search the given X, size: S
	private CacheManager cahce;	// the cache, (extnds Thread???), storing freq. querys 
	private ReadersManager readers;	// the only Threads that can read from the DB
	private Writer writer;


	//***** other local variables *****//
	private ServerSocket _myServerSocket;
	private Socket[] soc;
	private int _port;

	DataInputStream _input_from_client;
	DataOutputStream _send_to_client;

	Semaphore _syncQustAns; // think again on this need


	//////////##########			constructors			##########//////////
	/**
	 * the main and classic ctor - getting all necessary args
	 * 
	 */
	public Server(int S, int C, int M, int L, int Y){

				_S = S; /*number of allowed S-threads*/
		poolMan = new PoolManager(S,this);

		//		_C = C; /*size of the cache.*/
		//		_M = M; /*least number of times a query has requested to be allowed to enter the cache*/

		//		_L = L; // the range to get random Y for each given X to search

		cahce = new CacheManager(C,M,L);

		//		_Y = Y;
		readers = new ReadersManager(Y, L);	//		maybe made a DB object to manage all of Readers & Writers, they will inherit DB
		writer = new Writer();
		
		
		/////       initial    sockets       ???
		soc = new Socket[5];

	}


/*
	//	ctor for tests only!	//
	public Server(int _L, int _port, Semaphore m) {

		this._L = _L;


		this._port = _port;

		_syncQustAns = m;

	}
*/
	
	//////////##########			run			##########//////////

	/**
	 * run method to implement the main operation
	 * @Ovrride run from java class Thread
	 */
	public void run(){


		try {

			_myServerSocket = new ServerSocket(_port); 

			while(true){
				
				for(int t=0; ; t = (t+1)%_S){
//					soc[t] = _myServerSocket.accept();

					poolMan.setTask(_myServerSocket.accept());
					
					
					//_syncQustAns.acquire();

//					int x = _input_from_client.readInt();
//					System.out.println("Server got: "+x);



					//				_syncQustAns.release();

//					if(x==-1){
//						break;
//					}
				}

//			_myServerSocket.close();
			}
			//			soc.close();



			/*consider later if this can be removed or transfering to other place*/
			/*until here*/

		} catch (IOException e) {
			e.printStackTrace();
			//		} catch (InterruptedException e) {
			//			// TODO Auto-generated catch block
			//			e.printStackTrace();
		}


	}

	
	//////////##########			functions for maintain			##########//////////

	
	
	CacheManager getCache(){
		return this.cahce;
	}

	ReadersManager getReaders(){
		return this.readers;
	}
}



package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import sun.awt.Mutex;

import com.sun.security.ntlm.Client;


public class Server extends Thread{

	//*****	server creating parameters ******//
	private int _S, _C, _M, _L, _Y;
	
	private SThread[] search;	// extnds Thread, need to search the given X, size: S
	private Cache cache;	// the cache, extnds Thread, storing freq. querys 
	private Readers[] reader;	// the only Threads that can read from the DB

	
	//***** other local variables *****//
	private ServerSocket _myServerSocket;
	private Socket soc;
	private int _port;

	DataInputStream _input_from_client;
	DataOutputStream _send_to_client;

	Semaphore _syncQustAns; // think again on this need

	
	//##	constructors		##//
	/**
	 * the main and classic ctor - getting all necessary args
	 * 
	 */
	public Server(int S, int C, int M, int L, int Y){
		
		_S = S;
		search = new SThread[_S];
		
		_C = C;
		_M = M;
		cache = new Cache(_C,_M);

		_L = L; // the range to get random Y for each given X to search
		
		_Y = Y;
		reader = new Readers[_Y];
		
	}
	
	
	
	//	ctor for tests only!	//
	public Server(int _L, int _port, Semaphore m) {

		this._L = _L;
		
		
		this._port = _port;

		_syncQustAns = m;

	}

	public void run(){


		try {

			_myServerSocket = new ServerSocket(_port); 

			while(true){
				soc = _myServerSocket.accept();
				
				_syncQustAns.acquire();
				
				_input_from_client = new DataInputStream(soc.getInputStream());
				_send_to_client = new DataOutputStream(soc.getOutputStream());

				int x = _input_from_client.readInt();
				System.out.println("Server got: "+x);

				/*trying to send some response*/
				_send_to_client.write(x+2);
				_syncQustAns.release();

				if(x==-1){
					break;
				}

			}
			soc.close();
			_myServerSocket.close();



			/*consider later if this can be removed or transfering to other place*/
			/*until here*/

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


}



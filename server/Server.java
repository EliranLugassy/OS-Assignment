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

	private int _L;
	private ServerSocket _myServerSocket;
	private Socket soc;
	private int _port;

	DataInputStream _input_from_client;
	DataOutputStream _send_to_client;

	Semaphore _syncQustAns;

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



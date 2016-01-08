package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SearchTask implements Runnable{ 

/////////////////////#####################		local variables		######################///////////////////////////	
	Socket soc;
	int x;
	Integer y;

	Cache cache;
	ReadersManager db;

	DataInputStream _getXFromClient;
	DataOutputStream _sendToClient;


/////////////////////#####################		constructor		######################///////////////////////////
	/**
	 * constructor initial the cache+data-base to ask them query from
	 * @param c the cache
	 * @param d the DB
	 */
	public SearchTask(Cache c, ReadersManager d){
		cache = c;
		db = d;
	}

	
	
/////////////////////#####################		functions		######################///////////////////////////
	/**
	 * set this worker from which socket to take the X for searching
	 * @param s
	 */
	public void setSocket(Socket s){
		soc = s;
	}

	
	/**
	 * run function of the worker to search X where it need to be and return answer Y
	 */
	public void run(){

		y=-1;

		try {
			this.x = new DataInputStream(soc.getInputStream()).readInt();


			//#### TODO	- check how it works	####//
			y = cache.getY(x);

			if(y != null){// means y is in cache
				System.out.println("x="+x+" query found in cache with y="+"y");//check what to write correctly by the assignment


				//			return y trough the socket
				_sendToClient = new DataOutputStream(soc.getOutputStream());
				_sendToClient.writeInt(y);

			}
			else{
				y = db.getY(x);

				System.out.println("\n x="+x+" query found at the DB with y="+"y\n");//check what to write correctly by the assignment


				//			return y trough the socket
				_sendToClient = new DataOutputStream(soc.getOutputStream());
				_sendToClient.writeInt(y);


			}
		}//end of try
		
		catch (IOException e) {
			e.printStackTrace();
		}

	} //end run func

}

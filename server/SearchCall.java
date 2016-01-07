package server;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SearchCall implements Runnable{ //consider if will be better to use Callable!!!  

	Socket soc;
	int x;
	Integer y;

	CacheManager cache;
	ReadersManager db;

	DataInputStream _getXFromClient;
	DataOutputStream _sendToClient;


	public SearchCall(CacheManager c, ReadersManager d){
		cache = c;
		db = d;
	}

	public void setSocket(Socket s){
		soc = s;
	}

	public void run(){

		y=-1;

		try {
			this.x = new DataInputStream(soc.getInputStream()).readInt();


			//####	check how it works	####//
			y = cache.getY(x);

			if(y != null){// y is in cache
				System.out.println("x="+x+" query found in cache with y="+"y");//check what to write correctly by the assignment

				//			return y;//trough the socket

				_sendToClient = new DataOutputStream(soc.getOutputStream());
				_sendToClient.writeInt(y);

			}
			else{
				y = db.getY(x);

				System.out.println("\n x="+x+" query found at the DB with y="+"y\n");//check what to write correctly by the assignment

				//			return y;//trough the socket

				_sendToClient = new DataOutputStream(soc.getOutputStream());
				_sendToClient.writeInt(y);


			}
		}//end of try
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} //end run func

}

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread{

	//the main args of class
	private int _R;
	private String _fileName;

	//client side args
	private Socket _mySocket;
	private int _port = 50000;
	private DataInputStream _input_from_server;
	private DataOutputStream _send_to_server;
	
	
	
	
	public Client(int R, String fileName) {

		_R = R;
		_fileName = fileName;
		
	}



	public void run(){
	

		
		try {

			_mySocket = new Socket("localhost", _port);

			_input_from_server = new DataInputStream(_mySocket.getInputStream());
			
			_send_to_server = new DataOutputStream(_mySocket.getOutputStream());
			
			
			while(true){
				
				
				//rand X+Px - need to find the algo.
				int x = (int)Math.random()*(_R) + 1;
				
				System.out.println("Client "+this.getName()+": sending x");
				
				_send_to_server.writeInt(x);
				
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				
				
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
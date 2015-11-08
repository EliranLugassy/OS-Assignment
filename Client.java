import java.io.DataInputStream;	
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 
 * @author Evyatar Gerslte and Eliran Lugassy
 * this class represent the client side
 *
 */
public class Client extends Thread{

	//the main args of class
	private int _R;
	private String _fileName;

	//client side args
	private Socket _mySocket;
	private int _port = 50000;
	private DataInputStream _input_from_server;
	private DataOutputStream _send_to_server;



	/**
	 * Constructor who get an args
	 * @param R - this number indicates the end of the range. [1,R] (include them)
	 * @param fileName - this is the name of file who contains the Probabilities to number from the range
	 */
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
				int x = randomX(); //if x get 0 value -> the read from file failed
				System.out.println("Client "+this.getName()+": sending x");
				_send_to_server.writeInt(x);// wait for responding of the server
				//when get the response we need to print "Client : got reply y for query  x"

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

	// [      ]
	private int randomX() {


		try {

			FileInputStream fis = new FileInputStream(_fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			int [] probArr = new int[1000];
			double prob = 0;
			int rowInFile=1;
			int indexOnProbArr=0;

			while(rowInFile<=_R){ // 1 to R (include)
				prob = ois.readDouble(); // 0.2 // i=1 
				prob *= 1000; // to get performance number
				while(indexOnProbArr<prob){
					probArr[indexOnProbArr++] = rowInFile;
				}
				rowInFile++;
			}
			ois.close();
			fis.close();

			Random xQuery = new Random();
			int chooseX = xQuery.nextInt(1000);
			return probArr[chooseX];
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		return 0;
	}



public static void main(String[] args) {
	
}

}
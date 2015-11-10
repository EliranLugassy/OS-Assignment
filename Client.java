import java.io.BufferedReader;
import java.io.DataInputStream;	
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * 
 * @author Evyatar Gerslte and Eliran Lugassy
 * this class represent the client side
 */
public class Client extends Thread{

	/**
	 * the main args of class
	 * _R - the top value of the range to choose a number According to a known probability 
	 * _fileName - this is the file who contains the probabilities to the numbers 
	 */
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

				int queryX = getRandomX(); //if x get 0 value -> the read from file failed

				System.out.println("Client "+this.getName()+": sending"+queryX+".");

				_send_to_server.writeInt(queryX);// wait for responding of the server
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


	/**
	 * this function Represents a Retrieval of number in range by Probabilistic method.
	 * we build an array with length = 1000 to Store the number That way possible to take a random
	 * number with Equal probability among all elements of an array.
	 * @return x who Selected by Probabilistic method or 0 if the read from file is failed.
	 */
	private int getRandomX() {

		try {

			FileReader fr = new FileReader(_fileName);
			BufferedReader br = new BufferedReader(fr);
			int [] probArr = new int[1000];
			double prob = 0;
			int rowInFile=1;
			int indexOnProbArr=0;

			while(rowInFile<=_R){ // 1 to R (include them)

				prob = Double.parseDouble(br.readLine());

				if(prob == -1){
					break;
				}
				prob *= 1000; // to get performance number
				int k=0;
				while(k<prob){
					probArr[indexOnProbArr] = rowInFile;
					indexOnProbArr++;
					k++;
				}
				rowInFile++;
			}

			br.close();
			fr.close();

			Random xQuery = new Random();
			int chooseX = xQuery.nextInt(1000);
			return probArr[chooseX];

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
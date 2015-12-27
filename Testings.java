import java.util.concurrent.Semaphore;

import server.Server;
import sun.awt.Mutex;
import client.Client;

//run - function
//start - run as a Thread and call to run
public class Testings {


	public static void main(String[] args) {

		int port=52334;
		Semaphore m = new Semaphore(0);
		m.release(2);

		Server myServer = new Server(10, port, m);
		myServer.start();
		Client myClient = new Client(3, "C:/Users/Eliran Lugassy/Desktop/prob.txt", 52334, m);
		myClient.start();


	}

}

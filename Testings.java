import server.Server;
import client.Client;

	//run - function
	//start - run as a Thread and call to run
public class Testings {

	
	public static void main(String[] args) {
		
		Server myServer = new Server(10, 52333);
		Client myClient = new Client(3, "C:/Users/Eliran Lugassy/Desktop/prob.txt");
		myServer.start();
		myClient.start();
		
		
	}

}

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.Random;


public class Testings {

	static int _R=3;
	static String _fileName = "C:/Users/Eliran Lugassy/Desktop/a.txt";

	public static void main(String[] args) {




		try {

			FileReader fr = new FileReader(_fileName);
			BufferedReader br = new BufferedReader(fr);
			int [] probArr = new int[1000];
			double prob = 0;
			int rowInFile=1;
			int indexOnProbArr=0;


			while(rowInFile<=_R){ // 1 to R (include)
				
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

			System.out.println(probArr[chooseX]);


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}

}

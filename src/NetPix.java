import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.InflaterInputStream;

public class NetPix {
	public static void main(String[] args) throws IOException, InterruptedException {
		// get hashmap from file
		System.out.println("Welcome to NetPix");
		System.out.println("Please wait. Deserializing HashMap...");
		HashMap<String, double[][]> moviehm = null;
		// deserialize hashmap from file
		try {
			FileInputStream fis = new FileInputStream("movieHash.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			moviehm = (HashMap) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
		System.out.println("Deserialized HashMap.");
		Scanner in = new Scanner(System.in);
		boolean userAccepts = false;
		// get the input from the user until they are satisfied
		while (!userAccepts){
			System.out.println("Press:\n1 for Action\n2 for Adventure\n3 for Comedy\n4 for Crime"
					+ "\n5 for Documentary\n6 for Drama\n7 for Horror\n8 for Musical\n9 for Sci-Fi\n");
			int pick = in.nextInt();
			// call the sort 
			Map<Integer, String> map = HashMapSort.sortByValues(moviehm, pick+1);
			System.out.println("Top Suggestion:");
			Set set2 = map.entrySet();
			Iterator iterator2 = set2.iterator();
			Random random = new Random();
			int randomNum = random.nextInt(101);
			Map.Entry me2 = (Map.Entry) iterator2.next();
			for (int i=0;i<randomNum;i++){
				me2 = (Map.Entry) iterator2.next();
			}
			String choiceID = (String) me2.getKey();
			// open the url 
			URL oracle = new URL(
					"http://www.amazon.com/dp/"+choiceID);	
			URLConnection yc = oracle.openConnection();
			yc.setRequestProperty("User-Agent", "Mozilla/5.0");
			InputStream IS = yc.getInputStream();
			InputStreamReader ISR = new InputStreamReader(IS);
			BufferedReader input = new BufferedReader(ISR);
			String inputLine;
			String title;
			String webPage="";
			// get the movieID
			while ((inputLine = input.readLine()) != null){
				if (inputLine.startsWith("<title>")){
					System.out.println(inputLine.substring(19, inputLine.length()-8));
					break;
				}
				}
			System.out.println("Press 1 to accept or 2 to retry");
			int ok = in.nextInt();
			if (ok==1){
				userAccepts=true;
			}
			input.close();
		}
		System.out.println("Enjoy the Movie!");
	}

}

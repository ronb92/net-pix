import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;

public class WriteHashMap {
	public static void main(String [] args){
		//open the file
		File movieHashFile = new File("movieHash.ser");
		//create the hahsmap 
		HashMap<String,double[][]> movieHash = MovieHashMap.MovieHash();
	try
    {		// write the hashmap
           FileOutputStream fos =
              new FileOutputStream(movieHashFile);
           ObjectOutputStream oos = new ObjectOutputStream(fos);
           oos.writeObject(movieHash);
           oos.close();
           fos.close();
           System.out.printf("Serialized HashMap data is saved in hashmap.ser");
    }catch(IOException ioe)
     {
           ioe.printStackTrace();
     }
}
}

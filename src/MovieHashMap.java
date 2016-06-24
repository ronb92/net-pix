import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class MovieHashMap {
	public static HashMap<String,double[][]> MovieHash(){
		int minReviewCount = 20;
		File movies = new File("movies.txt");
		List<String> done = new ArrayList<String>();
		HashMap<String,double[][]> moviehm = new HashMap<String,double[][]>();
		double productCount = 0;
		int [] loadMilestone = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		try (BufferedReader br = new BufferedReader(new FileReader(movies))) {
			System.out.println("Analyzing database...");
		    String line;
		    while ((line = br.readLine()) != null) {
		    	//System.out.println(line);
		    	if (line.startsWith("product/productId:")){
			    	//System.out.println("2");
		    		String [] productIDLine = line.split(":");
		    		String productID = productIDLine[1].trim();
		    		if (!moviehm.containsKey(productID)){
		    			//THIS IS WHERE SCANNING INFO TAKES PLACE 
		    			double helpfulPct = 0;
		    			double rating = 0;
		    			String review = "";
		    			while(!line.startsWith("review/helpfulness:"))
		    				line = br.readLine();	//skip lines until we get to review/helpfulness line
		    			String [] helpfulnessLine = line.split(":");
			    		double helpfulNum = Double.parseDouble(helpfulnessLine[1].split("/")[0].trim());
			    		double helpfulDenom = Double.parseDouble(helpfulnessLine[1].split("/")[1].trim());
			    		if (helpfulDenom==0){
			    			helpfulPct =0.5;
			    		}
			    		else{
			    			helpfulPct = helpfulNum/helpfulDenom;
			    		}
			    		while(!line.startsWith("review/score:"))
		    				line = br.readLine();	//skip lines until we get to review/score line
			    		String [] ratingLine = line.split(":");
			    		rating = Double.parseDouble(ratingLine[1].trim());
			    		while(!line.startsWith("review/text:"))
			    			line = br.readLine();	//skip lines until we get to review/text line
			    		while(!line.isEmpty()){
			    			review+=line;
			    			line=br.readLine();
			    		}
			    		double [][] movieScores = {{0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};		// 0,1 = rating*helpfulness + review count, 2-11 = theme scores	
			    		movieScores [0][0] = 1;		// initialize review count to 1 
			    		movieScores [1][0] = rating*helpfulPct;			// total rating count
			    		movieScores [1][1] = rating*helpfulPct;		//avg rating % is equal to first rating
			    		int [] keyWordScores = KeyWordCounter.KeyWords(review);
			    		int j = 0;
			    		for (int i=2;i<12;i++){
			    			movieScores[i][0]=keyWordScores[j];
			    			j++;
			    		}
			    		moviehm.put(productID, movieScores);		//second arugment will be the scores array
		    			productCount++;
		    			// following code shows completion rate of the rating algorithm on the console
		    			double completion = round((productCount/253059)*100,2);
		    			if (completion != 0 && completion % 5 == 0){
		    				int milestone = (int) completion/5;
		    				if (loadMilestone[milestone]==0){
		    					System.out.println((int)completion+"% complete. "+(int)productCount+" products analyzed.");
		    					loadMilestone[milestone] =1;
		    				}
		    			}
		    		}	
		    		else{	//movie already in hashmap
		    			double helpfulPct = 0;
		    			double rating = 0;
		    			String review = "";
		    			while(!line.startsWith("review/helpfulness:"))
		    				line = br.readLine();	//skip lines until we get to review/helpfulness line
		    			String [] helpfulnessLine = line.split(":");
			    		double helpfulNum = Double.parseDouble(helpfulnessLine[1].split("/")[0].trim());
			    		double helpfulDenom = Double.parseDouble(helpfulnessLine[1].split("/")[1].trim());
			    		if (helpfulDenom==0){
			    			helpfulPct =0.5;
			    		}
			    		else{
			    			helpfulPct = helpfulNum/helpfulDenom;
			    		}
			    		while(!line.startsWith("review/score:"))
		    				line = br.readLine();	//skip lines until we get to review/score line
			    		String [] ratingLine = line.split(":");
			    		rating = Double.parseDouble(ratingLine[1].trim());
			    		while(!line.startsWith("review/text:"))
			    			line = br.readLine();	//skip lines until we get to review/text line
			    		while(!line.isEmpty()){
			    			review+=line;
			    			line=br.readLine();
			    		}
			    		double [][] movieScores = (double[][]) moviehm.get(productID);	// 0,1 = rating*helpfulness + review count, 2-11 = theme scores
			    		
			    		movieScores [0][0]+= 1;		// increment review count by 1 
			    		movieScores [1][0] += rating*helpfulPct;			// add to total rating count
			    		if (movieScores[0][0]>=minReviewCount)	//if theres more than 20 reviews the average rating can be counted | CAN MODIFY |
			    			movieScores [1][1] = movieScores[1][0]/movieScores[0][0];		
			    		else movieScores [1][1] = 0;		//avg rating is zero until 20 reviews are counted  
			    		int [] keyWordScores = KeyWordCounter.KeyWords(review);
			    		int j = 0;
			    		for (int i=2;i<12;i++){
			    			movieScores[i][0]+=keyWordScores[j];	//add the total scores to each
			    			if (movieScores[0][0]>=minReviewCount)		// if theres 20 reviews we can have an average 
			    				movieScores[i][1] = movieScores[i][0]/movieScores [0][0];	
			    			else movieScores[i][1] = 0;					//otherwise its just zero 
			    			j++;
			    		}
			    		moviehm.put(productID, movieScores);
		    		}
		    	}
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return moviehm;
	}
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}


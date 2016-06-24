import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HashMapSort {
	static HashMap sortByValues(HashMap<String, double[][]> map, int type) { 
	       List list = new LinkedList(map.entrySet());
	       // Comparator used with index of type in movie scores list
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	            	double y = (((Map.Entry<String, double[][]>) (o2)).getValue()[type][1])*(((Map.Entry<String, double[][]>) (o2)).getValue()[1][1]);
	            	double x = (((Map.Entry<String, double[][]>) (o1)).getValue()[type][1])*(((Map.Entry<String, double[][]>) (o1)).getValue()[1][1]);
	               return ((Comparable) (y)).compareTo(x);
	               // uses movie score for genre and average rating of movie
	            }
	       });

	       // copying sorted list in HashMap
	       // use a LinkedHashMap to preserve insertion order
	       HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	  }
}

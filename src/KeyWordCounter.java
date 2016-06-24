
public class KeyWordCounter {
	public static int [] KeyWords(String review){
		// im feeling some:
		// Action, Adventure/Epic, Comedy, Crime/Gangster, Drama, Epic/Historical, Horror, Musical/dance, Sci Fi, War, Western , TV SHOW as an option
		review = review.toUpperCase();
		int [] scores = new int [10];
		String [] actionWords = {"ACTION", "STUNT", "CHASE", "HERO", "VILLAIN", "FIGHT", "SHOOT", "BATTLE", "RESCUE", "ESCAPE"};
		String [] adventureWords = {"ADVENTURE", "EXCITING", "EPIC", "HISTOR", "MYTH", "LEGEND", "MEDIEVAL"};
		String [] comedyWords = {"COMEDY", "FUNNY", "LAUGH", "HILARIOUS", "GOOFY", "SLAPSTICK", "JOKE", "SILLY", "FUNNIEST", "PAROD"};
		String [] crimeWords = {"CRIME", "GANG", "MURDER", "CRIMINAL", "LAW", "FORENSIC", "MYSTERY", "KILLER", "DETECTIVE"};
		String [] documentaryWords = {"DOCUMENTARY", "BIOGRAPH", "HISTOR"};
		String [] dramaWords = {"DRAMA"};
		String [] horrorWords = {"HORROR", "SCARY", "THRILL", "TERRIFY", "CREEPY", "SHOCKING", "SUPERNATURAL", "ZOMBIES", "TERROR"};
		String [] musicalWords = {"MUSIC", "DANC", "SONG", "CHOREOGRAPHY", "CONCERT"};
		String [] sciFiWords = {"SCI-FI", "FANTASY", "ALIEN", "MONSTER", "SUPERHERO"}; 
		String [] TVshowWords = {"TV", "EPISODE", "SEASON", "FINALE", "SERIES"};
		
		for (String i: actionWords){
			int wordCount = count(review, i);
			scores[0] +=wordCount;
		}
		for (String i: adventureWords){
			int wordCount = count(review, i);
			scores[1] +=wordCount;
		}
		for (String i: comedyWords){
			int wordCount = count(review, i);
			scores[2] +=wordCount;
		}
		for (String i: crimeWords){
			int wordCount = count(review, i);
			scores[3] +=wordCount;
		}
		for (String i: documentaryWords){
			int wordCount = count(review, i);
			scores[4] +=wordCount;
		}
		for (String i: dramaWords){
			int wordCount = count(review, i);
			scores[5] +=wordCount;
		}
		for (String i: horrorWords){
			int wordCount = count(review, i);
			scores[6] +=wordCount;
		}
		for (String i: musicalWords){
			int wordCount = count(review, i);
			scores[7] +=wordCount;
		}
		for (String i: sciFiWords){
			int wordCount = count(review, i);
			scores[8] +=wordCount;
		}
		for (String i: TVshowWords){
			int wordCount = count(review, i);
			scores[9] +=wordCount;
		}
		
		return scores;

	}
	//counter function
	private static int count(final String string, final String substring)
	  {
	     int count = 0;
	     int idx = 0;

	     while ((idx = string.indexOf(substring, idx)) != -1)
	     {
	        idx++;
	        count++;
	     }

	     return count;
	  }
}

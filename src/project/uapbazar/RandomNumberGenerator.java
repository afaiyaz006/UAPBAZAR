package uapbazar;

import java.util.Random;
///Done by Abu Bakr
public class RandomNumberGenerator {
	public static  int randNum() {

	      
		 Random rand = new Random();
		 int n = rand.nextInt(1000000);
		 n += 1;
		 return n;

	 }
}

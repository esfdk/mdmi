package lab_3;

import java.util.ArrayList;
import java.util.List;

/**
 * Main class to run program from.
 * 
 * @author Anders Hartzen
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// First step - Load data and convert to Mushroom objects.
		ArrayList<Mushroom> mushrooms = DataLoader.LoadData();
		
		List<Mushroom> training = mushrooms.subList(0, 2000);
		List<Mushroom> test = mushrooms.subList(2000, 3000);
		
		for(Mushroom m : test)
		{
			System.out.println(Mushroom.isPoisonousKNN(m, training, 10));
		}
		
	}

}

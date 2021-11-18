package by.pokumeiko;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	private static Exchanger<Integer> exchanger = new Exchanger<>();
	
	public static void main(String args[]) {
		LOGGER.info("Start");
		
		ArrayList<Place> places = places();
		
		Parking parking = new Parking(places);
		
		ArrayList<Car> cars = cars(parking);
	
		cars.forEach(Thread::start);
		
	}
	
	/**Create cars*/
	public static ArrayList<Car> cars(Parking parking) {
		ArrayList<Car> cars = new ArrayList<Car>();
		IntStream.range(1, 13)
			.forEach(i -> cars.add(new Car (i, parking, (long) (Math.random() * 70), exchanger)));
		
		return cars;
	}

	/**Create places*/
	public static ArrayList<Place> places() {
		ArrayList<Place> places = new ArrayList<Place>();
		IntStream.range(1, 6).forEach(i -> places.add(new Place(i)));
		return places;
	}
	
}

package by.pokumeiko.tests;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import by.pokumeiko.Car;
import by.pokumeiko.Parking;
import by.pokumeiko.Place;

public class IsFreePlacesTest {
	
	private static Exchanger<Integer> exchanger = new Exchanger<>();
	
	@Test
	void test() {
			
		ArrayList<Place> places = new ArrayList<Place>();
		IntStream.range(1, 6).forEach(i -> places.add(new Place(i)));
		
		Parking parking = new Parking(places);
		
		ArrayList<Car> cars = new ArrayList<Car>();
		IntStream.range(1, 11)
			.forEach(i -> cars.add(new Car (i, parking, (long) (Math.random() * 70), exchanger)));
		
		cars.forEach(Thread::start);
		
		places.forEach(p ->  Assertions.assertEquals("true", Boolean.toString(p.isFree())));
	}
}

package by.pokumeiko.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Exchanger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import by.pokumeiko.*;

public class ReleaseAllTreadTest {
	private static Exchanger<Integer> exchanger = new Exchanger<>();
	
	@Test
	void test() {
		
		
		ArrayList<Place> places = new ArrayList<Place>();
		IntStream.range(1, 2).forEach(i -> places.add(new Place(i)));
		
		Parking parking = new Parking(places);
		
		ArrayList<Car> cars = new ArrayList<Car>();
		cars.add(new Car (0, parking, 100, exchanger));
		cars.add(new Car (1, parking, 1, exchanger));
		
		cars.get(0).start();
		
		long dateStart =  new Date().getTime();
		cars.get(1).start();
		while (new Date().getTime() - dateStart < 1) {
			 Assertions.assertEquals("false", Boolean.toString(cars.get(1).isInterrupted()));
			 continue;
		}
	}
}

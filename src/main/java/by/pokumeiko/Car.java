package by.pokumeiko;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Car extends Thread {
	private Integer carNo;
	private Integer placeNo = -1; 
	private long timeWaiting;
	private Parking parking;
	
	private Integer placeNoTemp;
		
	private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);
	private Exchanger<Integer> exchanger;

	
	public Car(Integer carNo, Parking parking, long timeWaiting, Exchanger<Integer> exchanger) {  
		this.exchanger = exchanger;
		this.timeWaiting = timeWaiting;
		this.carNo = carNo;
		this.parking = parking;
		parking.addCarToParking(this);
		setName("Car - " + carNo);
	}
	
	@Override
	public void run() {
		try {
			sleep((long) Math.random()*10);
			
			LOGGER.info("The car {} drove to parking with TimeWaiting = {} ", carNo, timeWaiting);
			
			placeNo = parking.getPlace(timeWaiting); 
			
			if (placeNo != -1) {
				LOGGER.info("The car {} parked on the place {}", carNo, placeNo);
				
				inParcking();
			
				leftParking(parking.getPlaceByNo(placeNo));
				
			} else {
				LOGGER.info("The car {} left the parking without parking", carNo);
				parking.leaveCarFromParking(this);
			}
		} catch (InterruptedException e) {
			interrupt();
			LOGGER.error("The car {} did not arrive. ", carNo, e);
			throw new RuntimeException();
		}
	}

	public void inParcking() {
		try {
			sleep((long) (Math.random()*100));
			
			placeNoTemp = exchanger.exchange(placeNo, 700, TimeUnit.MILLISECONDS);
			if ( (placeNoTemp - 1 == placeNo) || (placeNoTemp + 1 == placeNo) )  {
				placeNo = placeNoTemp;
				LOGGER.info("The car {} exchange place on {}.", carNo, placeNo);
			}
		
			sleep((long) (Math.random()*100));
		} catch (TimeoutException e) {
					
		} catch (InterruptedException e) {
				interrupt();
				LOGGER.error("Exchange error", e);
				e.printStackTrace();
		}
	}
	
	private void leftParking(Place place) {
		interrupt();
		parking.leaveCarFromParking(this);
		LOGGER.info("The car {} left the place {}", carNo, place.getPlaceNo());
		place.release();
	}
}

package by.pokumeiko;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Parking {
	ArrayList<Place> places;
	ArrayList<Car> cars = new ArrayList<Car>();
	
	private Semaphore semaphore;
	private ReentrantLock lock = new ReentrantLock();
	
	public Parking(ArrayList<Place> places) {
		this.places = places;
		semaphore = new Semaphore (places.size());
		places.forEach(p -> p.setSemaphore(semaphore));
	}
	
	public Integer getPlace(long timeWaiting) {
		if (!tryAcquire(timeWaiting)) {
			
			return -1;
	
		} else {
		
			lock.lock();
			Place freePlace = places.stream().filter(p -> p.isFree()).findFirst().get();
			freePlace.take();
			lock.unlock();
			
			return freePlace.getPlaceNo();
		}
	}
	
	
	private boolean tryAcquire(long TimeWaiting ) {
		try {
			return semaphore.tryAcquire(TimeWaiting, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public void addCarToParking(Car car) {
		try {
			lock.lock();
			cars.add(car);
		} finally {
			lock.unlock();
		}
	}
	
	public void leaveCarFromParking(Car car) {
		try {
			lock.lock();
			cars.remove(car);
		} finally {
			lock.unlock();
		}
	}
	
	public Place getPlaceByNo(int no) {
		 return places.get(no-1);
    }
	
}
	




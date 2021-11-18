package by.pokumeiko;

import java.util.concurrent.Semaphore;

public class Place {

	private Integer placeNo;
	private boolean free = true;
	private Semaphore semaphore;
	
	public Place(Integer placeNo) {
		this.placeNo = placeNo;
	}
	
	public Integer getPlaceNo() {
		return placeNo;
	}
	
	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	public boolean isFree() {
		return free;
	}
	
	public void take() {
		free = false;
	}

	public void release() {
		free = true;
		semaphore.release();
	}
	
}

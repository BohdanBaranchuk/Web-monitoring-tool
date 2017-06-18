package com.packt.analize_url.observ;

// create events for the Observer
public interface Subject {
	
	public void registerObserver(Observer o);
	
	public void removeObserver(Observer o);
	
	public void notifyObservers();
}

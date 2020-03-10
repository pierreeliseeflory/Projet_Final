package com.excilys.librarymanager.model;

public enum Subscription {
	BASIC (2),
	PREMIUM (5),
	VIP (20);
	
	private final int nbBooks;
	
	private Subscription (int n) {
		this.nbBooks = n;
	}

	public int getNbBooks() {
		return nbBooks;
	}
//	We can use .name() to get the name as String
//  and Subscription.valueOf('name') to get the enum instance corrseponding to the string
}

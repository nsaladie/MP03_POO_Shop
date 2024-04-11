package model;

public abstract class Person {
	protected String name;

	// Constructor for the class Client
	public Person(String name) {
		super();
		this.name = name;
	}

	// Constructor for the class Employee
	public Person() {
	}

	// To string that return the name of client with all the characters upper
	public String toString() {
		return name.toUpperCase();
	}

}

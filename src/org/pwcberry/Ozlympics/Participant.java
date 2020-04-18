package org.pwcberry.Ozlympics;

import java.text.DecimalFormat;

/**
 * This class contains the common attributes for Officials and Athletes, but
 * also enforcing subclasses to implement ID generation specific to that class.
 * 
 * The accessor methods for the instance variables are marked "final" as they do
 * not need to be overridden in a subclass.
 */
public abstract class Participant {

	// Maximum number of athletes and officials supported by the application.
	public static final int MAXIMUM_PARTICIPANTS = 200;

	// A quick reference to format numbers for IDs of subclasses
	protected static final DecimalFormat formatter = new DecimalFormat("000");

	private int age;
	private String name;
	private String state;
	private String id;

	/**
	 * Subclass constructors will call this constructor.
	 */
	protected Participant(String name, int age, String state) {
		this.name = name;
		this.age = age;
		this.state = state;

		// Generate an ID based on the subclass' definition of the method.
		this.id = this.generateId();
	}

	/**
	 * Subclasses define their own method for generating IDs.
	 * 
	 * @return the new ID.
	 */
	protected abstract String generateId();

	/**
	 * @return the age
	 */
	public final int getAge() {
		return age;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the state
	 */
	public final String getState() {
		return state;
	}

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + ": " + name + ", " + age + ", " + state;
	}
}

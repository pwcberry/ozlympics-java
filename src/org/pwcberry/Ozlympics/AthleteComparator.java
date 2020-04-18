package org.pwcberry.Ozlympics;

import java.util.Comparator;

/**
 * This class allows Athletes to be sorted by their scores in descending order.
 */
public class AthleteComparator implements Comparator<Athlete> {

	/**
	 * Compare Athletes so by default the sort is descending.
	 */
	public int compare(Athlete a1, Athlete a2) {
		// For ascending sort, a1 > a2 returns a positive integer.
		return (a1.getScore() > a2.getScore()) ? -1 : 
			(a1.getScore() < a2.getScore()) ? 1 : 0;
	}

	/**
	 * Two Athletes are equivalent when their scores are equal.
	 */
	public boolean equals(Athlete a1, Athlete a2) {
		if ((a1 == null) || (a2 == null)) {
			return false;
		}

		return a1.getScore() == a2.getScore();
	}
}

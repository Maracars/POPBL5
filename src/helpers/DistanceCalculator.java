package helpers;

import domain.model.Node;

// TODO: Auto-generated Javadoc
/**
 * The Class DistanceCalculator.
 * Calculates the distance of a segment of the airports roads
 */
public class DistanceCalculator {

	
	/**
	 * Calculate distance.
	 *
	 * @param src the src
	 * @param dst the dst
	 * @return the double
	 */
	public static double calculateDistance(Node src, Node dst) {

		return pitagor(src.getPositionX() - dst.getPositionX(), src.getPositionY() - dst.getPositionY());

	}
	
	/**
	 * Pitagor.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the double
	 */
	private static double pitagor(double x, double y) {
		return Math.sqrt((x * x) + (y * y));
	}
}

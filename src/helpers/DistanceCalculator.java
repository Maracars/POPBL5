package helpers;

import domain.model.Node;

public class DistanceCalculator {

	
	public static double calculateDistance(Node src, Node dst) {

		return pitagor(src.getPositionX() - dst.getPositionX(), src.getPositionY() - dst.getPositionY());

	}
	
	private static double pitagor(double x, double y) {
		return Math.sqrt((x * x) + (y * y));
	}
}

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.geometry.Point2D;

class Atan2 {

	@Test
	void vector() {
		assertEquals(270, angle(new Point2D(5, 5), new Point2D(0, 5)));

		assertEquals(90, angle(new Point2D(0, 0), new Point2D(5, 0)));
	}

	static double angle(Point2D p0, Point2D p1) {

		double difX = p0.getX() - p1.getX();
		double difY = p0.getY() - p1.getY();

		double alpha = -toDegrees(atan2(difX, difY));

		return (alpha + 360) % 360;
	}
}

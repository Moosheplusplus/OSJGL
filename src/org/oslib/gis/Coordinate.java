package org.oslib.gis;

/**
 * Map coordinate (latitude, longitude, elevation)
 *
 */
public class Coordinate {

	private double latitude, longitude, elevation;

	private static final double EARTH_RADIUS = 6378137D;

	public Coordinate(double latitude, double longitude) {
		this(latitude, longitude, 0);
	}

	public Coordinate(double latitude, double longitude, double elevation) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
	}

	public Coordinate(Coordinate coord) {
		this.latitude = coord.latitude();
		this.longitude = coord.longitude();
		this.elevation = coord.elevation();
	}

	/**
	 * The latitude of this coordinate.
	 *
	 * @return The latitude.
	 */
	public double latitude() {
		return latitude;
	}

	/**
	 * The longitude of this coordinate.
	 *
	 * @return The longitude.
	 */
	public double longitude() {
		return longitude;
	}

	/**
	 * The elevation of this coordinate.
	 *
	 * @return The elevation.
	 */
	public double elevation() {
		return elevation;
	}

	/**
	 * Sets the longitude of this coordinate
	 *
	 * @param longitude The coordinate's longitude
	 * @return This instance for purpose of chaining
	 */
	public Coordinate setLongitude(double longitude) {
		this.longitude = longitude;
		return this;
	}

	/**
	 * Sets the latitude of this coordinate
	 *
	 * @param latitude The coordinate's latitude
	 * @return This instance for purpose of chaining
	 */
	public Coordinate setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	/**
	 * Sets the elevation of this coordinate
	 *
	 * @param elevation The coordinate's elevation
	 * @return This instance for purpose of chaining
	 */
	public Coordinate setElevation(double elevation) {
		this.elevation = elevation;
		return this;
	}

	/**
	 * Returns haversine distance between two coordinates.
	 *
	 * @param coordinate The other locatable to compare distance between
	 * @return The haversine distance in Kilometers.
	 */
	public double distanceTo(Coordinate coordinate) {
		double dLat = Math.toRadians(coordinate.latitude - latitude);
		double dLon = Math.toRadians(coordinate.longitude - longitude);
		double aLat = Math.toRadians(latitude);
		double bLat = Math.toRadians(coordinate.latitude);
		double a = Math.pow(Math.sin(dLat/2), 2) + (Math.pow(Math.sin(dLon/2), 2)
				* Math.cos(aLat) * Math.cos(bLat));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return 6378.137 * c;
	}

	/**
	 * Calculates the angle (in degrees) it is between two coordinates.
	 *
	 * @param loc The other location
	 * @return the angle in degrees
	 */
	public double bearing(Coordinate loc) {
		double latA = Math.toRadians(latitude());
		double lngA = Math.toRadians(longitude());
		double latB = Math.toRadians(loc.latitude());
		double lngB = Math.toRadians(loc.longitude());

		double angle = -Math.atan2(Math.sin(lngA - lngB) * Math.cos(latB),
				Math.cos(latA) * Math.sin(latB) - Math.sin(latA)
						* Math.cos(latB) * Math.cos(lngA - lngB));

		if(angle < 0D)
			angle += Math.PI * 2D;
		if(angle > Math.PI)
			angle -= Math.PI * 2D;

		return Math.toDegrees(angle);
	}

	/**
	 * Derives a coordinate with the specified distance between two coordinates.
	 *
	 * @param loc The location to derive towards
	 * @param distance The distance in meters
	 * @return the derivative
	 */
	public Coordinate derive(Coordinate loc, double distance) {
		double bearing = Math.toRadians(bearing(loc));

		double latA = Math.toRadians(latitude);
		double lngA = Math.toRadians(longitude);

		double latB = Math.asin(Math.sin(latA) *
				Math.cos(distance / EARTH_RADIUS) + Math.cos(latA) * Math.sin(distance / EARTH_RADIUS)
				* Math.cos(bearing));

		double lngB = lngA + Math.atan2(Math.sin(bearing)
						* Math.sin(distance / EARTH_RADIUS) * Math.cos(latA),
				Math.cos(distance / EARTH_RADIUS)
						- Math.sin(latA) * Math.sin(latB));

		return new Coordinate(Math.toDegrees(latB), Math.toDegrees(lngB));
	}

	@Override
	public String toString() {
		return String.format("[%s, %s, %s]", latitude(), longitude(), elevation());
	}

	@Override
	public boolean equals(Object coord) {
		return coord.hashCode() == hashCode();
	}

	@Override
	public int hashCode() {
		long a = (long) Math.floor(latitude() * 100000000000L);
		long b = (long) Math.floor(longitude() * 100000000000L);
		return (int) (31 * (a ^ (a >>> 32)) + (b ^ (b >>> 32)));
	}
}

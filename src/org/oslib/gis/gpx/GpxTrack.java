package org.oslib.gis.gpx;

import java.util.ArrayList;

/**
 * A GpxTrack is an array of Segments which can represent both
 * a route and a track.
 *
 */
public class GpxTrack extends ArrayList<GpxSegment> {

	private String name;

	public GpxTrack() {
		this("Unnamed GpxTrack");
	}

	public GpxTrack(String name) {
		this.name = name;
	}

	public GpxTrack setName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	/**
	 * Calculates the total distance in meters the GpxTrack is.
	 *
	 * @return The total distance in meters.
	 */
	public double length() {
		double dist = 0;
		for (GpxSegment trackPoints : this)
			dist += trackPoints.length();
		return dist;
	}
}

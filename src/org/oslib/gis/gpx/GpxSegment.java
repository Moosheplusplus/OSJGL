package org.oslib.gis.gpx;

import java.util.ArrayList;

/**
 * A GpxSegment containing a {@link GpxWayPoint} array.
 *
 */
public class GpxSegment extends ArrayList<GpxWayPoint> {

	private final GpxTrack track;

	public GpxSegment(GpxTrack track) {
		this.track = track;
	}

	public GpxTrack parent() {
		return track;
	}

	/**
	 * Calculates the total distance in meters the segment is.
	 *
	 * @return The distance in meters
	 */
	public double distance() {
		if(size() < 1)
			return 0;

		double dist = 0;
		GpxWayPoint last = get(0);
		for(int i = 1; i < size(); i++) {
			dist += last.distanceTo(get(i));
			last = get(i);
		}
		return dist * 1000;
	}
}

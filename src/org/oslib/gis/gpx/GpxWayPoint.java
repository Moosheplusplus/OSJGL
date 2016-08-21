package org.oslib.gis.gpx;

import org.oslib.gis.Coordinate;
import org.oslib.gis.util.TimeUtil;

import java.util.Date;

/**
 * A GpxWayPoint is a point which could represent both a RoutePoint and a TrackPoint.
 *
 */
public class GpxWayPoint extends Coordinate {

	private Date time;

	public GpxWayPoint(Coordinate coord) {
		this(coord, new Date());
	}

	public GpxWayPoint(Coordinate coord, String time) {
		this(coord, TimeUtil.gpxTime(time));
	}

	public GpxWayPoint(Coordinate coord, Date time) {
		super(coord);
		this.time = time;
	}

	public Date getTime() {
		return time;
	}

	public GpxWayPoint setTime(Date time) {
		this.time = time;
		return this;
	}
}

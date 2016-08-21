package org.oslib.gis.gpx;

import java.util.ArrayList;

/**
 * @author Mooshe
 */
public class Gpx extends ArrayList<GpxTrack> {

	private final GpxMetaData data = new GpxMetaData();

	public GpxMetaData getMetaData() {
		return data;
	}
}

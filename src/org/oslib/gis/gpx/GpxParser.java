package org.oslib.gis.gpx;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.oslib.gis.Coordinate;
import org.oslib.gis.util.TimeUtil;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class GpxParser {

	public static Gpx parse(File file) throws IOException {
		try {
			GpxHandler handler = new GpxHandler();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, handler);
			return handler.gpx;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	private static class GpxHandler extends DefaultHandler {

		private Gpx gpx = new Gpx();

		private String path = "";

		private StringBuffer content = new StringBuffer();

		public void startElement(String uri, String local, String name, Attributes attr) throws SAXException {
			name = name.toUpperCase();
			if(this.path.isEmpty())
				this.path = name;
			else
				this.path += "."+name;

			if(name.equals("RTE")) {
				GpxTrack track = new GpxTrack();
				track.add(new GpxSegment(track));
				gpx.add(track);
			} else if(name.equals("TRK")) {
				gpx.add(new GpxTrack());
			} else if(name.equals("TRKSEG")) {
				GpxTrack track = gpx.get(gpx.size() - 1);
				track.add(new GpxSegment(track));
			} else if(name.equals("TRKPT") || name.equals("RTEPT")) {
				double lat = Double.parseDouble(attr.getValue("lat"));
				double lon = Double.parseDouble(attr.getValue("lon"));
				GpxTrack track = gpx.get(gpx.size() - 1);
				GpxSegment seg = track.get(track.size() - 1);
				seg.add(new GpxWayPoint(new Coordinate(lat, lon)));
			} else if(path.equals("GPX.METADATA.BOUNDS")) {
				double d1 = Double.parseDouble(attr.getValue("minlat"));
				double d2 = Double.parseDouble(attr.getValue("minlon"));
				double d3 = Double.parseDouble(attr.getValue("maxlat"));
				double d4 = Double.parseDouble(attr.getValue("maxlon"));
				gpx.getMetaData().setBounds(new Rectangle.Double(d1, d2, d3, d4));
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			content.append(ch, start, length);
		}

		@Override
		public void endElement(String uri, String local, String name) throws SAXException {
			name = name.toUpperCase();
			String text = new String(content);

			if(path.startsWith("GPX.METADATA."))
				handleMeta(name, text);
			else if(path.startsWith("GPX.RTE.") || path.startsWith("GPX.TRK."))
				handleRoute(name, text, path.startsWith("GPX.RTE."));

			content = new StringBuffer();
			if(path.contains("."))
				path = path.substring(0, path.lastIndexOf("."));
			else
				path = "";
		}

		private void handleRoute(String name, String content, boolean route) throws SAXException {
			GpxTrack track = gpx.get(gpx.size() - 1);
			GpxSegment seg = track.get(track.size() - 1);
			if(!path.startsWith(route ? "GPX.RTE.RTEPT" : "GPX.TRK.TRKPT") && name.equals("NAME"))
				gpx.get(gpx.size() - 1).setName(content);
			else if(name.equals("ELE")) {
				seg.get(seg.size() - 1).setElevation(Double.parseDouble(content));
			} else if(name.equals("TIME")) {
				seg.get(seg.size() - 1).setTime(TimeUtil.gpxTime(content));
			}
		}

		private void handleMeta(String name, String content) throws SAXException {
			if(name.equals("NAME"))
				gpx.getMetaData().setName(content);
			else if(name.equals("DESC"))
				gpx.getMetaData().setDescription(content);
			else if(name.equals("AUTHOR"))
				gpx.getMetaData().setAuthor(content);
			else if(name.equals("COPYRIGHT"))
				gpx.getMetaData().setCopyright(content);
			else if(name.equals("LINK"))
				gpx.getMetaData().setLink(content);
			else if(name.equals("TIME"))
				gpx.getMetaData().setCreated(TimeUtil.gpxTime(content));
			else if(name.equals("KEYWORDS"))
				gpx.getMetaData().setKeywords(content);
		}
	}
}

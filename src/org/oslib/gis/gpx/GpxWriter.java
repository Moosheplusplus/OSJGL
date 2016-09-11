package org.oslib.gis.gpx;

import org.oslib.gis.util.TimeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;

/**
 * @author Mooshe
 */
public class GpxWriter {

	private final Gpx gpx;
	private final Document document;

	public GpxWriter(Gpx gpx) {
		this.gpx = gpx;
		this.document = get();
	}

	public boolean write(OutputStream out) {
		if(document == null)
			return false;

		try {
			Transformer form = TransformerFactory.newInstance().newTransformer();
			StreamResult result = new StreamResult(out);

			form.transform(new DOMSource(document), result);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private Document get() {
		Document build = null;
		try {
			build = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
			Element root = build.createElement("gpx");

			root.setAttribute("creator", "https://github.com/moosheplusplus/OSJGL/");
			root.setAttribute("version", "1.1");

			appendMetaData(build, root);

			Element rte = build.createElement("rte");
			Element name = build.createElement("name");
			name.appendChild(build.createTextNode(gpx.get(0).getName()));
			appendSegment(build, rte);
			root.appendChild(rte);

			build.appendChild(root);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return build;
	}

	private void appendMetaData(Document build, Element root) {
		GpxMetaData data = gpx.getMetaData();

		Element meta = build.createElement("metadata");

		Element metatime = build.createElement("time");
		metatime.appendChild(build.createTextNode(TimeUtil.gpxTime(data.getCreated())));
		meta.appendChild(metatime);

		Element bounds = build.createElement("bounds");
		bounds.setAttribute("minlat", ""+data.getBounds().getX());
		bounds.setAttribute("minlon", ""+data.getBounds().getY());
		bounds.setAttribute("maxlat", ""+(data.getBounds().getX() + data.getBounds().getWidth()));
		bounds.setAttribute("maxlon", ""+(data.getBounds().getY() + data.getBounds().getHeight()));
		meta.appendChild(bounds);

		Element author = build.createElement("author");
		author.appendChild(build.createTextNode(data.getAuthor()));
		meta.appendChild(author);

		Element name = build.createElement("name");
		name.appendChild(build.createTextNode(data.getName()));
		meta.appendChild(name);

		Element desc = build.createElement("desc");
		desc.appendChild(build.createTextNode(data.getDescription()));
		meta.appendChild(desc);

		Element copy = build.createElement("copyright");
		copy.appendChild(build.createTextNode(data.getCopyright()));
		meta.appendChild(copy);

		Element link = build.createElement("link");
		copy.appendChild(build.createTextNode(data.getLink()));
		meta.appendChild(link);

		Element keys = build.createElement("keywords");
		keys.appendChild(build.createTextNode(data.getKeyWords()));
		meta.appendChild(keys);

		root.appendChild(meta);
	}

	private void appendSegment(Document build, Element e) {
		int c = 0;
		for(GpxSegment seg : gpx.get(0)) {
			for(GpxWayPoint p : seg) {
				Element rtept = build.createElement("rtept");
				rtept.setAttribute("lat", ""+p.latitude());
				rtept.setAttribute("lon", ""+p.longitude());

				Element ele = build.createElement("ele");
				ele.appendChild(build.createTextNode(""+p.elevation()));
				rtept.appendChild(ele);

				Element time = build.createElement("time");
				time.appendChild(build.createTextNode(TimeUtil.gpxTime(p.getTime())));
				rtept.appendChild(time);

				Element name = build.createElement("name");
				name.appendChild(build.createTextNode("pt"+(c++)));
				rtept.appendChild(name);

				e.appendChild(rtept);
			}
		}
	}
}

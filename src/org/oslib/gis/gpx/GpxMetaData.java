package org.oslib.gis.gpx;

import java.awt.*;
import java.util.Date;

/**
 * @author Mooshe
 */
public class GpxMetaData {

	private String name = "Unnamed";
	private String description = "No Description Available.";
	private String author = "Unknown Author";
	private String copyright = "No Copyright Available";
	private String link = "";
	private String keyWords = "";

	private Date created = new Date();
	private Rectangle.Double bounds = new Rectangle.Double(0.0, 0.0, 0.0, 0.0);

	/**
	 * Sets the bounds of the GPX file (<i>Optional</i>).
	 *
	 * @param bounds The rectangular bounds of the GPX file
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setBounds(Rectangle.Double bounds) {
		this.bounds = bounds;
		return this;
	}

	/**
	 * Sets the date the GPX file was created at (<i>Optional</i>).
	 *
	 * @param date The date the file was created
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setCreated(Date date) {
		this.created = date;
		return this;
	}

	/**
	 * Sets the Name of the GPX file (<i>Optional</i>).
	 *
	 * @param name The name of the GPX file
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Sets the Author of the GPX file (<i>Optional</i>).
	 *
	 * @param author The author of the GPX file
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setAuthor(String author) {
		this.author = author;
		return this;
	}

	/**
	 * Sets the Description of the GPX file (<i>Optional</i>).
	 *
	 * @param description The description of the GPX file
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Sets the Copyright of the GPX file (<i>Optional</i>).
	 *
	 * @param copyright The copyright of the GPX file
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setCopyright(String copyright) {
		this.copyright = copyright;
		return this;
	}

	/**
	 * Sets the Link of the GPX file (<i>Optional</i>).
	 *
	 * @param link The link of the GPX file
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setLink(String link) {
		this.link = link;
		return this;
	}

	/**
	 * Sets the keywords of the GPX file (<i>Optional</i>).
	 *
	 * @param keywords The keywords of the GPX file
	 * @return This instance for the purpose of chaining.
	 */
	public GpxMetaData setKeywords(String keywords) {
		this.keyWords = keywords;
		return this;
	}

	/**
	 * Grabs the rectangular bounds of the GPX file, or [0D, 0D, 0D, 0D] if
	 * bounds were never set (<i>Optional</i>).
	 *
	 * @return Rectangular bounds
	 */
	public Rectangle.Double getBounds() {
		return bounds;
	}

	/**
	 * Grabs the time the GPX file was created, or a new {@link Date} instance
	 * if the date was never set (<i>Optional</i>).
	 *
	 * @return The {@link Date} of when the file was created.
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * Grabs the description of the GPX file (<i>Optional</i>).
	 *
	 * @return The description represented as a {@link String}
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Grabs the name of the GPX file (<i>Optional</i>).
	 *
	 * @return The name represented as a {@link String}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Grabs the link of the GPX file (<i>Optional</i>).
	 *
	 * @return A URL represented in a {@link String}
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Grabs the Author of the GPX file (<i>Optional</i>).
	 *
	 * @return The Author of the GPX file
	 */
	public String getAuthor() {
		return author;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getKeyWords() {
		return keyWords;
	}
}

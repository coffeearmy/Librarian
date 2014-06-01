package com.coffeearmy.librarian.data;

import java.util.Comparator;
import java.util.Date;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.RESTUtility;

/** Class used to store all the ePub fields */
public class EPubData {

	private static final int THUMBSIZE_HEIGHT = 100;
	private static final int THUMBSIZE_WIDTH = 60;
	public static final String EMPTY_STRING = "NOTYET";

	String title;
	String fileName;
	String path;
	Date date;
	Bitmap cover;

	public EPubData() {
	}

	/** Create a ePubData from Dropbox entry object */
	public EPubData(Entry entry) {
		this.fileName = entry.fileName();
		this.path = entry.path;
		this.title = EMPTY_STRING;
		this.date = dateToString(entry.clientMtime);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the cover
	 */
	public Bitmap getCover() {
		return cover;
	}

	/**
	 * @param cover
	 *            the cover to set
	 */
	public void setCover(Bitmap cover) {
		this.cover = cover;
	}

	/** Return if the item have ePub metadata */
	public boolean isEPubMetadataLoaded() {
		return cover == null ? false : true;
	}

	/** Returns a thumbnail of the cover */
	public Bitmap getThumbnail() {
		return ThumbnailUtils.extractThumbnail(cover, THUMBSIZE_WIDTH,
				THUMBSIZE_HEIGHT);
	}

	/**
	 * Parse Dropbox date format to java.util.date, if can not be done use today
	 * date
	 */
	private Date dateToString(String d) {
		// Dropbox format : "Sat, 21 Aug 2010 22:31:20 +0000"
		Date dateParse = null;
		
		try {
			dateParse=RESTUtility.parseDate(d);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (dateParse == null)
				dateParse = new Date();
		}
		return dateParse;
	}

	/**
	 * Title comparator in ascending order, if title is not available use
	 * filename
	 */
	public static Comparator<EPubData> EPubDataNameComparator = new Comparator<EPubData>() {

		public int compare(EPubData ePub1, EPubData ePub2) {

			String ePubName1;
			String ePubName2;
			if (ePub1.getTitle().equals(EMPTY_STRING)
					|| ePub2.getTitle().equals(EMPTY_STRING)) {
				ePubName1 = ePub1.getFileName().toUpperCase();
				ePubName2 = ePub2.getFileName().toUpperCase();

			} else {
				ePubName1 = ePub1.getTitle().toUpperCase();
				ePubName2 = ePub2.getTitle().toUpperCase();
			}
			// ascending order
			return ePubName1.compareTo(ePubName2);

			// descending order
			// return ePubName2.compareTo(ePubName1);
		}

	};

	/** Date comparator */
	public static Comparator<EPubData> EPubDataDateComparator = new Comparator<EPubData>() {

		public int compare(EPubData ePubData1, EPubData ePubData2) {

			Date ePubDate1 = ePubData1.getDate();
			Date ePubDate2 = ePubData2.getDate();

			// ascending order
			return ePubDate1.compareTo(ePubDate2);

			// descending order
			// return ePubDate2.compareTo(ePubDate1);
		}

	};
}

package com.coffeearmy.librarian.data;

import java.util.Calendar;

import com.dropbox.client2.DropboxAPI.Entry;

import android.graphics.Bitmap;
import nl.siegmann.epublib.domain.Date;

/** Class used to store all the ePub fields */
public class EPubData {
	String title;
	String fileName;
	String path;
	Date date;
	Bitmap cover;
	public EPubData() {
	}
	/** Create a ePubData from Dropbox entry object*/
	public EPubData(Entry entry) {
		this.fileName=entry.fileName();
		this.path=entry.path;
		this.title="NOTYET";
		
		//this.date=entry.clientMtime();
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
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
	 * @param fileName the fileName to set
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
	 * @param path the path to set
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
	 * @param date the date to set
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
	 * @param cover the cover to set
	 */
	public void setCover(Bitmap cover) {
		this.cover = cover;
	}
	
	public boolean isEPubMetadataLoaded() {
		return cover==null?false:true;
	}
}

package com.example.android40.model;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * @author Alphan Yang
 * @author Thomas Pan
 * this class defines the Photo object
 */
public class Photo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7198885586861667817L;
	private String name;
	private String user;
	private String caption;
	private Calendar cal;
	private Date date;
	private File pic;
	private HashMap<String, Tag> tagList;
	private ArrayList<Tag> sortedTags;
	private ArrayList<String> albums;

	/**
	 * Photo object constructor
	 * 
	 * @param name      name of photo
	 * @param user      user that photo is associated with
	 * @param caption   photo caption
	 * @param albumName name of album that photo is stored in
	 * @param pic       file location of picture
	 */
	public Photo(String name, String user, String caption, String albumName, File pic) {
		this.name = name;
		this.user = user;
		this.caption = caption;
		albums = new ArrayList<String>();
		albums.add(albumName);
		cal = new GregorianCalendar();
		cal.set(Calendar.MILLISECOND, 0);
		this.date = cal.getTime();
		this.pic = pic;
		this.tagList = new HashMap<String, Tag>();
	}

	/**
	 * photo name setter
	 * 
	 * @param name name of photo
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * photo name getter
	 * 
	 * @return returns name of photo
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * setter for user associated with photo
	 * 
	 * @param user name of user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * getter for user associated with photo
	 * 
	 * @return returns name of user
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * setter for photo caption
	 * 
	 * @param caption string caption for photo
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * getter for photo caption
	 * 
	 * @return returns caption of photo
	 */
	public String getCaption() {
		return this.caption;
	}

	/**
	 * function to add album to arraylist of albums
	 * 
	 * @param album name of album to be added
	 */
	public void addAlbum(String album) {
		this.albums.add(album);
	}

	/**
	 * function to remove album from arraylist of albums
	 * 
	 * @param album album to be removed
	 */
	public void removeAlbum(String album) {
		this.albums.remove(album);
	}

	/**
	 * setter function for albums arraylist
	 * 
	 * @param albums name of albums arraylist
	 */
	public void setAlbums(ArrayList<String> albums) {
		this.albums = albums;
	}

	/**
	 * getter for albums arraylist
	 * 
	 * @return returns the albums arraylist
	 */
	public ArrayList<String> getAlbums() {
		return this.albums;
	}

	/**
	 * getter for date of photo
	 * 
	 * @return returns date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * setter for picture being grabbed from the file explorer
	 * 
	 * @param pic name of file
	 */
	public void setPic(File pic) {
		this.pic = pic;
	}

	/**
	 * getter for picture being grabbed from the file explorer
	 * 
	 * @return returns picture file
	 */
	public File getPic() {
		return this.pic;
	}

	/**
	 * function used to add tag to list of tags
	 * 
	 * @param t name of tag
	 */
	public void addTag(Tag t) {
		tagList.put(t.getDesc(), t);
	}

	/**
	 * function to remove tag from list of tags
	 * 
	 * @param value represents name of the tag
	 */
	public void removeTag(String value) {
		if (tagList.containsKey(value))
			tagList.remove(value);
	}

	/**
	 * function to get list of tags from hashmap
	 * 
	 * @return returns list of tags
	 */
	public HashMap<String, Tag> getTagList() {
		return tagList;
	}

	/**
	 * setter for tag list
	 * 
	 * @param tagList name of tag list to be set
	 */
	public void setTagList(HashMap<String, Tag> tagList) {
		this.tagList = tagList;
	}

	/**
	 * getter for date string in MM/dd/yyy-HH:mm:ss format
	 * 
	 * @return returns date string
	 */
	public String getDateString() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		return df.format(this.date);
	}

	/**
	 * setter for date string in MM/dd/yyy-HH:mm:ss format
	 * 
	 * @param date name of date to be passed into setter
	 */
	public void setStringDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		try {
			Date d = (Date) df.parse(date);
			this.date = d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * setter for sorted tags
	 * 
	 * @param t name of tag arraylist
	 */
	public void setSortedTags(ArrayList<Tag> t) {
		this.sortedTags = t;
	}

	/**
	 * getter got sorted tags arraylist
	 * 
	 * @return returns sorted tags arraylist
	 */
	public ArrayList<Tag> getSortedTags() {
		return this.sortedTags;
	}
}

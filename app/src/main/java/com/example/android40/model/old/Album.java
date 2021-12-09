package com.example.android40.model.old;

import com.example.android40.model.Photo;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author Thomas Pan
 * @author Alphan Yang
 * 
 *         This class is used to define the Album object
 */
public class Album implements Serializable {

	private static final long serialVersionUID = -6624706288939535911L;

	private String name;
	private String username;
	private String userId;
	private HashMap<String, Photo> photos;

	/**
	 * 
	 * @param name     name of album
	 * @param username username of album
	 * @param userId   userId of album
	 */
	public Album(String name, String username, String userId) {
		this.name = name;
		this.username = username;
		this.userId = userId;
		photos = new HashMap<String, Photo>();
	}

	/**
	 * setter method to set name
	 * 
	 * @param name name to set album to
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return returns name of album
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * setter method for User
	 * 
	 * @param username string username
	 * @param userId   string userId
	 */
	public void setUser(String username, String userId) {
		this.username = username;
		this.userId = userId;
	}

	/**
	 * 
	 * @return returns username of album
	 */
	public String getUserName() {
		return this.username;
	}

	/**
	 * 
	 * @return returns userId of album
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 
	 * @return returns list of photos from hashmap
	 */
	public HashMap<String, Photo> getPhotoList() {
		return photos;
	}

	/**
	 * 
	 * @param photos name of hashmap
	 */
	public void setPhotoList(HashMap<String, Photo> photos) {
		this.photos = photos;
	}

	/**
	 * 
	 * @param p photo to be added to list of photos
	 */
	public void addPhoto(Photo p) {
		photos.put(p.getName(), p);
	}

	/**
	 * 
	 * @param name name of photo to be deleted
	 */
	public void deletePhoto(String name) {
		if (photos.containsKey(name))
			photos.remove(name);
	}

	@Override
	public String toString() {
		return userId + ": " + name + ", " + username;
	}

}

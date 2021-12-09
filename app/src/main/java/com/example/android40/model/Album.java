package com.example.android40.model;

import java.io.Serializable;
import java.util.ArrayList;
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
	private ArrayList<Photo> photos;
	/**
	 * 
	 * @param name     name of album
	 */
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
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
	 * 
	 * @return returns list of photos from hashmap
	 */
	public ArrayList<Photo> getPhotoList() {
		return photos;
	}

	/**
	 *
	 * @param photos name of hashmap
	 */
	public void setPhotoList(ArrayList<Photo> photos) {
		this.photos = photos;
	}

	/**
	 * 
	 * @param p photo to be added to list of photos
	 */
	public void addPhoto(Photo p) {
		photos.add(p);
	}

	/**
	 * 
	 * @param name name of photo to be deleted
	 */
	public void deletePhoto(Photo p) {
	    photos.remove(p);
	}

	@Override
	public String toString() {
		return name + ", " + photos;
	}

}

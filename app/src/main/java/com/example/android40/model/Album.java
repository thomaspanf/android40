package com.example.android40.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Thomas Pan
 * @author Alphan Yang
 * 
 *         This class is used to define the Album object
 */
public class Album implements Serializable, Parcelable{

	private static final long serialVersionUID = -6624706288939535911L;

	private String name;
	//private HashMap<String, Photo> photos;
	private ArrayList<Photo> photos;
	/**
	 * 
	 * @param name     name of album
	 */
	public Album(String name) {
		this.name = name;
//		photos = new HashMap<String, Photo>();
//		sortedPhotos = new ArrayList<>(photos.values());
		photos = new ArrayList<Photo>();
	}

	protected Album(Parcel in) {
		name = in.readString();
	}

	public static final Creator<Album> CREATOR = new Creator<Album>() {
		@Override
		public Album createFromParcel(Parcel in) {
			return new Album(in);
		}

		@Override
		public Album[] newArray(int size) {
			return new Album[size];
		}
	};

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
	 * @param p name of photo to be deleted
	 */
	public void deletePhoto(Photo p) {
	    photos.remove(p);
	}

	@Override
	public String toString() {
		return name + ", " + photos;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
	}
}

package com.example.android40.model;

import com.example.android40.model.Album;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Alphan Yang
 * this class defines the user object
 */
public class User implements Serializable {

	private static final long serialVersionUID = -6648080630166964258L;

	private String userID;
	private String name;
	private HashMap<String, Album> albumList;

	/**
	 * user constructor
	 * 
	 * @param id   string user id
	 * @param name string user name
	 */
	public User(String id, String name) {
		this.userID = id;
		this.name = name;
		albumList = new HashMap<String, Album>();
	}

	/**
	 * setter for user id
	 * 
	 * @param id string name for id
	 */
	public void setID(String id) {
		this.userID = id;
	}

	/**
	 * setter for full name of user
	 * 
	 * @param name string full user name
	 */
	public void setFullName(String name) {
		this.name = name;
	}

	/**
	 * getter for userId
	 * 
	 * @return returns string for userID
	 */
	public String getID() {
		return this.userID;
	}

	/*
	 * getter for name
	 * 
	 * @return returns string for name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getter for album list stored in hashmap
	 * 
	 * @return returns hashmap containing list of albums
	 */
	public HashMap<String, Album> getAlbumList() {
		return this.albumList;
	}

	/**
	 * function used to add an album into the albumList hashmap
	 * 
	 * @param album name of album object to be stored in albumList hashmap
	 */
	public void addAlbum(Album album) {
		albumList.put(album.getName(), album);
	}

	/**
	 * function used to delete an album from the albumList hashmap
	 * 
	 * @param name name of album object to be deleted from albumList hashmap
	 */
	public void deleteAlbum(String name) {
		if (albumList.containsKey(name))
			albumList.remove(name);
	}

	/**
	 * function used to rename an album object
	 * 
	 * @param oldName string for old album name
	 * @param newName string for new album name
	 */
	public void renameAlbum(String oldName, String newName) {
		if (albumList.containsKey(oldName))
			albumList.get(oldName).setName(newName);
	}

}

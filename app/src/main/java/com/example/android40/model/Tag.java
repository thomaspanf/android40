package com.example.android40.model;

import java.io.Serializable;

/**
 * @author Alphan Yang
 *
 *         This class defines the Tag object.
 */
public class Tag implements Serializable {

	private static final long serialVersionUID = -7283309166724188511L;
	private String tagType;
	private String description;

	/**
	 * tag constructor
	 * 
	 * @param type        type of tag
	 * @param description description of tag
	 */
	public Tag(String type, String description) {
		this.tagType = type;
		this.description = description;
	}

	/**
	 * getter for tag type
	 * 
	 * @return returns tag type
	 */
	public String getType() {
		return tagType;
	}

	/**
	 * getter for tag description
	 * 
	 * @return returns tag description
	 */
	public String getDesc() {
		return description;
	}

	/**
	 * setter for tag type
	 * 
	 * @param t string of type to be passed in
	 */
	public void setType(String t) {
		this.tagType = t;
	}

	/**
	 * setter for tag description
	 * 
	 * @param s string of description to be passed in
	 */
	public void setDesc(String s) {
		this.description = s;
	}
}

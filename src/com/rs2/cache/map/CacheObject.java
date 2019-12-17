package com.rs2.cache.map;

import com.rs2.model.Position;

/**
 * Represents a single game object.
 * 
 * @author Graham Edgecombe
 * 
 */
public class CacheObject {

	/**
	 * The location.
	 */
	private Position location;

	/**
	 * The definition.
	 */
	private int id;

	/**
	 * The type.
	 */
	private int type = 0;

	/**
	 * The rotation.
	 */
	private int rotation = 0;

	/**
	 * Creates the game object.
	 * 
	 * @param definition
	 *            The definition.
	 * @param location
	 *            The location.
	 * @param type
	 *            The type.
	 * @param rotation
	 *            The rotation.
	 */
	public CacheObject(int id, Position location, int type, int rotation) {
		this.id = id;
		this.location = location;
		this.type = type;
		this.rotation = rotation;
	}

	/**
	 * Gets the location.
	 * 
	 * @return The location.
	 */
	public Position getLocation() {
		return location;
	}

	/**
	 * Gets the definition.
	 * 
	 * @return The definition.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the type.
	 * 
	 * @return The type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the rotation.
	 * 
	 * @return The rotation.
	 */
	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public void setLocation(Position location) {
		this.location = location;
	}

}

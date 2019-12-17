package com.rs2.cache.map;

import com.rs2.model.Position;

/**
 * Represents a single game object.
 * 
 * @author Graham Edgecombe
 * 
 */
public class CacheTile {

	/**
	 * The location.
	 */
	private Position location;

	/**
	 * The definition.
	 */
	private int id;

	private boolean overlay = false;

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
	public CacheTile(int id, Position location) {
		this.id = id;
		this.location = location;
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
	public boolean isOverlay() {
		return overlay;
	}

}

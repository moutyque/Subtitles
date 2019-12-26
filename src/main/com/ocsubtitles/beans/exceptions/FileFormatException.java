package com.ocsubtitles.beans.exceptions;

public class FileFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
	public FileFormatException() {
	}

	// Constructor that accepts a message
	public FileFormatException(String msg) {
		super(msg);
	}
}
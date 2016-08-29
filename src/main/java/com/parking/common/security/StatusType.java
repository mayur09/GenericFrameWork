package com.parking.common.security;

public enum StatusType {
	DISABLED, ENABLED;

	private int value;

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
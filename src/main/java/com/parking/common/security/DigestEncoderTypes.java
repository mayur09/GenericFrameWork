package com.parking.common.security;

public enum DigestEncoderTypes {
	MD5, SHA256;

	private int value;

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
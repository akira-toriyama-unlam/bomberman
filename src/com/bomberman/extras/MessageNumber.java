package com.bomberman.extras;

public class MessageNumber {
	int x, y;
	String messageNumber;
	
	public MessageNumber(String messageNumber, int x, int y) {
		this.messageNumber = messageNumber;
		this.x = x;
		this.y = y;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(String messageNumber) {
		this.messageNumber = messageNumber;
	}
	
	
}

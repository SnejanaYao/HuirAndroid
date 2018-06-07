package com.huir.android.entity;

public class UserShow {
	private int id;
	private int nickChatID;
	private String chatUserName;
	private String image;
	private String message;
	private String date;
	
	public UserShow(int id, int nickChatID, String chatUserName, String image, String message, String date) {
		super();
		this.id = id;
		this.nickChatID = nickChatID;
		this.chatUserName = chatUserName;
		this.image = image;
		this.message = message;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChatUserID() {
		return nickChatID;
	}
	public void setChatUserID(int chatUserID) {
		this.nickChatID = chatUserID;
	}
	public String getChatUserName() {
		return chatUserName;
	}
	public void setChatUserName(String chatUserName) {
		this.chatUserName = chatUserName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}

package com.huir.android.entity;

public class Msg {
   private String msg;
   private int  type;
   
   private String id; 
   private String path; //文件地址
   private int second; //秒数
   private boolean isPlayed;//是否已经播放过
   private boolean isPlaying;//是否正在播放
   
   
   
    /**
     *
     * 语音发送的构造方法
     * @param msg
     * @param type
     * @param id
     * @param path
     * @param second
     * @param isPlayed
     * @param isPlaying
     */
	public Msg(String msg, int type, String id, String path, int second, boolean isPlayed, boolean isPlaying) {
		super();
		this.msg = msg;
		this.type = type;
		this.id = id;
		this.path = path;
		this.second = second;
		this.isPlayed = isPlayed;
		this.isPlaying = isPlaying;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public boolean isPlayed() {
		return isPlayed;
	}

	public void setPlayed(boolean isPlayed) {
		this.isPlayed = isPlayed;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Msg() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 消息发送的构造方法
	 * @param msg
	 * @param type
	 */
	public Msg(String msg, int type) {
		this.msg = msg;
		this.type = type;
	}

}

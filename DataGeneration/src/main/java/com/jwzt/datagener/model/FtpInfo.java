package com.jwzt.datagener.model;

public class FtpInfo {
	private String ip;
	private int port;
	private String username;
	private String pwd;
	private String path;
	private String ispassivemode;
	private int timeout;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIspassivemode() {
		return ispassivemode;
	}
	public void setIspassivemode(String ispassivemode) {
		this.ispassivemode = ispassivemode;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
}

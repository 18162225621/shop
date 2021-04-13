package com.jwzt.datagener.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hdrNewThreadInterval;
	private String apiUrl;
	private String apiPageSize;
	private int maxCount;
	private String customerAuthenticator;
	private String wmTokenUrl;
	private String tokenUrl;
	private String localMpdPath;
	private String isSSL;
	private String localManifestPath;
	private String localMainfestDoMain;
	private String tokenActiveTime;
	private String apiIsHttps;
	private int wmTokenThreadInterval;
	private int copywmTokenThreadInteral;
	private int tokenEhcacheThreadInteral;
	private boolean isMainProject;
	private String appContextPath;
	private List<FtpInfo> ftps = new ArrayList<FtpInfo>();
	private boolean isFtpUpload;
	private int manifestDeleterThreadInteral;
	private String deleterManifestFile;
	
	
	public boolean getIsFtpUpload() {
		return isFtpUpload;
	}

	public void setIsFtpUpload(boolean isFtpUpload) {
		this.isFtpUpload = isFtpUpload;
	}

	public int getHdrNewThreadInterval() {
		return hdrNewThreadInterval;
	}

	public void setHdrNewThreadInterval(int hdrNewThreadInterval) {
		this.hdrNewThreadInterval = hdrNewThreadInterval;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getApiPageSize() {
		return apiPageSize;
	}

	public void setApiPageSize(String apiPageSize) {
		this.apiPageSize = apiPageSize;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public String getCustomerAuthenticator() {
		return customerAuthenticator;
	}

	public void setCustomerAuthenticator(String customerAuthenticator) {
		this.customerAuthenticator = customerAuthenticator;
	}

	public String getWmTokenUrl() {
		return wmTokenUrl;
	}

	public void setWmTokenUrl(String wmTokenUrl) {
		this.wmTokenUrl = wmTokenUrl;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getLocalMpdPath() {
		return localMpdPath;
	}

	public void setLocalMpdPath(String localMpdPath) {
		this.localMpdPath = localMpdPath;
	}

	public String getIsSSL() {
		return isSSL;
	}

	public void setIsSSL(String isSSL) {
		this.isSSL = isSSL;
	}

	public String getLocalManifestPath() {
		return localManifestPath;
	}

	public void setLocalManifestPath(String localManifestPath) {
		this.localManifestPath = localManifestPath;
	}

	public String getLocalMainfestDoMain() {
		return localMainfestDoMain;
	}

	public void setLocalMainfestDoMain(String localMainfestDoMain) {
		this.localMainfestDoMain = localMainfestDoMain;
	}

	public String getTokenActiveTime() {
		return tokenActiveTime;
	}

	public void setTokenActiveTime(String tokenActiveTime) {
		this.tokenActiveTime = tokenActiveTime;
	}

	public String getApiIsHttps() {
		return apiIsHttps;
	}

	public void setApiIsHttps(String apiIsHttps) {
		this.apiIsHttps = apiIsHttps;
	}

	public int getWmTokenThreadInterval() {
		return wmTokenThreadInterval;
	}

	public void setWmTokenThreadInterval(int wmTokenThreadInterval) {
		this.wmTokenThreadInterval = wmTokenThreadInterval;
	}

	public int getCopywmTokenThreadInteral() {
		return copywmTokenThreadInteral;
	}

	public void setCopywmTokenThreadInteral(int copywmTokenThreadInteral) {
		this.copywmTokenThreadInteral = copywmTokenThreadInteral;
	}

	public int getTokenEhcacheThreadInteral() {
		return tokenEhcacheThreadInteral;
	}

	public void setTokenEhcacheThreadInteral(int tokenEhcacheThreadInteral) {
		this.tokenEhcacheThreadInteral = tokenEhcacheThreadInteral;
	}

	public boolean getIsMainProject() {
		return isMainProject;
	}

	public void setIsMainProject(boolean isMainProject) {
		this.isMainProject = isMainProject;
	}
	
	
	public void setMainProject(boolean isMainProject) {
		this.isMainProject = isMainProject;
	}

	public String getAppContextPath() {
		return appContextPath;
	}

	public void setAppContextPath(String appContextPath) {
		
		this.appContextPath = appContextPath;
	}

	public List<FtpInfo> getFtps() {
		return ftps;
	}

	public void setFtps(List<FtpInfo> ftps) {
		this.ftps = ftps;
	}

	public int getManifestDeleterThreadInteral() {
		return manifestDeleterThreadInteral;
	}

	public void setManifestDeleterThreadInteral(int manifestDeleterThreadInteral) {
		this.manifestDeleterThreadInteral = manifestDeleterThreadInteral;
	}

	public String getDeleterManifestFile() {
		return deleterManifestFile;
	}

	public void setDeleterManifestFile(String deleterManifestFile) {
		this.deleterManifestFile = deleterManifestFile;
	}
	
	
}

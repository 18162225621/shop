package com.jwzt.datagener.helper;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.jwzt.datagener.model.FtpInfo;



public class FTPClientUtil {

	private final static Logger logger = Logger.getLogger(FTPClientUtil.class);

	private FTPClient ftpclient;
	
	private FtpInfo ftpinfo;

	public FTPClientUtil(FtpInfo ftpinfo) {
		
		this.ftpinfo = ftpinfo;
		
		ftpclient = new FTPClient();
		try {
			ftpclient.setConnectTimeout(ftpinfo.getTimeout()*1000);//连接超时时间
			ftpclient.connect(ftpinfo.getIp(),ftpinfo.getPort());
			if ("true".equals(ftpinfo.getIspassivemode())) {
				ftpclient.enterLocalPassiveMode();
			} else {
				ftpclient.enterLocalActiveMode();
			}
			if (ftpinfo.getUsername() != null) {
				if (!ftpclient.login(ftpinfo.getUsername(), ftpinfo.getPwd())) {
					ftpclient.disconnect();
					logger.error("登陆服务器" + ftpinfo + "验证失败，请检查你的ftp账号和密码是否正确 ！");
				}
			}
		} catch (IOException e) {
			logger.error("登陆服务器" + ftpinfo + "验证失败,发生IO错误:",e);
		}
	}

	/**
	 * 文件上传
	 * @param remotePath
	 * @param localPath
	 * @return
	 */
	public boolean uploadFile(String remotePath, String localPath) {
		boolean returnValue = false;
		File remotefile = new File(remotePath);
		if(remotefile.exists()){
			logger.info("文件【"+remotePath+"】已经存在，不做文件上传处理");
			return returnValue;
		}
		File localfile = new File(localPath);
		FileInputStream input = null;
		try {
			input = new FileInputStream(localfile);
			ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
			//设置FTP超时时间###############################
			ftpclient.setDataTimeout(ftpinfo.getTimeout()*1000);//传输超时时间
			if (remotePath.indexOf("/") != -1) {
				String path = remotePath.substring(0, remotePath.lastIndexOf("/"));
				mkdir(path);
			}
			returnValue = ftpclient.storeFile(new String(remotePath.getBytes(), ftpclient.getControlEncoding()), input);
			ftpclient.changeWorkingDirectory("/");
			if (!returnValue) {
				logger.info("上传文件"+ localfile+ "到"+ ftpinfo.getIp()+ remotePath+ "时发生未知错误!上传失败！");
			} else {
				logger.info("上传文件" + localfile + "到" + ftpinfo.getIp() + "成功!远程路径：" + remotePath);
			}
			
			//判断ftp文件是否存在
			if(returnValue && !exist(remotePath)){
				logger.info("文件【"+remotePath+"】上传失败");
				returnValue = false;
			}
			
		} catch (IOException e) {
			logger.error("上传文件" + localPath + "时出错!",e);
		}finally{
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
			if(ftpclient != null){
				close();
			}
		}
		return returnValue;
	}
	

	

	/***
	 * 创建一级或多级目录结构
	 * 
	 * @param pathname
	 *            /flod1/fload2
	 * @return
	 * @throws IOException
	 */
	private boolean mkdir(String pathname) throws IOException {

		boolean makesuccess = false;
		pathname = pathname.replace("\\", "/").replaceAll("^\\/|\\/$", "");
		if ("".equals(pathname)) {
			return true;
		}
		String[] path = pathname.split("/");
		String predir = "";
		for (int i = 0; i < path.length; i++) {
			String thisLevel = new String(path[i].getBytes(), ftpclient
					.getControlEncoding());
			ftpclient.makeDirectory(thisLevel);
			predir += "/" + thisLevel;
			makesuccess = ftpclient.changeWorkingDirectory(predir);
		}
		return makesuccess;
	}

	/***
	 * 退出登录并关闭ftp连接
	 */
	public void close() {
		try {
			if (ftpclient != null) {
				ftpclient.logout();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (ftpclient != null) {
				ftpclient.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				// ignored
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * 判断远程文件是否存在
	 * @param remotePath
	 * @return
	 */
	public boolean exist(String remotePath){
		
		boolean fileExist = false;
		try {
			FTPFile[] files = ftpclient.listFiles(remotePath);
			fileExist = files.length == 1;

		} catch (IOException e) {
			e.printStackTrace();
			logger.error("判断ftp上是否存在文件【"+remotePath+"】出现异常"+ e.toString());
		}
		return fileExist;
		
	}
	
	public static void main(String[] args) {
		FtpInfo ftpInof = new FtpInfo();
		ftpInof.setIp("127.0.0.1");
		ftpInof.setPort(21);
		ftpInof.setUsername("admin");
		ftpInof.setPwd("admin");
		FTPClientUtil ftpClientUtil = new FTPClientUtil(ftpInof);
		String remotePath  = "/localMpd/20171128/3252028_9110138_9110112_1511852836953.mpd";
		System.out.println(ftpClientUtil.exist(remotePath));
	}
}

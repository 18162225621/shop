package com.jwzt.datagener.helper;
import javax.net.ssl.*;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class DisableSSLCertificateCheckUtil {
	 private static final Logger LOGGER = Logger.getLogger(DisableSSLCertificateCheckUtil.class);

	    /**
	     * Prevent instantiation of utility class.
	     */

	    private DisableSSLCertificateCheckUtil() {

	    }

	    /**
	     * Disable trust checks for SSL connections.
	     */

	    public static void disableChecks() {
	        try {
	            new URL("https://qin-api-cs.wasu.tv/").getContent();
	        } catch (IOException e) {
	            // This invocation will always fail, but it will register the
	            // default SSL provider to the URL class.
	        	LOGGER.error("调用地址出现异常"+ e);
	        }
	        try {
	            SSLContext sslc;
	            sslc = SSLContext.getInstance("TLS");
	            TrustManager[] trustManagerArray = {new X509TrustManager() {
	                @Override
	                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	                }

	                @Override
	                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

	                }

	                @Override
	                public X509Certificate[] getAcceptedIssuers() {
	                    return new X509Certificate[0];
	                }
	            }};
	            sslc.init(null, trustManagerArray, null);
	            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
	            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	                @Override
	                public boolean verify(String s, SSLSession sslSession) {
	                    return true;
	                }
	            });
	        } catch (Exception e) {
	            LOGGER.error("error msg:{}", e);
	            throw new IllegalArgumentException("证书校验异常！");
	        }
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisableSSLCertificateCheckUtil.disableChecks();
	}

}

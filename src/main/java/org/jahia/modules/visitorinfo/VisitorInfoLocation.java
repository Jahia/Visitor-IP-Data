package org.jahia.modules.visitorinfo;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Visitor location methods
 */
public class VisitorInfoLocation {
	private static final String precisionCountry = "ip-country";
	private static final String precisionCity = "ip-city";
	private static final String precisionGps = "ip-gps";

	private static List<String[]> locations = null;

	private static final Logger logger = LoggerFactory
			.getLogger(VisitorInfoLocation.class);

	public static String country(String ip, HttpServletRequest request) {
		String connected = ResourceBundle.getBundle("technical").getString(
		"ip2location_mod");
		if (connected != null && connected.equals("online")) {
			return online(precisionCountry, ip);
		}
		return offline(precisionCountry, ip, request);
	}

	public static String city(String ip, HttpServletRequest request) {
		String connected = ResourceBundle.getBundle("technical").getString(
				"ip2location_mod");
		if (connected != null && connected.equals("online")) {
			return online(precisionCity, ip);
		}
		return offline(precisionCity, ip, request);
	}

	public static String gps(String ip, HttpServletRequest request) {
		String connected = ResourceBundle.getBundle("technical").getString(
				"ip2location_mod");
		if (connected != null && connected.equals("online")) {
			return online(precisionGps, ip);
		}
		return offline(precisionGps, ip, request);
	}

	/**
	 * Get location information by web services
	 * http://ipinfodb.com/ip_location_api.php
	 */
	private static String online(String precision, String ip) {
		String result = "";
		// bouchon de test 
		ip = "81.80.239.162";
		String token_url = ResourceBundle.getBundle("technical").getString("online_db")
				+ (precisionGps.equals(precision) || precisionCity.equals(precision) ? precisionCity
						: precisionCountry)
				+ "/?"
				+ "key="
				+ ResourceBundle.getBundle("technical").getString(
						"ip2location_key") + "&ip=" + ip;
		try {
			URL ip2location = new URL(token_url);
			result = readURL(ip2location);
		} catch (MalformedURLException exp) {
			logger.error(exp.getMessage());
		} catch (IOException exp) {
			logger.error(exp.getMessage());
		}

		if (result != null) {
			String[] results = result.split(";");
			if (results.length > 0 && "OK".equals(results[0])) {
				if (precisionCountry.equals(precision)) {
					result = result.split(";")[4];
				} else if (precisionCity.equals(precision)) {
					result = result.split(";")[6];
				} else if (precisionGps.equals(precision)) {
					result = result.split(";")[8] + "," + result.split(";")[9];
				} else {
					result = "precision error";
				}
			}
		}

		return result;
	}

	/**
	 * Call the web service by REST interface
	 */
	private static String readURL(URL url) throws IOException {
		String result;
		
		HttpURLConnection uc;
		String proxyActivation = ResourceBundle.getBundle("technical").getString("proxy_activation");
		if (proxyActivation != null && proxyActivation.equals("true")) {
			// Proxy
			String 	proxyUrl = ResourceBundle.getBundle("technical").getString("proxy_url");
			int 	proxyPort = new Integer(ResourceBundle.getBundle("technical").getString("proxy_port"));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort));
			uc = (HttpURLConnection)url.openConnection(proxy);
			// Authentification
			String 	proxyLogin = ResourceBundle.getBundle("technical").getString("proxy_login");
			String 	proxyPass = ResourceBundle.getBundle("technical").getString("proxy_pass");
			if (proxyLogin != null && proxyLogin.length() > 0) {
				String encoded = new String(Base64.encodeBase64(new String(proxyLogin+":"+proxyPass).getBytes()));
				uc.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
			}
		} else {
			uc = (HttpURLConnection)url.openConnection();
		}
		uc.connect();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = uc.getInputStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		
		result = new String(baos.toByteArray());
		logger.debug("online result " + result);
		return result;
	}

	/**
	 * Get location information by csv database
	 * http://ipinfodb.com/ip_database.php
	 */
	private static String offline(String precision, String ip,
			HttpServletRequest request) {
		String result;
		
		// Bouchon de test
		ip = "81.80.239.162";
		String[] results;
		
		String[] ipsplit = ip.split("\\.");
		long a = Integer.valueOf(ipsplit[0]);
		long b = Integer.valueOf(ipsplit[1]);
		long c = Integer.valueOf(ipsplit[2]);
		long d = Integer.valueOf(ipsplit[3]);
		final long ipkey = ((a * 256 + b) * 256 + c) * 256 + d;

		results = (String[]) CollectionUtils.find(getLocations(request), new Predicate() {
			public boolean evaluate(Object arg0) {
				String[] 	line 		= (String[]) arg0;
				long 		ipkeymin	= Long.parseLong(line[0]);
				long 		ipkeymax	= Long.parseLong(line[1]);
				
				return ipkeymin < ipkey &&  ipkey <= ipkeymax;
			}
		});
		
		if (precisionCountry.equals(precision)) {
			result = results[results.length - 1];
		} else if (precisionCity.equals(precision)) {
			result = "Not in Database";
		} else if (precisionGps.equals(precision)) {
			result = "Not in Database";
		} else {
			result = "precision error";
		}
		logger.debug("offline result " + result);
		return result;
	}

	private static synchronized List<String[]> getLocations(HttpServletRequest request) {
		FileReader file = null;
		CSVReader reader= null;
		try {
			if (locations == null) {
				String csvPath = ResourceBundle.getBundle("technical").getString("offline_db");
				file = new FileReader(csvPath);
				reader = new CSVReader(file);
				locations = reader.readAll();
				file.close();
				reader.close();
				logger.debug("initialisation locations " + locations.size());
			}
		} catch (FileNotFoundException exp) {
			logger.error(exp.getMessage());
		} catch (IOException exp) {
			logger.error(exp.getMessage());
		} finally {
			try {
				if (file != null) {
					file.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException exp) {
				logger.error(exp.getMessage());
			}
		}
		return locations;
	} 
}

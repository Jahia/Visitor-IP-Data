package org.jahia.modules.visitorinfo;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.jahia.services.usermanager.JahiaUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Facade to access visitor info methods
 */
public class VisitorInfoFacade {
	private static final Logger logger = LoggerFactory
			.getLogger(VisitorInfoFacade.class);

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String header(JahiaUser user, HttpServletRequest request,
			String key) {
		String value = request.getHeader(key);
		logger.debug("header " + value);
		return (value == null ? key : value);
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String remoteHost(JahiaUser user, HttpServletRequest request) {
		logger.debug("remoteHost " + request.getRemoteHost());
		return request.getRemoteHost();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String remoteAddr(JahiaUser user, HttpServletRequest request) {
		logger.debug("remoteAddr " + request.getRemoteAddr());
		return request.getRemoteAddr();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String remotePort(JahiaUser user, HttpServletRequest request) {
		logger.debug("remotePort " + request.getRemotePort());
		return "" + request.getRemotePort();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String remoteUser(JahiaUser user, HttpServletRequest request) {
		logger.debug("remoteUser " + request.getRemoteUser());
		return "" + request.getRemoteUser();
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String xForwardFor(JahiaUser user, HttpServletRequest request) {
		String xForwardFor = request.getHeader("X-Forwarded-For");
		if (xForwardFor == null) {
			xForwardFor = remoteAddr(user, request);
		} else {
			int commaOffset = -1;
			if ((commaOffset = xForwardFor.indexOf(",")) >= 0) {
				xForwardFor = xForwardFor.substring(0, commaOffset);
			}
		}
		logger.debug("xForwardFor " + xForwardFor);
		return xForwardFor;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String userPrincipal(JahiaUser user,
			HttpServletRequest request) {
		String userName = user.getName();
		if (userName == null) {
			Principal principal = request.getUserPrincipal();
			if (principal != null) {
				userName = principal.getName();
			}
		}
		logger.debug("userPrincipal " + userName);
		return userName;
	}

	/**
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public static String pathInfo(JahiaUser user, HttpServletRequest request) {
		String pathInfo = request.getPathInfo();
		logger.debug("pathInfo " + pathInfo);
		return pathInfo;
	}

	/**
	 * @see org.jahia.modules.visitorinfo.VisitorInfoLocation
	 */
	public static String country(JahiaUser user, HttpServletRequest request) {
		String ip 		= xForwardFor(user, request);
		String country	= VisitorInfoLocation.country(ip, request);
		logger.debug("country " + country);
		return country;
	}

	/**
	 * @see org.jahia.modules.visitorinfo.VisitorInfoLocation
	 */
	public static String city(JahiaUser user, HttpServletRequest request) {
		String ip 	= xForwardFor(user, request);
		String city	= VisitorInfoLocation.city(ip, request);
		logger.debug("city " + city);
		return city;
	}

	/**
	 * @see org.jahia.modules.visitorinfo.VisitorInfoLocation
	 */
	public static String gps(JahiaUser user, HttpServletRequest request) {
		String ip 	= xForwardFor(user, request);
		String gps	= VisitorInfoLocation.gps(ip, request);
		logger.debug("gps " + gps);
		return gps;
	}
}

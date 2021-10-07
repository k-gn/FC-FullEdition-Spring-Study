package com.example.practice.util;

// 공통 응답 script
public class Script {

	public static String back(String msg) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + msg + "');");
		sb.append("history.back();");
		sb.append("</script>");
		
		return sb.toString();
	}

	public static String confirm(String msg) {

		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + msg + "');");
		sb.append("location.href='/login'");
		sb.append("</script>");

		return sb.toString();
	}
}

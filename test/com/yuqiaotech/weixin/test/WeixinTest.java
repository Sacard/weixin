package com.yuqiaotech.weixin.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class WeixinTest {

	private static String corpId = "wx36e795a9cfda1ca8";
	private static String corpSecret = "E-1E-5pDj-oz6TtwJbwTLqJxjpLQDaT8XEYJX74YyNfEaFs8xrQGFg8J6i4tkuUZ";
	private static int agentId = 2;

	public static void main(String[] args) {
		createDepartment();
	}

	// TODO 保存access_token如果过期再重新获取
	public static String getAccess_token() { // 获得ACCESS_TOKEN

		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="
				+ corpId + "&corpsecret=" + corpSecret;

		String accessToken = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet
					.openConnection();

			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");

			JSONObject demoJson = new JSONObject(message);
			accessToken = demoJson.getString("access_token");

			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	public static void createMenu() {
		String user_define_menu = "{\"button\":"
				+ "[{\"name\":\"优惠\","
				+ "\"sub_button\":["
				+ "{\"type\":\"click\",\"name\":\"最新折扣\",\"key\":\"m_discount\"},"
				+ "{\"type\":\"click\",\"name\":\"优惠卡查询\",\"key\":\"m_query\"}]},"
				+ "{\"name\":\"店内业务\","
				+ "\"sub_button\":["
				+ "{\"type\":\"click\",\"name\":\"发型师\",\"key\":\"h_stylist\"},"
				+ "{\"type\":\"click\",\"name\":\"发型列表\",\"key\":\"h_list\"},"
				+ "{\"type\":\"click\",\"name\":\"发型介绍\",\"key\":\"h_introduce\"}]},"
				+ "{\"name\":\"更多\","
				+ "\"sub_button\":["
				+ "{\"type\":\"click\",\"name\":\"护法小知识\",\"key\":\"m_knowledge\"},"
				+ "{\"type\":\"click\",\"name\":\"公告信息\",\"key\":\"m_imgmsg\"},"
				+ "{\"type\":\"view\",\"name\":\"搜一下\",\"url\":\"http://www.baidu.com\"}]}]}";
		// 此处改为自己想要的结构体，替换即可
		String access_token = getAccess_token();

		String action = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ access_token + "&agentid=" + agentId;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(user_define_menu.getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			System.out.println(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void createDepartment() {
		String user_define_department = "{"//
				+ "\"name\":\"测试部门111\","//
				+ "\"parentid\":\"5\","//
				+ "\"order\":\"1\","//
				+ "}";

		String access_token = getAccess_token();

		String action = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="
				+ access_token;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(user_define_department.getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");

			System.out.println(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void createMember() {
		String user_define_department = "{"//
				+ "\"userid\":\"ceshichengyuan3\","//
				+ "\"name\":\"测试成员3\","//
				+ "\"department\":5,"//
				+ "\"position\":\"测试职位3\","//
				+ "\"mobile\":\"1234567893\","//
				+ "}";

		String access_token = getAccess_token();

		String action = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="
				+ access_token;
		try {
			URL url = new URL(action);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();
			OutputStream os = http.getOutputStream();
			os.write(user_define_department.getBytes("UTF-8"));// 传入参数
			os.flush();
			os.close();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");

			System.out.println(message);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

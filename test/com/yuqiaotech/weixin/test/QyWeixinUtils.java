package com.yuqiaotech.weixin.test;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 微信企业号工具类
 * @author 孙浩
 *
 */
public class QyWeixinUtils {

	private static String corpId = "wx36e795a9cfda1ca8";//企业号id
	private static String corpSecret = "E-1E-5pDj-oz6TtwJbwTLqJxjpLQDaT8XEYJX74YyNfEaFs8xrQGFg8J6i4tkuUZ";
	private static int agentId = 2;//企业号应用id
	// 存储最后一次获取accessToken的时间，因为一个token可用2个小时，频繁调用token会被禁
	private static String key = corpId + "_" + corpSecret;
	private static Map<String, Long> access_tokenTimeMapping = new HashMap();
	private static Map<String, String> access_tokenMapping = new HashMap();
	
	/**
	 * 获取access_token
	 * @return access_token
	 */
	public static String getAccess_token() {
		long nowTime = System.currentTimeMillis();
		// 调试的时候，获取一次token，然后把值放在这里。
		if (false)
			return "ZDytzkUFZH89TgjrHPxecEBFLQZzVF0qToPtxqxJtMaMvmXEJMbASeF-mt95HCfs";
		Long lastAccessTokenTime = access_tokenTimeMapping.get(key);
		if (lastAccessTokenTime == null)
			lastAccessTokenTime = 0l;
		if (nowTime - lastAccessTokenTime < 7000 * 1000) {
			String accessToken = access_tokenMapping.get(key);
			if (accessToken != null)
				return accessToken;
		}
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="
				+ corpId + "&corpsecret=" + corpSecret;
		//从返回信息中获取access_token
		String access_token = "";
		JSONObject demoJson;
		try {
			demoJson = new JSONObject(weixinGetRequest(url));
			access_token = demoJson.getString("access_token");
			access_tokenTimeMapping.put(key, nowTime);
			access_tokenMapping.put(key, access_token);
		} catch (JSONException e) {

			e.printStackTrace();
		} finally {
			return access_token;
		}

	}
	/**
	 * 创建菜单
	 */
	public static void createMenu() {
		String data = "{\"button\":"
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
		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ access_token + "&agentid=" + agentId;
		weixinPostRequest(url, data);
	}
	/**
	 * 创建部门
	 */
	public static void createDepartment() {
		String data = "{"//
				+ "\"name\":\"测试部门1111\","//
				+ "\"parentid\":\"6\","//
				+ "\"order\":\"1\","//
				// + "\"id\":\"6\","//
				+ "}";

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="
				+ access_token;
		weixinPostRequest(url, data);

	}
	
	/**
	 * 删除部门
	 * @param id 部门id
	 */
	public static void deleteDepartment(int id) {

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token="
				+ access_token + "&id=" + id;
		weixinGetRequest(url);

	}
	/**
	 * 更新部门
	 */
	public static void updateDepartment() {
		String data = "{"//
				+ "\"id\":4,"//
				+ "\"name\":\"测试部门1update\","//
				// + "\"parentid\":\"4\","//
				// + "\"order\":\"1\","//
				+ "}";

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="
				+ access_token;
		weixinPostRequest(url, data);

	}
	/**
	 * 获取指定部门及其下的子部门
	 * @param id 部门id
	 */
	public static void listDepartments(int id) {

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="
				+ access_token + "&id=" + id;
		weixinGetRequest(url);

	}
	/**
	 * 创建用户
	 */
	public static void createUser() {
		String data = "{"//
				+ "\"userid\":\"ceshichengyuan4\","//
				+ "\"name\":\"测试成员4\","//
				+ "\"department\":6,"//
				+ "\"position\":\"测试职位4\","//
				+ "\"mobile\":\"1234567894\","//
				+ "}";

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="
				+ access_token;
		weixinPostRequest(url, data);
	}
	/**
	 * 删除用户
	 * @param userId 用户账号
	 */
	public static void deleteUser(String userId) {

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="
				+ access_token + "&userid=" + userId;
		weixinGetRequest(url);
	}
	/**
	 * 更新用户
	 */
	public static void updateUser() {
		String data = "{"//
				+ "\"userid\":\"ceshichengyuan3\","//
				+ "\"name\":\"测试成员3update\","//
				+ "\"department\":6,"//
				// + "\"position\":\"测试职位4\","//
				// + "\"mobile\":\"1234567894\","//
				+ "}";

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="
				+ access_token;
		weixinPostRequest(url, data);
	}
	/**
	 * 获取用户信息
	 * @param userId 用户账号
	 */
	public static void getUser(String userId) {

		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="
				+ access_token + "&userid=" + userId;
		weixinGetRequest(url);
	}
	/**
	 * 获取部门成员
	 * @param department_id 部门id
	 * @param fetch_child 1/0：是否递归获取子部门下面的成员
	 * @param status 0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加
	 */
	public static void simpleListUsers(int department_id, int fetch_child, int status) {
		//TODO status可叠加
		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token="
				+ access_token + "&department_id=" + department_id + "&fetch_child=" + fetch_child + "&status=" + status;
		weixinGetRequest(url);
	}
	/**
	 * 获取部门成员详细信息
	 * @param department_id 部门id
	 * @param fetch_child 1/0：是否递归获取子部门下面的成员
	 * @param status 0获取全部成员，1获取已关注成员列表，2获取禁用成员列表，4获取未关注成员列表。status可叠加
	 */
	public static void listUsers(int department_id, int fetch_child, int status) {
		//TODO status可叠加
		String access_token = getAccess_token();

		String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token="
				+ access_token + "&department_id=" + department_id + "&fetch_child=" + fetch_child + "&status=" + status;
		weixinGetRequest(url);
	}
	
	
	/**
	 * 以POST方式向微信发出请求
	 * @param url 请求地址
	 * @param data 请求数据
	 */
	private static void weixinPostRequest(String url, String data) {

		try {
			URL urlPOST = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlPOST
					.openConnection();

			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();
			//向微信写数据
			OutputStream os = http.getOutputStream();
			os.write(data.getBytes("UTF-8"));
			os.flush();
			os.close();
			//读返回数据
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			System.out.println(message);//打印返回信息
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 以GET方式向微信发出请求
	 * @param url 请求地址
	 * @return 返回信息
	 */
	private static String weixinGetRequest(String url) {
		String message = "";
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet
					.openConnection();

			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();
			//读返回数据
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			message = new String(jsonBytes, "UTF-8");
			System.out.println(message);//打印返回信息
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return message;
		}
	}
	/**
	 * 清空access_token的缓存。
	 */
	public static void resetAccess_token() {
		access_tokenMapping.clear();
		access_tokenTimeMapping.clear();
	}
}

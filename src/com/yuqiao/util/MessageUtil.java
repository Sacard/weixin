package com.yuqiao.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.yuqiao.bean.BackMessage;
import com.yuqiao.bean.TextMessage;

public class MessageUtil {
	public static Map<String, String> xmlToMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream is = null;
		try {
			is = request.getInputStream();
			Document doc = reader.read(is);

			Element root = doc.getRootElement();

			root.elements();
			for (Element e : (List<Element>) root.elements()) {
				map.put(e.getName(), e.getText());

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;

	}

	public static String textMessageToXml(TextMessage tm) {
		XStream xStream = new XStream();
		xStream.aliasType("xml", tm.getClass());
		return xStream.toXML(tm);
	}

	public static String backMessageToXml(BackMessage bm) {
		XStream xStream = new XStream();
		xStream.aliasType("xml", bm.getClass());
		return xStream.toXML(bm);
	}

	public static Map<String, String> xmlToMap(String xml) {
		Map<String, String> map = new HashMap<String, String>();

		try {

			Document doc = DocumentHelper.parseText(xml);
			;
			Element root = doc.getRootElement();

			root.elements();
			for (Element e : (List<Element>) root.elements()) {
				map.put(e.getName(), e.getText());

			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;

	}
}

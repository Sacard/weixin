package com.yuqiao.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.yuqiao.bean.BackMessage;
import com.yuqiao.bean.TextMessage;
import com.yuqiao.util.MessageUtil;

public class MyServlet extends HttpServlet {
	private String token = "test";// 这个Token是随机生成，但是必须跟企业号上的相同
	private String corpID = "wx36e795a9cfda1ca8";// 这里是你企业号的CorpID
	private String encodingAESKey = "rb7rMcRBf2h1KQV3VtjdkA8ixHq1RSfakTEsk4Kx5u2";// 这个EncodingAESKey是随机生成，但是必须跟企业号上的相同
	/**
	 * 建立连接
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");

		// 微信加密签名
		String msgSignature = request.getParameter("msg_signature");
		// 时间戳
		String timeStamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echoStr = request.getParameter("echostr");
		// TODO 检验msg_signature
		// 需要返回的明文
		String newEchoStr;
		PrintWriter out = null;
		WXBizMsgCrypt wxcpt = null;
		try {
			out = response.getWriter();
			wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpID);
			newEchoStr = wxcpt.VerifyURL(msgSignature, timeStamp, nonce,
					echoStr);
			// 验证URL成功，将newEchoStr返回
			out.print(newEchoStr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AesException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	/**
	 * 接收消息并回复
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String msgSignature = req.getParameter("msg_signature");

		String timeStamp = req.getParameter("timestamp");

		String nonce = req.getParameter("nonce");
		//接收回调消息
		Map<String, String> map = MessageUtil.xmlToMap(req);

		BackMessage bm = new BackMessage();
		bm.setToUserName(map.get("ToUserName"));
		bm.setEncrypt(map.get("Encrypt"));
		bm.setAgentID(map.get("AgentID"));

		WXBizMsgCrypt wxBizMsgCrypt = null;
		try {
			wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAESKey, corpID);
			PrintWriter out = resp.getWriter();
			//解密出消息明文
			String xmlAccept = wxBizMsgCrypt.DecryptMsg(msgSignature,
					timeStamp, nonce, MessageUtil.backMessageToXml(bm));
			Map<String, String> mapAccept = MessageUtil.xmlToMap(xmlAccept);
			//构建回复消息
			TextMessage tm = new TextMessage();
			tm.setAgentID(mapAccept.get("AgentID"));
			tm.setContent("您所发送的消息是：" + mapAccept.get("Content"));
			tm.setCreateTime(new Date().getTime() + "");
			tm.setFromUserName(mapAccept.get("ToUserName"));

			tm.setMsgId(Long.parseLong(mapAccept.get("MsgId")) + 1 + "");
			tm.setMsgType(mapAccept.get("MsgType"));
			tm.setToUserName(mapAccept.get("FromUserName"));
			//将回复消息加密
			String backXml = wxBizMsgCrypt.EncryptMsg(
					MessageUtil.textMessageToXml(tm),
					new Timestamp(new Date().getTime()) + "", nonce);
			//返回回调消息
			out.print(backXml);

			out.close();

		} catch (AesException e) {

			e.printStackTrace();
		}

	}
}

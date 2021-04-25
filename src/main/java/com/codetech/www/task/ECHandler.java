package com.codetech.www.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ECHandler extends TextWebSocketHandler {

	//�α��� �� ��ü
		List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
		
	//�α������� ��������
	Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

	// Ŭ���̾�Ʈ�� ������ �����
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String senderId = getMemberId(session); // ������ ������ http������ ��ȸ�Ͽ� id�� ��� �Լ�
		if (senderId != null) { // �α��� ���� �ִ� ��츸
			System.out.println(senderId + " ���� ��");
			users.put(senderId, session); // �α����� �������� ����
		}
	}

	// Ŭ���̾�Ʈ�� Data ���� ��
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String senderId = getMemberId(session);
		// Ư�� �������� ������
		String msg = message.getPayload();
		if (msg != null) {
			String[] strs = msg.split(",");
			log(strs.toString());
			if (strs != null && strs.length == 4) {
				String type = strs[0];
				String target = strs[1]; // m_id ����
				String content = strs[2];
				String url = strs[3];
				WebSocketSession targetSession = users.get(target); // �޽����� ���� ���� ��ȸ

				// �ǽð� ���ӽ�
				if (targetSession != null) {
					// ex: [&������] ��û�� ���Խ��ϴ�.
					TextMessage tmpMsg = new TextMessage(
							"<a target='_blank' href='" + url + "'>[<b>" + type + "</b>] " + content + "</a>");
					targetSession.sendMessage(tmpMsg);
				}
			}
		}
	}

	// ���� ������ ��
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String senderId = getMemberId(session);
		if (senderId != null) { // �α��� ���� �ִ� ��츸
			log(senderId + " ���� �����");
			users.remove(senderId);
			sessions.remove(session);
		}
	}

	// ���� �߻���
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log(session.getId() + " �ͼ��� �߻�: " + exception.getMessage());

	}

	// �α� �޽���
	private void log(String logmsg) {
		System.out.println(new Date() + " : " + logmsg);
	}

	// �����Ͽ� id ��������
	// ������ ������ http������ ��ȸ�Ͽ� id�� ��� �Լ�
	private String getMemberId(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		String m_id = (String) httpSession.get("user_idS"); // ���ǿ� ����� m_id ���� ��ȸ
		return m_id == null ? null : m_id;
	}
}

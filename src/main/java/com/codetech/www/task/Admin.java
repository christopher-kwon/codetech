package com.codetech.www.task;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.codetech.www.task.EchoHandler;

// ��� �������� �������� �� ���� url
@Controller
@ServerEndpoint("/admin")
public class Admin {
	// Owner�� �� ���̶�� ����, ���� �� �̻��� ���ǿ��� ������ �ϸ� ������ ���Ǹ� �۵��Ѵ�.
	private static Session admin = null;

	// Owner ������ ������ �ϸ� �߻��ϴ� �̺�Ʈ �Լ�
	@OnOpen
	public void handleOpen(Session userSession) {
		// ������ Owner ������ ������ �������̶��
		if (admin != null) {
			try {
				// ������ ���´�.
				admin.close();
			} catch (IOException e) {
			}
		}
		// Owner ������ ������ �ٲ۴�.
		admin = userSession;
		// ������ ������ �ִ� ������ ������ Owner client�� ������.
		for (String key : EchoHandler.getUserKeys()) {
			visit(key);
		}
	}

	// Owner ������ �޽����� ������ �߻��ϴ� �̺�Ʈ
	@OnMessage
	public void handleMessage(String message, Session session) throws IOException {
		// key�� �޽��� ����Ű�� #####�� �־���.
		String[] split = message.split("#####", 2);
		// ���� key ������
		String key = split[0];
		// �� ������ �޽���
		String msg = split[1];
		// �Ϲ� ������ key�� Ž���� �޽��� ����
		EchoHandler.sendMessage(key, msg);
	}

	// ������ ����� �� Owner ������ null ó���Ѵ�.
	@OnClose
	public void handleClose(Session session) {
		admin = null;
	}

	// Owner ������ �޽����� ������ �Լ�
	private static void send(String message) {
		if (admin != null) {
			try {
				admin.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Store ������ �������� ��, Owner �������� �˸��� �Լ�
	public static void visit(String key) {
		// json ������ status�� visit�̰� key�� ���� Ű �����̴�.(javascript�� ���ߴ� ��������)
		send("{\"status\":\"visit\", \"key\":\"" + key + "\"}");
	}

	// Store ������ �޽����� ���� ��, Owner �������� �˸��� �Լ�
	public static void sendMessage(String key, String message) {
		// json ������ status�� message�̰� key�� ���� Ű �����̴�.(javascript�� ���ߴ� ��������) message�� ������
		// �޽����̴�.
		send("{\"status\":\"message\", \"key\":\"" + key + "\", \"message\":\"" + message + "\"}");
	}

	// Store ������ ������ ���� ��, Owner �������� �˸��� �Լ�
	public static void bye(String key) {
		// json ������ status�� bye�̰� key�� ���� Ű �����̴�.(javascript�� ���ߴ� ��������)
		send("{\"status\":\"bye\", \"key\":\"" + key + "\"}");
	}
}

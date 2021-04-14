package com.codetech.www.task;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.codetech.www.domain.Cart;

import oracle.jdbc.proxy.annotation.OnError;


@Controller
@ServerEndpoint(value = "/chatting") 
public class EchoHandler {
	
	public static final List<Cart> sessionList = new ArrayList<Cart>();
	private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

	public EchoHandler() {
		logger.info("������(����) ��ü����");
	}

	/*
	 * @OnOpen�� Ŭ���̾�Ʈ�� �����Ͽ� ������ ������ �ƹ��� ���� ���� ������ �� �����ϴ� �޼ҵ� �Դϴ�.
	 * 
	 * javax.websocket.Session: �����ڸ��� �Ѱ��� ������ �����Ǿ� ������ ��ż������� ���Ǹ� �����ڸ��� ���е˴ϴ�.
	 */

	@OnOpen
	public void onOpen2(Session session) {
		logger.info("Open session id:" + session.getId());
		logger.info("session ���� ��Ʈ�� : " + session.getQueryString());
		
		//id=admin&filename=/2020-1-6-/bbs2020163195410.png
		String queryString = session.getQueryString();
		String id=queryString.substring(queryString.indexOf("=")+1, queryString.indexOf("&"));
		String filename = queryString.substring(queryString.indexOf("/"));
		Cart cart = new Cart();
		cart.setSession(session);
		cart.setFilename(filename);
		cart.setId(id);
		sessionList.add(cart);
		
		String message = id + "���� �����ϼ̽��ϴ�.in";
		sendAllSessionToMessage(session, message);
	}
	
	
	//���� ��� ����(id�� ���� �̸�) ���ϱ�
	private String getinfo(Session self) {
		String information = "";
		synchronized (sessionList) {
			for(Cart cart : EchoHandler.sessionList) {
				Session s = cart.getSession();
				if(self.getId().equals(s.getId())) {
					information = cart.getId() + "&" + cart.getFilename();
					//���� ����� ���� : 1234&2019-7-6/bbs201932423.png
					logger.info("���� ����� ���� = " + information);
					break;
				}
			}
		} //synchronized end
		return information;
	}

	private void sendAllSessionToMessage(Session self, String message) {
		String info = getinfo(self);
		
		synchronized (sessionList) {
			try {
				for (Cart cart : EchoHandler.sessionList) {
					Session s = cart.getSession();
					if (!self.getId().equals(s.getId())) { //���� ������ ������� �����ϴ�.
						//admin&2019-7-6/bbs2019823472.png&admin���� �����̽��ϴ�.
						logger.info("���� ������ ��� ������� ������ �޽��� : " + info + "&" + message);
						s.getBasicRemote().sendText(info + "&" + message);
					}
				}
			} catch (Exception e) {
				logger.info("sendAllSessionToMessage ���� " + e.getMessage());
			}
			
		} //synchronized

	}

	/*
	 * @OnMessage�� Ŭ���̾�Ʈ���� �޽����� ������ ��, ����Ǵ� �޼ҵ��Դϴ�.
	 */

	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("Message : " + message);
		sendAllSessionToMessage(session, message);
	
	}

	@OnError
	public void onError(Throwable e, Session session) {
		e.printStackTrace();
		logger.info("error�Դϴ�");
		remove(session); 
	}

	// @OnClose�� Ŭ���̾�Ʈ�� �����ϰ��� ������ ����� ����Ǵ� �޼ҵ��Դϴ�.
	 @OnClose
	public void onClose(Session session) {
		logger.info("Session " + session.getId() + " has ended");
		sessionList.remove(session);
	}
	 
	 private void remove(Session session) {
		 synchronized (sessionList) {
			 for(int i = 0; i < sessionList.size(); i++) {
				 Session s = sessionList.get(i).getSession();
				 if(session.getId().contentEquals(s.getId()) ) { //���� ���� ���̵� ���� cart�� �����մϴ�.
					 sessionList.remove(i);
					 return;
				 }
			 }
		 }
	 }
}

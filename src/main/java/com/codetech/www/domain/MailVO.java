package com.codetech.www.domain;

public class MailVO {

	private String from = "codetechhta@naver.com";
	private String to;
	private String subject = "Ŀ����ũ�Դϴ�. ";
	private String content = "Ŀ����ũ�Դϴ�. ";

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

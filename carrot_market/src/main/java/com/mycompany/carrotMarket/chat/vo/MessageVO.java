package com.mycompany.carrotMarket.chat.vo;

import java.util.Date;

public class MessageVO {
	private int messageId;

	private int chatId;

	private String sender;

	private String content;

	private Date sentAt;

	public MessageVO() {

	}

	public MessageVO(int chatId, String sender, String content) {
		this.chatId = chatId;
		this.sender = sender;
		this.content = content;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	@Override
	public String toString() {
		return "MessageVO [chatId=" + chatId + ", sender=" + sender + ", content=" + content + "]";
	}

}

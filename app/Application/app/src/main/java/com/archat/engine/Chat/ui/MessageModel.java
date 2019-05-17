package com.archat.engine.Chat.ui;

public class MessageModel {
    public String chatId = "";
    public String messageId;
    public String senderId;
    public String message = "";
    public String messageType;
    public String mediaUrl = "";
    public long timeStamp;

    public MessageModel(){}

    public MessageModel(String chatId, String messageId, String senderId, String messageText, String messageType, String mediaUrl, long timeStamp){
        this.chatId = chatId;
        this.messageId = messageId;
        this.senderId = senderId;
        this.message = messageText;
        this.messageType = messageType;
        this.mediaUrl = mediaUrl;
        this.timeStamp = timeStamp;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getChatId() {
        return chatId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getSenderId() {
        return senderId;
    }
}

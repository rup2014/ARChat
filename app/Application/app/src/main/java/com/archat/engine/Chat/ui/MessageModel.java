package com.archat.engine.Chat.ui;

public class MessageModel {
    public String chatId = "";
    public String senderId;
    public String senderName;
    public String message = "";
    public String messageType;
    public String mediaUrl = "";
    public String photoUrl = "";
    public long timeStamp;

    public MessageModel(){}

    public MessageModel(String chatId, String senderId, String senderName,String messageText, String messageType, String mediaUrl, String photoUrl,long timeStamp){
        this.chatId = chatId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.message = messageText;
        this.messageType = messageType;
        this.mediaUrl = mediaUrl;
        this.photoUrl = photoUrl;
        this.timeStamp = timeStamp;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
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

    public String getMessageType() {
        return messageType;
    }

    public String getSenderId() {
        return senderId;
    }
}

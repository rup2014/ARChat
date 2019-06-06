package com.archat.engine.Chat.ui;

public class ChatModel {
    public String chatName;
    public String lastMessage;
    public String timeStamp;
    public String chatId;

    public ChatModel(){}

    public ChatModel(String chatName, String lastMessage, String timeStamp, String chatId){
        this.chatName = chatName;
        this.lastMessage = lastMessage;
        this.timeStamp = timeStamp;
        this.chatId = chatId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getChatName() {
        return chatName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getChatId() { return chatId; }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

package com.archat.engine.Chat.ui;

public class ChatModel {
    public String chatName;
    public String lastMessage;
    public long timeStamp;

    public ChatModel(){}

    public ChatModel(String chatName, String lastMessage, long timeStamp){
        this.chatName = chatName;
        this.lastMessage = lastMessage;
        this.timeStamp =timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getChatName() {
        return chatName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}

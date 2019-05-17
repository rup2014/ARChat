package com.archat.engine.Chat.ui;

public class MembersModel {
    public String chatId;
    public boolean member;

    public MembersModel(){}

    public MembersModel(String chatId, boolean member){
        this.chatId = chatId;
        this.member = member;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public boolean isMember() {
        return member;
    }
}

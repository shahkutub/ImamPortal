package com.imamportal.model;

public class MessageResponse {

    MessageData messages = new MessageData();
    UserDetails from_user = new UserDetails();

    public MessageData getMessages() {
        return messages;
    }

    public void setMessages(MessageData messages) {
        this.messages = messages;
    }

    public UserDetails getFrom_user() {
        return from_user;
    }

    public void setFrom_user(UserDetails from_user) {
        this.from_user = from_user;
    }
}

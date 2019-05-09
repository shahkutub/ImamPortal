package com.imamportal.model;

public class ChatAppMsgDTO {

    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";

    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";

    // Message content.
    private String msgContent,From_user,File,Message,Created_at;

    public String getCreated_at() {
        return Created_at;
    }

    public void setCreated_at(String created_at) {
        Created_at = created_at;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }

    public String getFrom_user() {
        return From_user;
    }

    public void setFrom_user(String from_user) {
        From_user = from_user;
    }

    // Message type.
    private String msgType;


    public ChatAppMsgDTO( String from_user, String file, String message, String created_at) {
        this.msgContent = msgContent;
        From_user = from_user;
        File = file;
        Message = message;
        Created_at = created_at;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
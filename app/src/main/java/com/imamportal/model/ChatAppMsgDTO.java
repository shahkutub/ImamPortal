package com.imamportal.model;

public class ChatAppMsgDTO {

    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";

    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";


//    "id": 9,
//                "message": "sdsd",
//                "is_seen": 0,
//                "deleted_from_sender": 0,
//                "deleted_from_receiver": 0,
//                "from_user": 16,
//                "created_at": "2018-10-17 06:03:35",
//                "updated_at": "2018-10-17 06:03:35",
//                "to_user": 14


    // Message content.
    private String message,is_seen,deleted_from_sender,deleted_from_receiver,from_user,created_at,to_user;

    public ChatAppMsgDTO(String to_user, String message) {
        this.message = message;
        this.to_user = to_user;
    }

    public static String getMsgTypeSent() {
        return MSG_TYPE_SENT;
    }

    public static String getMsgTypeReceived() {
        return MSG_TYPE_RECEIVED;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIs_seen() {
        return is_seen;
    }

    public void setIs_seen(String is_seen) {
        this.is_seen = is_seen;
    }

    public String getDeleted_from_sender() {
        return deleted_from_sender;
    }

    public void setDeleted_from_sender(String deleted_from_sender) {
        this.deleted_from_sender = deleted_from_sender;
    }

    public String getDeleted_from_receiver() {
        return deleted_from_receiver;
    }

    public void setDeleted_from_receiver(String deleted_from_receiver) {
        this.deleted_from_receiver = deleted_from_receiver;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }
}
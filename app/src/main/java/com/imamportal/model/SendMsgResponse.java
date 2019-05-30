package com.imamportal.model;

public class SendMsgResponse {
//    {
//    "status": "success",
//    "message": {
//        "message": "Test Message From API.",
//        "to_user": "1",
//        "from_user": 1,
//        "updated_at": "2019-05-23 15:21:56",
//        "created_at": "2019-05-23 15:21:56",
//        "id": 13
//    }
//}

    String status;
    SendMsgData message = new SendMsgData();

    private class SendMsgData {
        String message,to_user,from_user;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTo_user() {
            return to_user;
        }

        public void setTo_user(String to_user) {
            this.to_user = to_user;
        }

        public String getFrom_user() {
            return from_user;
        }

        public void setFrom_user(String from_user) {
            this.from_user = from_user;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SendMsgData getMessage() {
        return message;
    }

    public void setMessage(SendMsgData message) {
        this.message = message;
    }
}

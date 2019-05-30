package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class NotificationResponse {

    String comment_notification_count;
    String message_number;
    String group_message_number;

    List<NotificationInfo> comment_notification_contents = new ArrayList<>();
    List<NotificationInfo> message_contents = new ArrayList<>();
    List<NotificationInfo> group_message_contents = new ArrayList<>();

    public String getComment_notification_count() {
        return comment_notification_count;
    }

    public void setComment_notification_count(String comment_notification_count) {
        this.comment_notification_count = comment_notification_count;
    }

    public String getMessage_number() {
        return message_number;
    }

    public void setMessage_number(String message_number) {
        this.message_number = message_number;
    }

    public String getGroup_message_number() {
        return group_message_number;
    }

    public void setGroup_message_number(String group_message_number) {
        this.group_message_number = group_message_number;
    }

    public List<NotificationInfo> getComment_notification_contents() {
        return comment_notification_contents;
    }

    public void setComment_notification_contents(List<NotificationInfo> comment_notification_contents) {
        this.comment_notification_contents = comment_notification_contents;
    }

    public List<NotificationInfo> getMessage_contents() {
        return message_contents;
    }

    public void setMessage_contents(List<NotificationInfo> message_contents) {
        this.message_contents = message_contents;
    }

    public List<NotificationInfo> getGroup_message_contents() {
        return group_message_contents;
    }

    public void setGroup_message_contents(List<NotificationInfo> group_message_contents) {
        this.group_message_contents = group_message_contents;
    }
}

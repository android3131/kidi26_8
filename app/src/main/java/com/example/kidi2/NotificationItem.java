package com.example.kidi2;

public class NotificationItem {
    int imageID;
    String notificationText;
    Boolean seen;

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public NotificationItem(int imageID, String notificationText) {
        this.imageID = imageID;
        this.notificationText = notificationText;
        seen=false;
    }
}

package com.example.wechat.event;

/**
 * author:salmonzhang
 * Description:
 * Date:2017/8/16 0016 19:18
 */

public class ContactUpdateEvent {

    public String contact;
    public boolean isAdded;

    public ContactUpdateEvent(String contact, boolean isAdded) {
        this.contact = contact;
        this.isAdded = isAdded;
    }
}

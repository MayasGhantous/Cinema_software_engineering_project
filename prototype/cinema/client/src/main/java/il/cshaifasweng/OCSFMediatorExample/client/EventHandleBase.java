package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class EventHandleBase {
    private Message message;

    // Constructor
    public EventHandleBase(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
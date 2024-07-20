package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class UpdateEachUserScreeningEvent extends EventHandleBase{

    public UpdateEachUserScreeningEvent(Message message) {
        super(message);
    }
}

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class UpdateCatalogEvent extends EventHandleBase{
    public UpdateCatalogEvent(Message message) {
        super(message);
    }
}

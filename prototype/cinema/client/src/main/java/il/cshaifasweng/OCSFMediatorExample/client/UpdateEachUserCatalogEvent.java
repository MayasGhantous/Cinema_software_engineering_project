package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class UpdateEachUserCatalogEvent extends EventHandleBase{
    public UpdateEachUserCatalogEvent(Message message) {
        super(message);
    }
}

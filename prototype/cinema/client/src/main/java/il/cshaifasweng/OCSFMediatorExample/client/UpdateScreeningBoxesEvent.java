package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class UpdateScreeningBoxesEvent extends EventHandleBase{
    public UpdateScreeningBoxesEvent(Message message) {
        super(message);
    }
}

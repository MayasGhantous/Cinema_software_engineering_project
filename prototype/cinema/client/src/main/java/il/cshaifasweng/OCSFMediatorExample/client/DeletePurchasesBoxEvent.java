package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class DeletePurchasesBoxEvent extends EventHandleBase{
    public DeletePurchasesBoxEvent(Message message) {
        super(message);
    }
}
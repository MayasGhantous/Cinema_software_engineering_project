package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ShowPurchasesBoxEvent extends EventHandleBase {
    public ShowPurchasesBoxEvent(Message message) {super(message);}
}
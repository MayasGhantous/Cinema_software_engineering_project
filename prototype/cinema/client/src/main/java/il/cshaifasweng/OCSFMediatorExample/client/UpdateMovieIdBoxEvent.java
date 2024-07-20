package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class UpdateMovieIdBoxEvent extends EventHandleBase{
    public UpdateMovieIdBoxEvent(Message message) {
        super(message);
    }
}

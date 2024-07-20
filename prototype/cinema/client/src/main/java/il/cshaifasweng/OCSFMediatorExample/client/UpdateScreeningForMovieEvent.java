package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class UpdateScreeningForMovieEvent extends EventHandleBase{
    public UpdateScreeningForMovieEvent(Message message) {
        super(message);
    }
}

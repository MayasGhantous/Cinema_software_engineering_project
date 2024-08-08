package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.client.BaseEventBox;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import java.io.IOException;

public class SimpleClient extends AbstractClient {
    public static Message Current_Message;
    public static SimpleClient client = null;

    public SimpleClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        Message message = (Message) msg;

        switch (message.getMessage()) {
            case "#GotAllMovies":
                EventBus.getDefault().post(new BaseEventBox("MOVIES_GOT", message));
                break;
            case "#GoToHomePage":
                EventBus.getDefault().post(new BaseEventBox("MOVIES_GOT", message));
                break;
            case "#UpdateMovieList":
                EventBus.getDefault().post(new BaseEventBox("UPDATE_MOVIE_LIST", message));
                break;
            case "#ScreeningsGot":
                Current_Message = message;
                Platform.runLater(() -> {
                    SimpleChatClient.setWindowTitle("edit_screenings");
                    try {
                        SimpleChatClient.setRoot("EditScreening");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "#UpdateScreeningForMovie":
                EventBus.getDefault().post(new BaseEventBox("UPDATE_SCREENING_FOR_MOVIE", message));
                break;
            case "#UpdateBoxesInScreening":
                EventBus.getDefault().post(new BaseEventBox("UPDATE_BOXES_IN_SCREENING", message));
                break;
            case "#ChangeMovieIdBox":
                System.out.println("I got your message");
                EventBus.getDefault().post(new BaseEventBox("CHANGE_MOVIE_ID_BOX", message));
                break;
            case "#UpdateMovieList_Eatch":
                EventBus.getDefault().post(new BaseEventBox("UPDATE_MOVIE_LIST_EACH", message));
                break;
            case "#UpdateScreeningForMovie_each":
                EventBus.getDefault().post(new BaseEventBox("UPDATE_SCREENING_FOR_MOVIE_EACH", message));
                break;
            case "#ServerError":
                EventBus.getDefault().post(new BaseEventBox("SERVER_ERROR_MESSAGE", message));
                break;
            case "#show_purchases_client":
                EventBus.getDefault().post(new BaseEventBox("SHOW_PURCHASES", message));
                break;
            case "#delete_purchases_client":
                EventBus.getDefault().post(new BaseEventBox("DELETE_PURCHASE", message));
                break;
            case "#loginWorkerFailedUserName":
            case "#loginWorker":
            case "#loginWorkerFailedPass":
                EventBus.getDefault().post(new BaseEventBox("LOGIN", message));
                break;
            case "#userNotFound":
            case "#alreadyLoggedIn":
            case "#loginConfirmed":
            case "#serverError":
                EventBus.getDefault().post(new BaseEventBox("SERVER_ERROR_MESSAGE1", message));
                break;
            case "#Reports":
                EventBus.getDefault().post(new BaseEventBox("REPORTS", message));
                break;
            case "#gotComplainsHistogram":
            case "#gotMultiEntryReports":
            case "#gotTicketSellsReports":
            case "#reportsCreated":
            case "#fetchedReports":
            case "#foundReports":
                EventBus.getDefault().post(new BaseEventBox("REPORTS", message));
                break;
            default:
                EventBus.getDefault().post(new BaseEventBox("WRONG_NAMEING", message));
                break;
        }
    }

    public static SimpleClient getClient() {
        if (client == null) {
            client = new SimpleClient("localhost", 3000);
        }
        return client;
    }
}

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * JavaFX App
 */
public class SimpleChatClient extends Application {

    private static Scene scene;
    private static Stage appStage;
    private static Map<List<String>, List<Integer>> rooms = new HashMap<>();

    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        create_rooms();
        scene = new Scene(loadFXML("host"), 640, 480);
        appStage = stage;
        stage.setScene(scene);
        stage.show();
    }

    public static void setWindowTitle(String title) {
        appStage.setTitle(title);
    }

    public static String getWindowTitle() {
        return appStage.getTitle();
    }

    public static void setRoot(String pageName) throws IOException {
        if (pageName == null || pageName.equals("")) {
            Parent root = loadFXML("MasterPage");
            scene = new Scene(root);
            appStage.setScene(scene);
            appStage.show();
        } else {
            EventBus.getDefault().post(new BeginContentChangeEnent(pageName));
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SimpleChatClient.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        EventBus.getDefault().unregister(this);
        super.stop();
    }

    @Subscribe
    public void onMessageEvent(BaseEventBox message) {
        if (message.getId() == 13) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION,
                        String.format("Message:\nId: %d\nData: %s\nTimestamp: %s\n",
                                message.getMessage().getId(),
                                message.getMessage().getMessage(),
                                message.getMessage().getTimeStamp() != null ? message.getMessage().getTimeStamp().format(dtf) : "N/A")
                );
                alert.setTitle("new message");
                alert.setHeaderText("New Message:");
                alert.show();
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void create_rooms() {
        rooms = new HashMap<>();
        rooms.put(List.of("Sakhnin", "1"), List.of(10, 10));
        rooms.put(List.of("Sakhnin", "2"), List.of(5, 10));
        rooms.put(List.of("Sakhnin", "3"), List.of(7, 7));
        rooms.put(List.of("Haifa", "1"), List.of(10, 10));
        rooms.put(List.of("Haifa", "2"), List.of(5, 10));
        rooms.put(List.of("Haifa", "3"), List.of(7, 7));
        rooms.put(List.of("Nazareth", "1"), List.of(10, 10));
        rooms.put(List.of("Nazareth", "2"), List.of(5, 10));
        rooms.put(List.of("Nazareth", "3"), List.of(7, 7));
        rooms.put(List.of("Nhif", "1"), List.of(10, 10));
        rooms.put(List.of("Nhif", "2"), List.of(5, 10));
        rooms.put(List.of("Nhif", "3"), List.of(7, 7));
    }

    public static void print_rooms() {
        for (Map.Entry<List<String>, List<Integer>> entry : rooms.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public static List<Integer> get_rows_and_columns(List<String> keys) {
        return rooms.get(keys);
    }

    private static void add_to_rooms(String Branch, int room_number, int row_size, int column_size) {
        ArrayList<String> keys = new ArrayList<>();
        keys.add(Branch);
        keys.add(String.valueOf(room_number));
        ArrayList<Integer> values = new ArrayList<>();
        values.add(row_size);
        values.add(column_size);
        rooms.put(keys, values);
    }
}

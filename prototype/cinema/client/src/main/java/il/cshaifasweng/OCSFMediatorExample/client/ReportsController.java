package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ReportsController {

    @FXML
    private Text Error_Message;

    @FXML
    private TableColumn<?, ?> branch_col;

    @FXML
    private ComboBox<?> branch_comboBox;

    @FXML
    private BarChart<?, ?> complaintsChart;

    @FXML
    private DatePicker date_;

    @FXML
    private TableColumn<?, ?> date_col;

    @FXML
    private ComboBox<?> id_sort;

    @FXML
    private TextField report;

    @FXML
    private TableView<?> reports_table;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    void get_sorted_reports(ActionEvent event) {

    }

    @FXML
    void search_report_button(ActionEvent event) {

    }

    @FXML
    void view_report(MouseEvent event) {

    }

    @Subscribe
    public void change_content1(BeginContentChangeEnent event) {
        System.out.println(event.getPage());
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().post(new ContentChangeEvent(event.getPage()));
    }

}



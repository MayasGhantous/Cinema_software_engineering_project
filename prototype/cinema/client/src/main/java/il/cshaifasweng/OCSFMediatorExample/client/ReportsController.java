package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Reports;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ReportsController {

    @FXML
    private Text Error_Message;

    @FXML
    private TableColumn<Reports, String> branch_col;

    @FXML
    private ComboBox<String> branch_comboBox;

    @FXML
    private BarChart<String, Number> complaintsChart;

    @FXML
    private DatePicker date_;

    @FXML
    private TableColumn<Reports, String> date_col;

    @FXML
    private ComboBox<String> id_sort;

    @FXML
    private TextField report;

    @FXML
    private TableView<Reports> reports_table;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private ObservableList<Reports> reportsList;
    private ObservableList<Reports> originalReportsList;

    @FXML
    void initialize() throws IOException {
        EventBus.getDefault().register(this);
        Error_Message.setVisible(false);

        branch_comboBox.getItems().clear();
        branch_comboBox.getItems().addAll("", "Sakhnin", "Haifa", "Nazareth", "Nhif");

        id_sort.getItems().clear();
        id_sort.getItems().addAll("", "branch", "date");

        // Initialize table columns
        date_col.setCellValueFactory(new PropertyValueFactory<>("reportDate"));
        branch_col.setCellValueFactory(new PropertyValueFactory<>("branch"));

        // Initialize lists
        reportsList = FXCollections.observableArrayList();
        originalReportsList = FXCollections.observableArrayList();
        reports_table.setItems(reportsList);

        // Initialize charts
        xAxis.setLabel("Month");
        yAxis.setLabel("Number of Complaints");

        displayComplaintsHistogram();
        fetchMultiEntryTicketsReports();
        fetchTicketSellsReports();
        create_reports();
        display_report_table();
    }

    private void create_reports() throws IOException {
        System.out.println("got into create_reports");
        //Message message = new Message(3, "#createReports");
        Message message = new Message(BaseEventBox.get_event_id("REPORTS"), "#createReports");
        SimpleClient.getClient().sendToServer(message);
        handleReportsCreation(message);
    }


    public void handleReportsCreation(Message message) {
        System.out.println("got into handleReportsCreation");
        if (!"#reportsCreated".equals(message.getMessage())) {
            return;
        }
        try {
            display_report_table();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void display_report_table() throws IOException {
        System.out.println("got into display_report_table");

        //Message message = new Message(4, "#fetchReports");
        Message message = new Message(BaseEventBox.get_event_id("REPORTS"), "#fetchReports");
        SimpleClient.getClient().sendToServer(message);
        handleReportsFetch(message);
    }


    public void handleReportsFetch(Message message) {
        System.out.println("got into handleReportsFetch");
        if (!"#fetchedReports".equals(message.getMessage())) {
            return;
        }

        List<Reports> fetchedReports = (List<Reports>) message.getObject2();
        reportsList.setAll(fetchedReports);
        reports_table.setItems((ObservableList<Reports>) reportsList);
        originalReportsList.setAll(fetchedReports);

        for (Reports report : fetchedReports) {
            System.out.println("Fetched report: " + report.getBranch() + ", " + report.getReportDate());
        }
    }

    private void fetchMultiEntryTicketsReports() throws IOException {
        System.out.println("got into fetchMultiEntryTicketsReports");
        //Message message = new Message(1, "#getMultiEntry");
        Message message = new Message(BaseEventBox.get_event_id("REPORTS"), "#getMultiEntry");
        SimpleClient.getClient().sendToServer(message);
        handleMultiEntryTicketsReports(message);
    }


    public void handleMultiEntryTicketsReports(Message message) {
        System.out.println("got into handleMultiEntryTicketsReports");
        if (!"#gotMultiEntryReports".equals(message.getMessage())) {
            return;
        }

        Map<String, Long> multiEntryTicketsReport = (Map<String, Long>) message.getObject();

        for (Map.Entry<String, Long> entry : multiEntryTicketsReport.entrySet()) {
            System.out.println("User: " + entry.getKey() + ", Remaining Tickets: " + entry.getValue());
        }
    }

    private void fetchTicketSellsReports() throws IOException {
        System.out.println("got into fetchTicketSellsReports");
        //Message message = new Message(2, "#getTicketSells");
        Message message = new Message(BaseEventBox.get_event_id("REPORTS"), "#getTicketSells");

        SimpleClient.getClient().sendToServer(message);
        handleTicketSellsReports(message);
    }

    public void handleTicketSellsReports(Message message) {
        System.out.println("got into handleTicketSellsReports");
        if (!"#gotTicketSellsReports".equals(message.getMessage())) {
            return;
        }

        Map<String, Map<Integer, Long>> ticketSellsReport = (Map<String, Map<Integer, Long>>) message.getObject();

        for (Map.Entry<String, Map<Integer, Long>> entry : ticketSellsReport.entrySet()) {
            String branch = entry.getKey();
            Map<Integer, Long> monthlySales = entry.getValue();
            System.out.println("Branch: " + branch);
            for (Map.Entry<Integer, Long> monthEntry : monthlySales.entrySet()) {
                System.out.println("Month: " + monthEntry.getKey() + ", Sales: " + monthEntry.getValue());
            }
        }
    }

    private void displayComplaintsHistogram() throws IOException {
        System.out.println("got into displayComplaintsHistogram");
        //Message message = new Message(0, "#getComplainsHistogram");
        Message message = new Message(BaseEventBox.get_event_id("REPORTS"), "#getComplainsHistogram");

        SimpleClient.getClient().sendToServer(message);
        handleComplaintsHistogram(message);
    }

//    public void handleComplaintsHistogram(Message message) {
//        System.out.println("got into handleComplaintsHistogram");
//        if (!"#gotComplainsHistogram".equals(message.getMessage())) {
//            return;
//        }
//
//        Map<String, Map<Integer, Long>> complaintsHistogram = (Map<String, Map<Integer, Long>>) message.getObject2();
//        complaintsChart.getData().clear();
//
//        for (Map.Entry<String, Map<Integer, Long>> entry : complaintsHistogram.entrySet()) {
//            String branch = entry.getKey();
//            XYChart.Series<String, Number> series = new XYChart.Series<>();
//            series.setName(branch);
//            Map<Integer, Long> monthCounts = entry.getValue();
//
//            for (Map.Entry<Integer, Long> monthEntry : monthCounts.entrySet()) {
//                series.getData().add(new XYChart.Data<>(monthEntry.getKey().toString(), monthEntry.getValue()));
//            }
//
//            complaintsChart.getData().add(series);
//        }
//    }

    public void handleComplaintsHistogram(Message message) {
        System.out.println("got into handleComplaintsHistogram");
        if (!"#gotComplainsHistogram".equals(message.getMessage())) {
            return;
        }

        Map<String, Map<Integer, Long>> complaintsHistogram = (Map<String, Map<Integer, Long>>) message.getObject2();
        complaintsChart.getData().clear();

        for (Map.Entry<String, Map<Integer, Long>> entry : complaintsHistogram.entrySet()) {
            String branch = entry.getKey();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(branch);
            Map<Integer, Long> monthCounts = entry.getValue();

            for (Map.Entry<Integer, Long> monthEntry : monthCounts.entrySet()) {
                series.getData().add(new XYChart.Data<>(monthEntry.getKey().toString(), monthEntry.getValue()));
            }

            complaintsChart.getData().add(series);
        }
    }
    @FXML
    void get_sorted_reports(ActionEvent event) {
        String sortBy = id_sort.getValue();
        if (sortBy == null || sortBy.isEmpty()) {
            reportsList.setAll(originalReportsList);
            return;
        }

        List<Reports> sortedReports = new ArrayList<>(reportsList);
        if (sortBy.equals("branch")) {
            sortedReports.sort(Comparator.comparing(Reports::getBranch));
        } else if (sortBy.equals("date")) {
            sortedReports.sort(Comparator.comparing(Reports::getReportDate));
        }

        reportsList.setAll(sortedReports);
    }

    @FXML
    void search_report_button(ActionEvent event) {
        String branch = branch_comboBox.getValue();
        LocalDate localDate = date_.getValue();

        System.out.println("Selected Branch: " + branch);
        System.out.println("Selected Date: " + localDate);

        if (branch == null || branch.isEmpty()) {
            Error_Message.setVisible(true);
            Error_Message.setText("Please select a branch.");
            return;
        }

        try {
            Message message;
            if (localDate == null) {
                // Search by branch only
                message = new Message(BaseEventBox.get_event_id("REPORTS"), "#searchReportsByBranch");
                message.setObject(branch);
                message.setObject2(null);
            } else {
                // Search by branch and date
                Date date = java.sql.Date.valueOf(localDate);
                System.out.println("Formatted Date: " + date);
                message = new Message(BaseEventBox.get_event_id("REPORTS"), "#searchReportsByBranchAndDate");
                message.setObject(branch);
                message.setObject2(date);
            }
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void handleFoundReports(Message message) {
        System.out.println("got into handleFoundReports");
        if (!"#foundReports".equals(message.getMessage())) {
            return;
        }

        List<Reports> foundReports = (List<Reports>) message.getObject2();
        System.out.println("Found Reports: " + foundReports.size());

        for (Reports report : foundReports) {
            System.out.println("Report: Branch = " + report.getBranch() + ", Date = " + report.getReportDate());
        }

        reportsList.setAll(foundReports);
        reports_table.setItems((ObservableList<Reports>) reportsList);

        if (foundReports.isEmpty()) {
            Error_Message.setVisible(true);
            Error_Message.setText("No reports found for the selected branch and date.");
        } else {
            Error_Message.setVisible(false);
        }
    }


    @FXML
    void view_report(MouseEvent event) {
        Reports selectedReport = reports_table.getSelectionModel().getSelectedItem();
        if (selectedReport != null) {
            report.setText(convertReportToString(selectedReport));
        }
    }

    private String convertReportToString(Reports selectedReport) {
        // Convert the selected report to a string for display
        StringBuilder reportString = new StringBuilder();
        reportString.append("Branch: ").append(selectedReport.getBranch())
                .append("\nDate: ").append(selectedReport.getReportDate().toString())
                .append("\nTicket Sells: ").append(selectedReport.getReport_ticket_sells().toString())
                .append("\nMulti-Entry Tickets: ").append(selectedReport.getReport_multy_entry_ticket().toString())
                .append("\nComplaints: ").append(selectedReport.getReport_complains().toString());
        return reportString.toString();
    }

//    @FXML
//    void get_sorted_reports(ActionEvent event) {
//        String sortBy = id_sort.getValue();
//        if (sortBy == null || sortBy.isEmpty()) {
//            reportsList.setAll(originalReportsList);
//            return;
//        }
//
//        List<Reports> sortedReports = new ArrayList<>(reportsList);
//        if (sortBy.equals("branch")) {
//            sortedReports.sort(Comparator.comparing(Reports::getBranch));
//        } else if (sortBy.equals("date")) {
//            sortedReports.sort(Comparator.comparing(Reports::getReportDate));
//        }
//
//        reportsList.setAll(sortedReports);
//    }
//
//    @FXML
//    void search_report_button(ActionEvent event) {
//        String branch = branch_comboBox.getValue();
//        LocalDate localDate = date_.getValue();
//        if (branch == null || localDate == null) {
//            Error_Message.setVisible(true);
//            Error_Message.setText("Please select both branch and date.");
//            return;
//        }
//        java.sql.Date date = java.sql.Date.valueOf(localDate);
//
//        ObservableList<Reports> filteredReports = FXCollections.observableArrayList();
//        for (Reports report : originalReportsList) {
//            if (report.getBranch().equals(branch) && report.getReportDate().equals(date)) {
//                filteredReports.add(report);
//            }
//        }
//
//        if (filteredReports.isEmpty()) {
//            Error_Message.setVisible(true);
//            Error_Message.setText("No reports found for the selected branch and date.");
//        } else {
//            Error_Message.setVisible(false);
//            reportsList.setAll(filteredReports);
//        }
//    }
//
//    @FXML
//    void view_report(MouseEvent event) {
//        Reports selectedReport = reports_table.getSelectionModel().getSelectedItem();
//        if (selectedReport != null) {
//            report.setText(convertReportToString(selectedReport));
//        }
//    }
//
//    private String convertReportToString(Reports selectedReport) {
//        // Convert the selected report to a string for display
//        StringBuilder reportString = new StringBuilder();
//        reportString.append("Branch: ").append(selectedReport.getBranch())
//                .append("\nDate: ").append(selectedReport.getReportDate().toString())
//                .append("\nTicket Sells: ").append(selectedReport.getReport_ticket_sells())
//                .append("\nMulti-Entry Tickets: ").append(selectedReport.getReport_multy_entry_ticket())
//                .append("\nComplaints: ").append(selectedReport.getReport_complains());
//        return reportString.toString();
//    }

    @Subscribe
    public void change_content1(BeginContentChangeEnent event) {
        System.out.println(event.getPage());
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().post(new ContentChangeEvent(event.getPage()));
    }

    @Subscribe
    public void implementations(BaseEventBox event) {
        if (event.getId() == BaseEventBox.get_event_id("REPORTS")) {
            Message message = event.getMessage();
            switch (message.getMessage()) {
                case "#reportsCreated":
                    Platform.runLater(() -> handleReportsCreation(message));
                    break;
                case "#fetchedReports":
                    Platform.runLater(() -> handleReportsFetch(message));
                    break;
                case "#gotComplainsHistogram":
                    Platform.runLater(() -> handleComplaintsHistogram(message));
                    break;
                case "#gotMultiEntryReports":
                    Platform.runLater(() -> handleMultiEntryTicketsReports(message));
                    break;
                case "#gotTicketSellsReports":
                    Platform.runLater(() -> handleTicketSellsReports(message));
                    break;
                case "#foundReports":
                    Platform.runLater(() -> handleFoundReports(message));
                    break;
                default:
                    break;
            }
        }
    }
}

package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.impl.CustomerBoImpl;
import dao.util.BoType;
import db.DBConnection;
import dto.ItemDto;
import dto.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.CustomerDto;
import dao.custom.CustomerDao;
import dao.custom.impl.CustomerDaoImpl;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.*;
import java.util.List;


public class ItemFormController {

    @FXML
    private TableColumn colUntPr;

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colOption;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtUntPr;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtQty;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colUntPr.setCellValueFactory(new PropertyValueFactory<>("untPr"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }

    private void setData(ItemTm newValue) {
        if (newValue != null) {
            txtCode.setEditable(false);
            txtCode.setText(newValue.getCode());
            txtDesc.setText(newValue.getDesc());
            txtUntPr.setText(newValue.getUnitPrice()+"");
            txtQty.setText(String.valueOf(newValue.getQty()));
        }
    }

    private void loadCustomerTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        try {
            List<ItemDto> dtoList  = itemBo.allItem();
            for (ItemDto dto:dtoList) {
                Button btn = new Button("Delete");
                ItemTm c = new ItemTm(
                        dto.getCode(),
                        dto.getDesc(),
                        dto.getUnitPrice(),
                        dto.getQty(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(c.getCode());
                });

                tmList.add(c);
            }
            tblItem.setItems(tmList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String id) {

        try {
            boolean isDeleted = itemBo.deleteItem(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadCustomerTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {
        loadCustomerTable();
        tblItem.refresh();
        clearFields();
    }

    private void clearFields() {
        tblItem.refresh();
        txtQty.clear();
        txtUntPr.clear();
        txtDesc.clear();
        txtCode.clear();
        txtCode.setEditable(true);
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        ItemDto dto = new ItemDto(
                txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUntPr.getText()),
                Integer.parseInt(txtQty.getText())
        );

        try {
            boolean isSaved = itemBo.saveItem(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        ItemDto dto = new ItemDto(
                txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUntPr.getText()),
                Integer.parseInt(txtQty.getText())
        );

        try {
            boolean isUpdated = itemBo.updateItem(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Item "+dto.getCode()+" Updated!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void backButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) tblItem.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reportButtonOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/customer_report.jrxml");
            //
            //
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
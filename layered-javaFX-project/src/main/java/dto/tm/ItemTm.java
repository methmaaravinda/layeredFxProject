package dto.tm;

import javafx.scene.control.Button;

public class ItemTm {
    private String code;
    private String desc;
    private double unitPrice;
    private int qty;
    private Button btn;


    public ItemTm() {

    }

    public ItemTm(String code, String desc, double unitPrice, int qty, Button btn) {
        this.code = code;
        this.desc = desc;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.btn = btn;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public Button getBtn() {
        return btn;
    }

    // Setters
    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    // toString method
    @Override
    public String toString() {
        return "ItemTm{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", btn=" + btn +
                '}';
    }
}

package rig.up.Model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

import javax.sql.StatementEvent;

public class SavedbuildII implements Serializable {

    @Exclude private String SavedName;
    private String Casing;
    private String Graphic_Card;
    private String Memory;
    private String Motherboard;
    private String Power_Supply;
    private String Processor;
    private String Storage;
    private String Total_Price;
    private String Total_Wattage;
    private String UserId;

    public SavedbuildII() {
    }

    public SavedbuildII(String savedName, String casing, String graphic_Card, String memory, String motherboard, String power_Supply, String processor, String storage, String total_Price, String total_Wattage, String userId) {
        this.SavedName = savedName;
        Casing = casing;
        Graphic_Card = graphic_Card;
        Memory = memory;
        Motherboard = motherboard;
        Power_Supply = power_Supply;
        Processor = processor;
        Storage = storage;
        Total_Price = total_Price;
        Total_Wattage = total_Wattage;
        UserId = userId;
    }

    public String getSavedName() { return SavedName; }

    public void setSavedName(String savedName) {
        SavedName = savedName;
    }

    public String getCasing() {
        return Casing;
    }

    public void setCasing(String casing) {
        Casing = casing;
    }

    public String getGraphic_Card() {
        return Graphic_Card;
    }

    public void setGraphic_Card(String graphic_Card) {
        Graphic_Card = graphic_Card;
    }

    public String getMemory() {
        return Memory;
    }

    public void setMemory(String memory) {
        Memory = memory;
    }

    public String getMotherboard() {
        return Motherboard;
    }

    public void setMotherboard(String motherboard) {
        Motherboard = motherboard;
    }

    public String getPower_Supply() {
        return Power_Supply;
    }

    public void setPower_Supply(String power_Supply) {
        Power_Supply = power_Supply;
    }

    public String getProcessor() {
        return Processor;
    }

    public void setProcessor(String processor) {
        Processor = processor;
    }

    public String getStorage() {
        return Storage;
    }

    public void setStorage(String storage) {
        Storage = storage;
    }

    public String getTotal_Price() {
        return Total_Price;
    }

    public void setTotal_Price(String total_Price) {
        Total_Price = total_Price;
    }

    public String getTotal_Wattage() {
        return Total_Wattage;
    }

    public void setTotal_Wattage(String total_Wattage) {
        Total_Wattage = total_Wattage;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}

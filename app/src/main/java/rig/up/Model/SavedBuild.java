package rig.up.Model;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class SavedBuild {

    private String savedName;
    private String name;
    private String tPrice;
    private String tWatt;

    List<Component> componentsMobo;
    List<Component> componentsCpu;
    List<Component> componentsMemory;
    List<Component> componentsGpu;
    List<Component> componentsStorage;
    List<Component> componentsCasing;
    List<Component> componentsPsu;

    public SavedBuild(String toString2, String aCommon, String s2, String string1, String toString1, String s1, String s, String name, String email, String toString, DatabaseReference component) {
    }

    public SavedBuild(String savedName, String name, String tPrice, String tWatt, List<Component> componentsMobo, List<Component> componentsCpu, List<Component> componentsMemory, List<Component> componentsGpu, List<Component> componentsStorage, List<Component> componentsCasing, List<Component> componentsPsu) {
        this.savedName = savedName;
        this.name = name;
        this.tPrice = tPrice;
        this.tWatt = tWatt;
        this.componentsMobo = componentsMobo;
        this.componentsCpu = componentsCpu;
        this.componentsMemory = componentsMemory;
        this.componentsGpu = componentsGpu;
        this.componentsStorage = componentsStorage;
        this.componentsCasing = componentsCasing;
        this.componentsPsu = componentsPsu;
    }

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String gettPrice() {
        return tPrice;
    }

    public void settPrice(String tPrice) {
        this.tPrice = tPrice;
    }

    public String gettWatt() {
        return tWatt;
    }

    public void settWatt(String tWatt) {
        this.tWatt = tWatt;
    }

    public List<Component> getComponentsMobo() {
        return componentsMobo;
    }

    public void setComponentsMobo(List<Component> componentsMobo) {
        this.componentsMobo = componentsMobo;
    }

    public List<Component> getComponentsCpu() {
        return componentsCpu;
    }

    public void setComponentsCpu(List<Component> componentsCpu) {
        this.componentsCpu = componentsCpu;
    }

    public List<Component> getComponentsMemory() {
        return componentsMemory;
    }

    public void setComponentsMemory(List<Component> componentsMemory) {
        this.componentsMemory = componentsMemory;
    }

    public List<Component> getComponentsGpu() {
        return componentsGpu;
    }

    public void setComponentsGpu(List<Component> componentsGpu) {
        this.componentsGpu = componentsGpu;
    }

    public List<Component> getComponentsStorage() {
        return componentsStorage;
    }

    public void setComponentsStorage(List<Component> componentsStorage) {
        this.componentsStorage = componentsStorage;
    }

    public List<Component> getComponentsCasing() {
        return componentsCasing;
    }

    public void setComponentsCasing(List<Component> componentsCasing) {
        this.componentsCasing = componentsCasing;
    }

    public List<Component> getComponentsPsu() {
        return componentsPsu;
    }

    public void setComponentsPsu(List<Component> componentsPsu) {
        this.componentsPsu = componentsPsu;
    }
}

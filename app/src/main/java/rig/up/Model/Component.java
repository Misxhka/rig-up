package rig.up.Model;

public class Component {

    private String BuildId;
    private String Build_CategoryId;
    private String CategoryId;
    private String Description;
    private String Image;
    private String Name;
    private String Price;
    private String SocketId;
    private String Wattage;

    public Component() {
    }

    public Component(String buildId,String build_CategoryId, String categoryId, String description,String image, String name, String price, String socketId, String wattage ) {
        BuildId = buildId;
        Build_CategoryId = build_CategoryId;
        CategoryId = categoryId;
        Description = description;
        Image = image;
        Name = name;
        Price = price;
        SocketId = socketId;
        Wattage = wattage;
    }

    public String getBuild_CategoryId() { return Build_CategoryId; }

    public void setBuild_CategoryId(String build_CategoryId) { Build_CategoryId = build_CategoryId; }

    public String getBuildId() { return BuildId; }

    public void setBuildId(String buildId) { BuildId = buildId; }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() { return Image; }

    public void setImage(String image) { Image = image; }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSocketId() {
        return SocketId;
    }

    public void setSocketId(String socketId) {
        SocketId = socketId;
    }

    public String getWattage() {
        return Wattage;
    }

    public void setWattage(String wattage) {
        Wattage = wattage;
    }

    public String getKey() { return getKey(); }
}

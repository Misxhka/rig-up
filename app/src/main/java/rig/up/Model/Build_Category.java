package rig.up.Model;

public class Build_Category {

    private String BuildId;
    private String CategoryId;

    public Build_Category(String buildId, String categoryId) {
        BuildId = buildId;
        CategoryId = categoryId;
    }

    public Build_Category() {
    }

    public String getBuildId() {
        return BuildId;
    }

    public void setBuildId(String buildId) {
        BuildId = buildId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getJsonObject(){

        return "{BuildId:"+BuildId+", CategoryId:"+CategoryId+"}";
    }

}

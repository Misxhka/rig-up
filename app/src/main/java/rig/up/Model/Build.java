package rig.up.Model;

public class Build extends Component {

    private String Name;

    public Build() {
    }

    public Build(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

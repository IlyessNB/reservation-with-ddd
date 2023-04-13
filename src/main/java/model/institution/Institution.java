package model.institution;

public class Institution {
    String id;
    String name;
    String address;

    public Institution(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

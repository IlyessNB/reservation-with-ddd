package model.institution;

public class Institution {
    String id;
    String name;
    String address;

    private Institution(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public static Institution of(String id, String name, String address) {
        return new Institution(id, name, address);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

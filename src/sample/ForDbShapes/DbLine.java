package sample.ForDbShapes;

public class DbLine {
    private Integer id;
    private String name;
    private Double length;
    private String about;

    public DbLine(Integer id, String name, Double length, String about) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.about = about;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLength() {
        return length;
    }

    public String getAbout() {
        return about;
    }
}

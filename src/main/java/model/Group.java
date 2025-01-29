package model;

public class Group {
    private Long id;
    private String groupNumber;
    private String facultyName;

    public Group() {
    }

    public Group(Long id, String groupNumber, String facultyName) {
        this.id = id;
        this.groupNumber = groupNumber;
        this.facultyName = facultyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
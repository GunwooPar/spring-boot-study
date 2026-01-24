package spring_study.spring_study.domain;

public enum BoardUserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String grade;

    BoardUserRole(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}

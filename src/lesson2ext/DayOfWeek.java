package lesson2ext;

public enum DayOfWeek {
    MONDAY("40 hours"), TUESDAY("32 hours"), WEDNESDAY("24 hours"), THURSDAY("16 hours"), FRIDAY("8 hours"), SATURDAY("Weekend"), SUNDAY("Weekend");

    private String workingHours;

    public String getWorkingHours() {
        return workingHours;
    }

    DayOfWeek(String workingHours) {
        this.workingHours = workingHours;
    }
}


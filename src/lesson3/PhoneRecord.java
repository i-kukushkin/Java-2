package lesson3;

public final class PhoneRecord {
    private String surname;
    private String phoneNumber;

    public PhoneRecord(String surname, String phoneNumber) {
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

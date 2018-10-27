package lesson3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PhoneBook {
    private List<PhoneRecord> phoneBook = new ArrayList<>();
    private final String MISSING_RECORD_MSG = "Такой записи не существует.";

    public PhoneBook() {
    }

    public final void add(String surname, String phoneNumber) {
        phoneBook.add(new PhoneRecord(surname, phoneNumber));
    }

    public final void get(String surname) {
        Iterator<PhoneRecord> iterator = phoneBook.iterator();
        boolean valueIsPresent = false;
        while (iterator.hasNext()) {
            PhoneRecord currentRecord = iterator.next();
            if (surname.equalsIgnoreCase(currentRecord.getSurname())) {
                System.out.println(currentRecord.getSurname() + " " + currentRecord.getPhoneNumber());
                valueIsPresent = true;
            }
            if (!iterator.hasNext() && !valueIsPresent)
                System.out.println(MISSING_RECORD_MSG);
        }
    }
}

package lesson2ext;

/**
 * Java 2. Extended homework to Lesson 2.
 * @author Ilya Kukushkin
 * @version 25 Oct 2018
 */

public class DayOfWeekMain {
    public static void main(String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.MONDAY));
        System.out.println(getWorkingHours(DayOfWeek.TUESDAY));
        System.out.println(getWorkingHours(DayOfWeek.WEDNESDAY));
        System.out.println(getWorkingHours(DayOfWeek.THURSDAY));
        System.out.println(getWorkingHours(DayOfWeek.FRIDAY));
        System.out.println(getWorkingHours(DayOfWeek.SATURDAY));
        System.out.println(getWorkingHours(DayOfWeek.SUNDAY));
    }

    private static String getWorkingHours(DayOfWeek dayOfWeek) {
        return dayOfWeek + ": " + dayOfWeek.getWorkingHours();
    }
}

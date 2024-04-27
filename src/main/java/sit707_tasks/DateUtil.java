package sit707_tasks;

public class DateUtil {
    // Months in order 0-11 maps to January-December.
    private static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };

    private static final int[] MONTH_DAYS = {
        31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31
    };

    private int day, month, year;

    /**
     * Constructs an object from the given day, month, and year.
     */
    public DateUtil(int day, int month, int year) {
        validateDate(day, month, year);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Validates the input day, month, and year.
     */
    private void validateDate(int day, int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month: " + month + ", expected range 1-12");
        }
        if (year < 1700 || year > 2100) {
            throw new IllegalArgumentException("Invalid year: " + year + ", expected range 1700-2024");
        }
        int maxDay = getMonthDuration(month, year);
        if (day < 1 || day > maxDay) {
            throw new IllegalArgumentException("Invalid day: " + day + ", expected range 1-" + maxDay + " for " + MONTHS[month - 1] + " " + year);
        }
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    /**
     * Increment one day.
     */
    public void increment() {
        day++;
        int maxDay = getMonthDuration(month, year);
        if (day > maxDay) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
    }

    /**
     * Decrement one day from current date.
     */
    public void decrement() {
        day--;
        if (day < 1) {
            month--;
            if (month < 1) {
                month = 12;
                year--;
            }
            day = getMonthDuration(month, year);
        }
    }

    /**
     * Add a specified number of months to the current date.
     */
    public void addMonths(int months) {
        month += months;
        year += month / 12;
        month %= 12;
        if (month <= 0) {
            month += 12;
            year--;
        }
        int maxDay = getMonthDuration(month, year);
        if (day > maxDay) {
            day = maxDay;
        }
    }

    /**
     * Subtract a specified number of months from the current date.
     */
    public void subtractMonths(int months) {
        addMonths(-months);
    }

    /**
     * Add a specified number of years to the current date.
     */
    public void addYears(int years) {
        // Update the year by adding the specified number of years
        this.year += years;
        
        // Check if the current day is valid in the new year and adjust if necessary
        int maxDay = getMonthDuration(this.month, this.year);
        if (this.day > maxDay) {
            this.day = maxDay;
        }
    }

    /**
     * Subtract a specified number of years from the current date.
     */
    public void subtractYears(int years) {
        // Update the year by subtracting the specified number of years
        this.year -= years;
        
        // Check if the current day is valid in the new year and adjust if necessary
        int maxDay = getMonthDuration(this.month, this.year);
        if (this.day > maxDay) {
            this.day = maxDay;
        }
    }
    /**
     * Calculate the duration of a month in a year.
     */
    public static int getMonthDuration(int month, int year) {
        if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        }
        return MONTH_DAYS[month - 1];
    }

    /**
     * Check if it is a leap year.
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Calculate the day of the week for a given date using Zeller's Congruence algorithm.
     */
    public int calculateDayOfWeek() {
        // Adjust month and year for the algorithm
        int adjustedMonth = month;
        int adjustedYear = year;
        
        if (adjustedMonth < 3) {
            adjustedMonth += 12;
            adjustedYear--;
        }
        
        int a = day;
        int b = adjustedYear % 100;
        int c = adjustedYear / 100;
        
        int h = (a + (13 * (adjustedMonth + 1)) / 5 + b + b / 4 + c / 4 - 2 * c) % 7;
        
        // Return day of the week where 0 is Sunday, and so on.
        
        return (h + 6) % 7;
    }

    /**
     * User-friendly output.
     */
    @Override
    public String toString() {
        return day + " " + MONTHS[month - 1] + " " + year;
    }

}

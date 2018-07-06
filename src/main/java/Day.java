/**
 * Created by Gilad-Laptop on 5/4/2018.
 */
public enum Day {
    SUNDAY (0),
    MONDAY (1),
    TUESDAY (2),
    WEDNESDAY (3),
    THURSDAY (4),
    FRIDAY (5),
    SATURDAY (6);

    private final int day;

    Day(int day) {
        this.day = day;
    }

    public String stringValue(){
        switch (this.day){
            case 1:
                return "SUNDAY";
            case 2:
                return "MONDAY";
            case 3:
                return "TUESDAY";
            case 4:
                return "WEDNESDAY";
            case 5:
                return "THURSDAY";
            case 6:
                return "FRIDAY";
            case 7:
                return "SATURDAY";
        }
        return null;
    }

    public static Day fromString(String day){
        switch (day.toUpperCase()){
            case "SUNDAY":
                return Day.SUNDAY;
            case "MONDAY":
                return Day.MONDAY;
            case "TUESDAY":
                return Day.TUESDAY;
            case "WEDNESDAY":
                return Day.WEDNESDAY;
            case "THURSDAY":
                return Day.THURSDAY;
            case "FRIDAY":
                return Day.FRIDAY;
            case "SATURDAY":
                return Day.SATURDAY;
        }
        System.out.println("could not find day for: '" + day + "'");
        return null;
    }

    public static Day fromInt(int day){
        switch (day){
            case 0:
                return Day.SUNDAY;
            case 1:
                return Day.MONDAY;
            case 2:
                return Day.TUESDAY;
            case 3:
                return Day.WEDNESDAY;
            case 4:
                return Day.THURSDAY;
            case 5:
                return Day.FRIDAY;
            case 6:
                return Day.SATURDAY;
        }
        System.out.println("could not find day for: '" + day + "'");
        return null;
    }

    public int intValue() {
        return this.day;
    }
}

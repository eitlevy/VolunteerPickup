/**
 * Created by Gilad-Laptop on 5/5/2018.
 */
public enum Time {

    MORNING (0),
    NOON (1),
    EVENING (2);

    private final int time;

    Time(int time) {this.time = time;}

    public String stringValue(Time time) {
        switch (this.time) {
            case 1:
                return "00:00-08:00";
            case 2:
                return "08:00-16:00";
            case 3:
                return "16:00-00:00";

        }

        return null;
    }

    public static Time fromString(String time) {
        switch (time) {
            case "00:00-08:00":
                return MORNING;
            case "08:00-16:00":
                return NOON;
            case "16:00-00:00":
                return EVENING;
        }
        System.out.println("could not find time for: '" + time + "'");
        return null;
    }

    public static Time fromInt(int time) {
        switch (time) {
            case 0:
                return MORNING;
            case 1:
                return NOON;
            case 2:
                return EVENING;
        }
        System.out.println("could not find time for: '" + time + "'");
        return null;
    }

    public int intValue() {
        return this.time;
    }
}

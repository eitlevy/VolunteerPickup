import com.opencsv.CSVReader;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Google API key:
 * AIzaSyCEq5H7SqyQu5r4uoa_I_bD2KEJ_oh4Sg0
 *
 * Google API URL example:
 * https://maps.googleapis.com/maps/api/distancematrix/json?origins=Oranit&destinations=Jerusalem&key=AIzaSyCEq5H7SqyQu5r4uoa_I_bD2KEJ_oh4Sg0
 *
 * import: alt+enter
 * send web request java
 * json library
 * refactor: shift+F6
 * travelling agent algorithm
 *
 * SCV file path: C:\\Users\\Gilad-Laptop\\Desktop/test1.csv
 */
public class VolunteerPlacementMain {

    private static Shift[] shifts;
    private static Comparator<Pair<Integer, Integer>> shiftSizeComparator = Comparator.comparingInt(Pair::getValue);

    public static void main(String[] args) throws FileNotFoundException
    {
        DriveDurationCache.init("AIzaSyCEq5H7SqyQu5r4uoa_I_bD2KEJ_oh4Sg0");
        Map<String, Volunteer> volunteers = new HashMap<>();

        int permitArequest = Integer.parseInt(args[0]);
        int permitBrequest = Integer.parseInt(args[1]);
        int permitCrequest = Integer.parseInt(args[2]);
        int maxVolsInShift = Integer.parseInt(args[3]);
        int minVolsInShift = Integer.parseInt(args[4]);

        shifts = new Shift[21];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                shifts[i * 3 + j] = new Shift(Day.fromInt(i), Time.fromInt(j));
            }
        }

        CSVReader reader = null;         // initiates SCVreader
        try {
            // reads SCV file
            reader = new CSVReader(new FileReader("C:\\\\Users\\\\Gilad-Laptop\\\\Desktop/test1.csv"));
            boolean header = false;
            String[] line;
            while ((line = reader.readNext()) != null) {
                if(header == false) {
                    header = true;
                    continue;
                }
                String ID = line[0];
                String name = line[1];
                String address = line[2];
                String dayString = line[3];
                String timeString = line[4];
                String permitAString = line[5];
                String permitBString = line[6];
                String permitCString = line[7];

                System.out.println("ID: "+ ID + ", Name: " + name + ", Address: " + address + " , Day: " + dayString + " , Shift Time: " + timeString + " , permitA: " + permitAString + " , permitB: " + permitBString + " , permitC: " + permitCString + "]");

                boolean permitA = Boolean.parseBoolean(permitAString);
                boolean permitB = Boolean.parseBoolean(permitBString);
                boolean permitC = Boolean.parseBoolean(permitCString);

                Volunteer volunteer = volunteers.get(ID);
                if(volunteer == null)
                {
                    volunteer = new Volunteer(ID, name, address, permitA, permitB, permitC);
                    volunteers.put(ID, volunteer);
                }

                shifts[getArrayIndex(Day.fromString(dayString), Time.fromString(timeString))].addVolunteer(volunteer);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Shift potentialShift = findSmallestShift();
        do {
            potentialShift.findBestCombination(permitArequest, permitBrequest, permitCrequest, maxVolsInShift, minVolsInShift);
            int shiftIndex = getArrayIndex(potentialShift);
            if (shiftIndex > 0 && shifts[shiftIndex].getVolunteers().size() > 2) {
                shifts[shiftIndex - 1].getVolunteers().removeAll(shifts[shiftIndex].getVolunteers().subList(1, shifts[shiftIndex].getVolunteers().size()-1));
            }
            if (shiftIndex < shifts.length - 1 && shifts[shiftIndex].getVolunteers().size() > 2) {
                shifts[shiftIndex + 1].getVolunteers().removeAll(shifts[shiftIndex].getVolunteers().subList(1, shifts[shiftIndex].getVolunteers().size()-1));
            }
            potentialShift = findSmallestShift();
        } while (potentialShift!=null);

        for (int i = 0; i < 21; i++) {
            System.out.println(shifts[i]);
        }

        System.exit(0);

    }


    public static int getArrayIndex(Day day, Time time) {
        int arrayIndex = 0;
        arrayIndex = day.intValue()*3 + time.intValue();
        return arrayIndex;
    }

    public static int getArrayIndex(Shift shift){
        return getArrayIndex(shift.day, shift.time);
    }

    private static Shift findSmallestShift() {
        List<Pair<Integer, Integer>> potentialShifts = new ArrayList<>();
        for (int i = 0; i < shifts.length; i++) {
            if (!shifts[i].isProcessed()) {
                potentialShifts.add(new Pair<>(i, shifts[i].getVolunteers().size()));
            }
        }
        if (potentialShifts.isEmpty()) {
            return null;
        }
        potentialShifts.sort(shiftSizeComparator);
        return shifts[potentialShifts.get(0).getKey()];
    }

}
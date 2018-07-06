import com.google.common.collect.Sets;
import javafx.util.Pair;

import java.util.*;

public class Shift {

    Day day;
    Time time;
    private boolean processed = false;
    private List<Volunteer> volunteers = new ArrayList<>();
    private long driveTime;

    Shift(Day day, Time time) {
        this.day = day;
        this.time = time;
    }

    void addVolunteer(Volunteer volunteer) {
        volunteers.add(volunteer);

    }

    List<Volunteer> getVolunteers() {
        return volunteers;
    }

    void findBestCombination(int permitARequest, int permitBRequest, int permitCRequest, int maxVolsInShift, int minVolsInShift) {
        Set<Set<Volunteer>> combinations = new HashSet<>();
        if (this.volunteers.size() >= minVolsInShift) {
            if (maxVolsInShift < this.volunteers.size()) {
                combinations.addAll(Sets.combinations(new HashSet(volunteers), Math.min(maxVolsInShift, this.volunteers.size())));
            } else { // maxVols >= thi.svolunteer.size
                combinations.add(new HashSet<>(volunteers));
            }
        }
        combinations.removeIf(combination -> !validateCombination(combination, permitARequest, permitBRequest, permitCRequest));

        if (combinations.isEmpty()) {
            this.volunteers.clear();
        } else {
            List<Volunteer> selectedPermutation = null;
            long selectedCombinationDriveTime = Integer.MAX_VALUE;
            for (Set<Volunteer> combination : combinations) {
                Pair<Long, List<Volunteer>> permutationTime = findCombinationLowestDriveTime(combination);
                if (permutationTime.getKey() < selectedCombinationDriveTime) {
                    selectedCombinationDriveTime = permutationTime.getKey();
                    selectedPermutation = permutationTime.getValue();
                }
            }
            this.driveTime = selectedCombinationDriveTime;
            this.volunteers = selectedPermutation;
        }

        this.processed = true;
    }

    private Pair<Long, List<Volunteer>> findCombinationLowestDriveTime(Set<Volunteer> combination) {
        List<Volunteer> list = new ArrayList(combination);
        Permutations permutations = new Permutations(list);
        List<List<Volunteer>> allPermutations = permutations.permute();
        long selectedPermutationDriveTime = Integer.MAX_VALUE;
        List<Volunteer> selectedPermutation = null;
        for (List<Volunteer> permutation : allPermutations) {
            permutation.add(0, Volunteer.policeStation);
            permutation.add(Volunteer.policeStation);
            long driveTime = calculatePermutationDriveTime(permutation);
            if (driveTime < selectedPermutationDriveTime) {
                selectedPermutation = permutation;
                selectedPermutationDriveTime = driveTime;
            }
        }
        return new Pair<>(selectedPermutationDriveTime, selectedPermutation);
    }

    private long calculatePermutationDriveTime(List<Volunteer> permutation) {
        long permutationDriveTime=0;
        for (int i = 0; i < permutation.size()-1; i++)
        {
            long coupleTime = DriveDurationCache.getInstance().getDriveDuration(permutation.get(i), permutation.get(i+1));
            if(coupleTime == -1)
            {
                return Integer.MAX_VALUE;
            }
            permutationDriveTime = permutationDriveTime + coupleTime;
        }
        return permutationDriveTime;
    }

    private boolean validateCombination(Collection<Volunteer> combination, int permitArequest, int permitBrequest, int permitCrequest) {
        int permitAcounter = 0;
        int permitBcounter = 0;
        int permitCcounter = 0;

        Iterator<Volunteer> it = combination.iterator();

        while (it.hasNext()) {
            Volunteer volunteer = it.next();
            if (volunteer.permitA)
                permitAcounter++;

            if (volunteer.permitB)
                permitBcounter++;

            if (volunteer.permitC)
                permitCcounter++;
        }

        if (permitAcounter >= permitArequest && permitBcounter >= permitBrequest && permitCcounter >= permitCrequest) {
            return true;
        } else {
            return false;
        }
    }

    public int shiftSize() {
        //to sort the shifts from the smallest size to the largest
        return this.volunteers.size();
    }

    boolean isProcessed() {
        return processed;
    }

    @Override
    public String toString() {
        return day +
                " " + time +
                ", volunteers=" + volunteers +
                ", driveTime=" + driveTime +
                '}';
    }
}

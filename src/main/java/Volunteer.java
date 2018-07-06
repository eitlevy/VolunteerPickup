import java.util.List;
import java.util.Objects;

/**
 * Created by Gilad-Laptop on 5/4/2018.
 */

public class Volunteer {
    public static Volunteer policeStation = new Volunteer("101", "Police Station", "Engel 2 st, Tel Aviv", false, false, false);

    String ID;
    String name;
    String address;
    boolean permitA;
    boolean permitB;
    boolean permitC;

    public Volunteer(String ID, String name, String address, boolean permitA, boolean permitB, boolean permitC) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.permitA = permitA;
        this.permitB = permitB;
        this.permitC = permitC;
    }

    public boolean isPermitA() {
        return permitA;
    }

    public boolean isPermitB() {
        return permitB;
    }

    public boolean isPermitC() {
        return permitC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return Objects.equals(ID, volunteer.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

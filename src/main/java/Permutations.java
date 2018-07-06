import java.util.ArrayList;
import java.util.List;

public class Permutations<T> {
    private List<T> items;
    private List<List<T>> permutations = new ArrayList<>();

    public Permutations(List<T> data) {
        items = data;
    }

    private void _permute(List<T> permutation, List<T> data) {
        if (data.size() <= 0) {
            permutations.add(permutation);
            return;
        }

        for ( T datum : data ) {
            List<T> remnants = new ArrayList<>(data);
            remnants.remove(datum);
            List<T> elements = new ArrayList<>(permutation);
            elements.add(datum);
            _permute(elements, remnants);
        }
    }

    public List<List<T>> permute() {
        List<T> permutation = new ArrayList<T>();
        _permute(permutation, items);
        return permutations;
    }

}
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;

import java.io.IOException;

public class DriveDurationCache {

    private static DriveDurationCache driveDurationCache;
    private Table<Volunteer, Volunteer, Long> cache = HashBasedTable.create();
    private GeoApiContext geoApiContext;

    public static void init(String apiKey) {
        driveDurationCache = new DriveDurationCache(apiKey);
    }

    public static DriveDurationCache getInstance() {
        return driveDurationCache;
    }

    private DriveDurationCache(String apiKey) {
        geoApiContext = new GeoApiContext.Builder().apiKey(apiKey).build();
    }

    /**
     * @param origin the origin volunteer
     * @param destination the destination volunteer
     * @return duration of driving in seconds or -1 if could not get duration
     */
    long getDriveDuration(Volunteer origin, Volunteer destination)
    {
        Long duration = cache.get(origin, destination);
        if (duration == null) {
            String[] origins = {origin.address};
            String[] destinations = {destination.address};
            DistanceMatrixApiRequest matrixApiRequest = DistanceMatrixApi.getDistanceMatrix(geoApiContext, origins, destinations);
            DistanceMatrix matrix;
            try {
                matrix = matrixApiRequest.await();
            } catch (ApiException | InterruptedException | IOException e) {
                e.printStackTrace();
                return -1;
            }
            duration = matrix.rows[0].elements[0].duration.inSeconds;
            cache.put(origin, destination, duration);
        }
        return duration;
    }

}

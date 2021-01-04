package com.taxi.taxi.util;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsApiConnector {
    private GeoApiContext context;

    public GoogleMapsApiConnector() {
        context = new GeoApiContext
                .Builder()
                .apiKey("google_api_key")
                .build();
    }

    public GeoApiContext getContext() {
        return context;
    }

    public DistanceMatrix getDistanceMatrix(LatLng origin, LatLng... destinations) {
        DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(getContext());
        DistanceMatrix distanceMatrix = request
                .mode(TravelMode.DRIVING)
                .origins(origin)
                .destinations(destinations)
                .language("pl")
                .awaitIgnoreError();

        return distanceMatrix;
    }

}

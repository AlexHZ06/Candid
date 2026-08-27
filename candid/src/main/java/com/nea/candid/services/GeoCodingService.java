package com.nea.candid.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class GeoCodingService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;


    public GeoCodingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public double[] getCoordinates(String address) throws Exception {

        String encodedAddress =
                URLEncoder.encode(address, StandardCharsets.UTF_8);

        String url =
                "https://nominatim.openstreetmap.org/search"
                        + "?q=" + encodedAddress
                        + "&format=jsonv2"
                        + "&limit=1";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "YourNEAProject/1.0")
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        JsonNode results =
                objectMapper.readTree(response.body());

        if (results.isEmpty()) {
            throw new RuntimeException("Address not found");
        }

        double latitude =
                results.get(0).get("lat").asDouble();

        double longitude =
                results.get(0).get("lon").asDouble();

        return new double[]{latitude, longitude};
    }

}

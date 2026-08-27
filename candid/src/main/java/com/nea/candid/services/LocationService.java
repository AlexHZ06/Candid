package com.nea.candid.services;

import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.repositories.ProfilesTableRepo;
import com.nea.candid.services.database.UsersDbService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final ProfilesTableRepo profilesTableRepo;
    private final UsersDbService usersTableService;

    public LocationService(ProfilesTableRepo profilesTableRepo, UsersDbService usersTableService) {
        this.profilesTableRepo = profilesTableRepo;
        this.usersTableService = usersTableService;
    }

    public List<Long> calculateDistanceBounds(long profileid, float radius){

       ProfilesTableEntity profile = profilesTableRepo.getProfileById(profileid);

        double differenceLat = radius/ 111.32;
        double differenceLon = radius/ 111.32 * Math.cos(profile.getLongitude());

        double maxLat = profile.getLatitude() +  differenceLat;
        double minLat = profile.getLatitude() - differenceLat;
        double maxLon = profile.getLongitude() +  differenceLon;
        double minLon = profile.getLongitude() - differenceLon;

        return usersTableService.getPhotographerByDistanceBounds(maxLat, minLat, maxLon, minLon);

    }

}

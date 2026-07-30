package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.RefreshTokenTableEntity;
import com.nea.candid.repositories.RefreshTokenTableRepo;
import org.springframework.stereotype.Service;

import java.sql.Ref;

@Service
public class RefreshTokenTableService {

    private final RefreshTokenTableRepo repository;

    public RefreshTokenTableService(RefreshTokenTableRepo repository) {
        this.repository = repository;
    }

    public void saveRefreshToken(RefreshTokenTableEntity token) {

        repository.saveRefreshToken(token);

    }

    public RefreshTokenTableEntity getByToken(String jwtToken) {

        return repository.getByToken(jwtToken);

    }

    public void deleteTokenByUserId(long userId) {

        repository.deleteTokenByUserId(userId);

    }

    public RefreshTokenTableEntity getTokenByUserId(long userId) {

        return repository.getByUserId(userId);

    }

}

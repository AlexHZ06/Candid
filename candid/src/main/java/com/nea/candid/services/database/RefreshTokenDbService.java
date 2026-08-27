package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.RefreshTokenTableEntity;
import com.nea.candid.repositories.RefreshTokenTableRepo;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenDbService {

    private final RefreshTokenTableRepo repository;

    public RefreshTokenDbService(RefreshTokenTableRepo repository) {
        this.repository = repository;
    }

    public String saveRefreshToken(String jwtToken, LocalDateTime expiresAt, LocalDateTime issuedAt, long userId) {

        String uuid = UUID.randomUUID().toString();
        boolean exitLoop = false;

        while (!exitLoop) {

            try{

                return repository.saveRefreshToken(jwtToken, expiresAt, issuedAt, userId, uuid);

            }
            catch (DuplicateKeyException e){

                uuid = UUID.randomUUID().toString();

            }
            catch (Exception e){

                exitLoop = true;

            }
        }

        throw new RuntimeException("Refresh token was not saved");

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

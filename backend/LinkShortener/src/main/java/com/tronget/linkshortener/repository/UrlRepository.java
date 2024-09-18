package com.tronget.linkshortener.repository;

import com.tronget.linkshortener.model.UrlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByUrl(String url);
    Optional<UrlEntity> findByHashedUrl(String HashedUrl);
}

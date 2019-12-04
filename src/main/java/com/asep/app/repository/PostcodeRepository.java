package com.asep.app.repository;

import com.asep.app.entity.Postcode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostcodeRepository extends CrudRepository<Postcode, String> {
    Postcode findByPostcode(String postcode);

    List<Postcode> findByPostcodeNotNull(Pageable pageable);

    boolean existsByPostcode(String postcode);
}

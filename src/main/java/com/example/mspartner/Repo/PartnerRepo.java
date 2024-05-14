package com.example.mspartner.Repo;

import com.example.mspartner.Model.Partner;
import com.example.mspartner.Model.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepo extends JpaRepository<Partner,Long> {

    Page <Partner> findByPartnerLevelIsNotNull(Pageable pageable);
    Page <Partner> findByPartnerLevelIsNull(Pageable pageable);
    List<Partner> findByDisplayIsTrueAndPartnerLevelIsNotNull();
    Page<Partner> findByPartnerLevel_NameIgnoreCase(String levelName, Pageable pageable);


}

package com.example.mspartner.Repo;


import com.example.mspartner.Model.PartnerLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerLevelRepo extends JpaRepository<PartnerLevel,Long> {

}

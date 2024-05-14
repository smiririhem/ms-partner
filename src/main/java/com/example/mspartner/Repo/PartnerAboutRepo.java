package com.example.mspartner.Repo;

import com.example.mspartner.Model.PartnerAbout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerAboutRepo extends JpaRepository<PartnerAbout,Long> {
}

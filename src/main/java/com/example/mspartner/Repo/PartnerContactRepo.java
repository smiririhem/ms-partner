package com.example.mspartner.Repo;


import com.example.mspartner.Model.PartnerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerContactRepo extends JpaRepository<PartnerContact,Long> {
    boolean existsByEmail(String email);
}

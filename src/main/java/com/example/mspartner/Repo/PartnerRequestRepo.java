package com.example.mspartner.Repo;
import com.example.mspartner.Model.PartnerRequest;
import com.example.mspartner.Model.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRequestRepo extends JpaRepository<PartnerRequest,Long> {
}

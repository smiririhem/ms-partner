package com.example.mspartner.Repo;

import com.example.mspartner.Model.PartnerSolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PartnerSolutionRepo extends JpaRepository<PartnerSolution,Long> {


}

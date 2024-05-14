package com.example.mspartner.Service;

import com.example.mspartner.Model.PartnerLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerLevelService {

    PartnerLevel createPartnerLevel(PartnerLevel partnerLevel);
    List<PartnerLevel> findAllAll();
    void deleteById(Long id);
    PartnerLevel updatePartnerLevel(PartnerLevel partnerLevel , Long id );
    PartnerLevel getPartnerLevelById(Long id);
    void deletePartnerLevel(List<Long> partnerLevelIds);



}

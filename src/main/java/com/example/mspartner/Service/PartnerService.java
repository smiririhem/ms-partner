package com.example.mspartner.Service;

import com.example.mspartner.Model.Partner;
import com.example.mspartner.Model.PartnerLevel;
import com.example.mspartner.Model.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PartnerService {
    Partner createPartner(Partner partner);
     List<Partner> getAllPartners();
    Page <Partner> findPartnerrequest(Pageable pageable);
    Page <Partner> findPartnerrequestByState(Pageable pageable, State state);
    Partner getPartnerById(Long id);
    Partner updatePartnerLevelAttribute(Long partnerId, Long idLevel);
     void updateStateAttribute(Long partnerId, State state) ;
    Page<Partner> findPartnerByPartnerLevel(Pageable pageable, String level);
    List<Partner> getPartnersWithDisplayAndNonNullPartnerLevel();
    Partner updateDisplay(Long partnerId, Boolean display);
     void DeletPartner (Long partnerId);
     void deletePartnerMultiple(List<Long> partnerIds) ;
    public void uploadFiles(MultipartFile[] files, String UPLOAD_DIR) throws IOException;
    public Partner updatePartner(Long partnerId, Partner updatedPartner);
    public Page<Partner> findPartner(Pageable pageable) ;


}

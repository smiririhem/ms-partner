package com.example.mspartner.Service;


import com.example.mspartner.Model.PartnerSolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PartnerSolutionService {

    PartnerSolution createPartnerSolution(PartnerSolution partnerSolution);
    List<PartnerSolution> findAll();
    void deleteById(Long id);
    PartnerSolution updatePartnerSolution(PartnerSolution partnerSolution , Long id);
    PartnerSolution getPartnerSolutionById(Long id);
    void deletePartnerSolution(List<Long> partnerSolutionIds);
    public void uploadFiles(MultipartFile[] files, String UPLOAD_DIR) throws IOException ;

}

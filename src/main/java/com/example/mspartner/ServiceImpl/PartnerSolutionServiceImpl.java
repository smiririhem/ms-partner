package com.example.mspartner.ServiceImpl;

import com.example.mspartner.Model.PartnerSolution;
import com.example.mspartner.Repo.PartnerSolutionRepo;
import com.example.mspartner.Service.PartnerSolutionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PartnerSolutionServiceImpl implements PartnerSolutionService {
    @Autowired
    private PartnerSolutionRepo partnerSolutionRepo;

    @Override
    public   PartnerSolution createPartnerSolution(PartnerSolution partnerSolution){
        return partnerSolutionRepo.save(partnerSolution);
    }

    @Override
    public List<PartnerSolution> findAll(){
        return partnerSolutionRepo.findAll();
    }

    @Override
    public void deleteById(Long id){
        partnerSolutionRepo.deleteById(id);
    }

    @Override
    public PartnerSolution updatePartnerSolution(PartnerSolution partnerSolution , Long id){
     PartnerSolution existing = partnerSolutionRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(partnerSolution.getName());
            existing.setDescription(partnerSolution.getDescription());
            existing.setLogo(partnerSolution.getLogo());
            return partnerSolutionRepo.save(existing);
        } else {
            throw new RuntimeException("Contact not found with ID: " + id);
        }
    }



    @Override
    public PartnerSolution getPartnerSolutionById(Long id) {
        Optional<PartnerSolution> partnerLevelOptional = partnerSolutionRepo.findById(id);
        if (partnerLevelOptional.isPresent()) {
            return partnerLevelOptional.get();
        } else {
            return null;
        }
    }


@Override
    public void deletePartnerSolution(List<Long> partnerSolutionIds){
    for (long id:partnerSolutionIds){
        partnerSolutionRepo.deleteById(id);
    }
}

    public void uploadFiles(MultipartFile[] files, String downloadDir) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("One or more selected files are empty.");
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(downloadDir  + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }


}

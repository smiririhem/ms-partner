package com.example.mspartner.ServiceImpl;

import com.example.mspartner.Model.PartnerLevel;
import com.example.mspartner.Repo.PartnerLevelRepo;
import com.example.mspartner.Service.PartnerLevelService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerLevelServiceImpl implements PartnerLevelService {
    @Autowired
    private PartnerLevelRepo partnerLevelRepo;


    @Override
    public   PartnerLevel createPartnerLevel(PartnerLevel partnerLevel)
    {

        return partnerLevelRepo.save(partnerLevel);
    }

    @Override
    public List<PartnerLevel> findAllAll(){
        return partnerLevelRepo.findAll();
    }
    @Override
    public void deleteById(Long id){
        partnerLevelRepo.deleteById(id);
    }

    @Override
    public PartnerLevel updatePartnerLevel(PartnerLevel partnerLevel , Long id  ){
        PartnerLevel existing = partnerLevelRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(partnerLevel.getName());
            existing.setDescription(partnerLevel.getDescription());
            existing.setColor(partnerLevel.getColor());

            return partnerLevelRepo.save(existing);
        } else {
            throw new RuntimeException("Contact not found with ID: " + id);
        }
    }



    @Override
    public PartnerLevel getPartnerLevelById(Long id) {
        Optional<PartnerLevel> partnerLevelOptional = partnerLevelRepo.findById(id);
        if (partnerLevelOptional.isPresent()) {
            return partnerLevelOptional.get();
        } else {
            return null;
        }
    }

    @Transactional
    public void deletePartnerLevel(List<Long> partnerLevelIds) {
        for (Long Id : partnerLevelIds) {
            partnerLevelRepo.deleteById(Id);
        }
    }



}

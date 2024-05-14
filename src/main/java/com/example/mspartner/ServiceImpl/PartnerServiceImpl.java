package com.example.mspartner.ServiceImpl;

import com.example.mspartner.Model.*;
import com.example.mspartner.Repo.*;
import com.example.mspartner.Service.PartnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerRepo partnerRepo;

    @Autowired
    private PartnerLevelRepo partnerLevelRepo;

    @Autowired
    private PartnerContactRepo partnerContactRepo;

    @Autowired
    private PartnerRequestRepo partnerRequestRepo;

    @Autowired
    private PartnerAboutRepo partnerAboutRepo;



    @Override
    public Partner createPartner(Partner partner){
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //partnercontact
        PartnerContact partnerContact = partner.getPartnerContact();
        partnerContact.setPartner(partner);
        //partnerabout
        PartnerAbout partnerAbout = partner.getPartnerAbout();
        partnerAbout.setPartner(partner);
        //partnerrequest
        PartnerRequest partnerRequest = partner.getPartnerRequest();
        partnerRequest.setDate(formattedDateTime);
        partnerRequest.setState(State.New);

        partnerRequest.setPartner(partner);
//display
        partner.setDisplay(false);
//date

        partner.setDate(formattedDateTime);


// Assuming you have an instance of Partner class named 'partner'
// Call setPartnerRequest() method with both partnerRequest and partner
        return partnerRepo.save(partner);

    }






    @Override
    public Partner getPartnerById(Long id){
        Optional<Partner> partnerOptional = partnerRepo.findById(id);
        return partnerOptional.orElse(null);
    }

    @Override
    public Partner updatePartnerLevelAttribute(Long partnerId, Long idLevel) {
        Partner partner = partnerRepo.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("partner not found"));
        PartnerLevel level = partnerLevelRepo.findById(idLevel)
                .orElseThrow(() -> new IllegalArgumentException("partner level not found"));
        partner.setPartnerLevel(level);
        return partnerRepo.save(partner);
    }
    @Override
    public void updateStateAttribute(Long partnerId, State state) {
        Partner partner = partnerRepo.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("partner not found"));

        PartnerRequest partnerRequest = partner.getPartnerRequest();
        partnerRequest.setState(state);

        partnerRepo.save(partner);
    }





    @Override
    public Partner updateDisplay(Long partnerId, Boolean display) {
        Partner partner = partnerRepo.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("partner not found"));

          partner.setDisplay(display);
          return partnerRepo.save(partner);
    }

    @Override
    public void DeletPartner (Long partnerId) {
        partnerRepo.deleteById(partnerId);
    }
    @Override
    public void deletePartnerMultiple(List<Long> partnerIds) {
        List<Partner> partners = partnerRepo.findAllById(partnerIds);
        partnerRepo.deleteAll(partners);
    }

    public void uploadFiles(MultipartFile[] files, String UPLOAD_DIR) throws IOException {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("One or more selected files are empty.");
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR  + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }
    @Override
    public Partner updatePartner(Long partnerId, Partner updatedPartner) {
        Optional<Partner> optionalPartner = partnerRepo.findById(partnerId);
        if (optionalPartner.isPresent()) {
            Partner existingPartner = optionalPartner.get();

            // Mise à jour des informations du partenaire
            existingPartner.setCompanyName(updatedPartner.getCompanyName());
            existingPartner.setCompanySize(updatedPartner.getCompanySize());
            existingPartner.setCountry(updatedPartner.getCountry());
            existingPartner.setCity(updatedPartner.getCity());
            existingPartner.setZipCode(updatedPartner.getZipCode());
            existingPartner.setActivityArea(updatedPartner.getActivityArea());
            existingPartner.setPartnerLevel(updatedPartner.getPartnerLevel());

            // Mise à jour des informations de contact du partenaire
            PartnerContact updatedPartnerContact = updatedPartner.getPartnerContact();
            PartnerContact existingPartnerContact = existingPartner.getPartnerContact();
            existingPartnerContact.setFirstName(updatedPartnerContact.getFirstName());
            existingPartnerContact.setLastName(updatedPartnerContact.getLastName());
            existingPartnerContact.setEmail(updatedPartnerContact.getEmail());
            existingPartnerContact.setPhone(updatedPartnerContact.getPhone());
            existingPartnerContact.setJobTitle(updatedPartnerContact.getJobTitle());
            existingPartnerContact.setPartner(existingPartner);

            // Mise à jour des informations sur le partenaire
            PartnerAbout updatedPartnerAbout = updatedPartner.getPartnerAbout();
            PartnerAbout existingPartnerAbout = existingPartner.getPartnerAbout();
            existingPartnerAbout.setCumulusExperience(updatedPartnerAbout.getCumulusExperience());
            existingPartnerAbout.setOtherPartners(updatedPartnerAbout.getOtherPartners());
            existingPartnerAbout.setReasons(updatedPartnerAbout.getReasons());
            existingPartnerAbout.setLogo(updatedPartnerAbout.getLogo());
            existingPartnerAbout.setFile(updatedPartnerAbout.getFile());
            existingPartnerAbout.setPartner(existingPartner);

            return partnerRepo.save(existingPartner);
        } else {
            throw new RuntimeException("Partenaire non trouvé avec l'ID : " + partnerId);
        }
    }


    //////////////////////////////////partner request page
    @Override
    public Page<Partner> findPartnerrequest(Pageable pageable) {
        List<Partner> allPartners = partnerRepo.findByPartnerLevelIsNull(pageable).getContent();
        List<Partner> filteredPartners = new ArrayList<>();

        for (Partner partner : allPartners) {
            if (partner.getPartnerRequest().getState() != State.archived){
                filteredPartners.add(partner);
            }
        }

        return new PageImpl<>(filteredPartners, pageable, filteredPartners.size());
    }
    @Override
    public Page<Partner> findPartner(Pageable pageable) {
        List<Partner> allPartners = partnerRepo.findByPartnerLevelIsNotNull(pageable).getContent();


        return new PageImpl<>(allPartners, pageable, allPartners.size());
    }


    //////////////////////////////////////////pour le site
    @Override
    public List<Partner> getPartnersWithDisplayAndNonNullPartnerLevel() {
        return partnerRepo.findByDisplayIsTrueAndPartnerLevelIsNotNull();
    }
    /////////filtre dans paetner liste
    @Override
    public Page<Partner> findPartnerByPartnerLevel(Pageable pageable, String level) {
        return partnerRepo.findByPartnerLevel_NameIgnoreCase(level, pageable);
    }
///////filtre dans oartner request
    @Override
    public Page<Partner> findPartnerrequestByState(Pageable pageable, State state) {
        List<Partner> partnersWithState = new ArrayList<>();

        Page<Partner> partnersPage = partnerRepo.findByPartnerLevelIsNull(pageable);
        for (Partner partner : partnersPage.getContent()) {
            PartnerRequest partnerRequest = partner.getPartnerRequest();
            if (partnerRequest != null && partnerRequest.getState() == state) {
                partnersWithState.add(partner);
            }
        }

        return new PageImpl<>(partnersWithState, pageable, partnersWithState.size());
    }
    ///////////////

    ////////////test
    public List<Partner> getAllPartners() {
        return partnerRepo.findAll();
    }





}

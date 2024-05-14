package com.example.mspartner.Controller;

import com.example.mspartner.Model.Partner;
import com.example.mspartner.Model.State;
import com.example.mspartner.Service.PartnerService;

import com.example.mspartner.ServiceImpl.RabbitMQProducer;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/partners")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "Keycloak")
public class PartnerController {
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private RabbitMQProducer producer ;

    @Value("${nfs.upload.dir}")
    private String uploadDir;


    /***************************************partner request 'site '***************************************************/
    @PermitAll
    @PostMapping("/create")
    public ResponseEntity<Partner> create_partner(@RequestBody Partner partner){
        try {

            partnerService.createPartner(partner);
            producer.sendMessage(partner.getId(), partner.getCompanyName());

            return new ResponseEntity<>(partner, HttpStatus.CREATED);
        }catch(Exception e){
            return  new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***************************************partner Admin***************************************************/

    @GetMapping("/{id}")
    public ResponseEntity<?> getPartnerById(@PathVariable Long id) {
        Partner partner = partnerService.getPartnerById(id);
        if (partner != null) {
            return new ResponseEntity<>(partner, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Partner not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("updatelevel/{partnerId}/{levelId}")
    public ResponseEntity<?>  changePartnerlevel(@PathVariable Long partnerId, @PathVariable Long levelId) {
        try {
            Partner updated=    partnerService.updatePartnerLevelAttribute(partnerId, levelId);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateStateAttribute")
    public ResponseEntity<?> updateStateAttribute(
            @RequestParam Long partnerId,
            @RequestParam State state) {
        try {
            partnerService.updateStateAttribute(partnerId, state);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the state attribute.");
        }
    }

    @PutMapping("/updatedisplay")
    public ResponseEntity<?> updateDisplay(
            @RequestParam Long partnerId,
            @RequestParam Boolean display) {
        try {
            Partner updated = partnerService.updateDisplay(partnerId, display);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/partner/{partnerId}")
    public ResponseEntity<?> deletePartner(@PathVariable Long partnerId) {
        try {
            partnerService.DeletPartner(partnerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur s'est produite lors de la suppression du partenaire.");
        }
    }

    @DeleteMapping("/partners/delete-multiple")
    public ResponseEntity<?> deleteMultiplePartners(@RequestBody List<Long> partnerIds) {
        try {
            partnerService.deletePartnerMultiple(partnerIds);
            return ResponseEntity.ok("Partners deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or more partners not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting partners");
        }
    }

    @PermitAll
    @PostMapping("/upload")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile[] files) {


        try {
            partnerService.uploadFiles(files, uploadDir);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    ////////////////////////////////////////////////////////////////
    @GetMapping("/Allpartners")
    public ResponseEntity<List<Partner>> getAllPartners() {
        List<Partner> partners = partnerService.getAllPartners();
        return new ResponseEntity<>(partners, HttpStatus.OK);
    }


    @GetMapping("/partnerrequest")
    public ResponseEntity<Map<String, Object>> getPartnerRequest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Partner> partnerPage = partnerService.findPartnerrequest(paging);
            List<Partner> partners = partnerPage.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("partners", partners);
            response.put("currentPage", partnerPage.getNumber());
            response.put("totalItems", partnerPage.getTotalElements());
            response.put("totalPages", partnerPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/partnerlist")
    public ResponseEntity<Map<String, Object>> getpartnerlist(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Partner> partnerPage = partnerService.findPartner(paging);
            List<Partner> partners = partnerPage.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("partners", partners);
            response.put("currentPage", partnerPage.getNumber());
            response.put("totalItems", partnerPage.getTotalElements());
            response.put("totalPages", partnerPage.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //pour site
    @GetMapping("/display")
    public ResponseEntity<List<Partner>> getPartnersWithDisplayAndNonNullPartnerLevel() {
        List<Partner> partners = partnerService.getPartnersWithDisplayAndNonNullPartnerLevel();
        return ResponseEntity.ok(partners);
    }
    @GetMapping("/partnerByLevel")
    public ResponseEntity<Map<String, Object>> getPartnersByLevel(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String level
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Partner> partnerPage = partnerService.findPartnerByPartnerLevel(pageable, level);
            List<Partner> partners = partnerPage.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("partners", partners);
            response.put("currentPage", partnerPage.getNumber());
            response.put("totalItems", partnerPage.getTotalElements());
            response.put("totalPages", partnerPage.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/partnerByState")
    public ResponseEntity<Map<String, Object>> getPartnersByState(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam State state
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Partner> partnerPage = partnerService.findPartnerrequestByState(pageable, state);
            List<Partner> partners = partnerPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("partners", partners);
            response.put("currentPage", partnerPage.getNumber());
            response.put("totalItems", partnerPage.getTotalElements());
            response.put("totalPages", partnerPage.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable("id") Long id,
                                                 @RequestBody Partner updatedPartner) {
        try {
            Partner updated = partnerService.updatePartner(id, updatedPartner);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }




}

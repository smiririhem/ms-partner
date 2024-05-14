package com.example.mspartner.Controller;


import com.example.mspartner.Model.PartnerLevel;
import com.example.mspartner.Service.PartnerLevelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partner_level")
@CrossOrigin(origins = "*")

@SecurityRequirement(name = "Keycloak")
public class PartnerLevelController {
   @Autowired
    private PartnerLevelService partnerLevelService;



    @PostMapping("/create")
    public ResponseEntity<PartnerLevel> create(@RequestBody PartnerLevel partnerLevel){
        partnerLevelService.createPartnerLevel(partnerLevel);
        return new ResponseEntity<>(partnerLevel, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PartnerLevel>> display_all(){
        List<PartnerLevel> partnerLevels=partnerLevelService.findAllAll();
        if (partnerLevels.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(partnerLevels,HttpStatus.OK);
        }}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteId(@PathVariable Long id){
        try {
            partnerLevelService.deleteById(id);
            return new ResponseEntity<>("Partner level deleted successfully", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Failed to delete partner level", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PartnerLevel> updatePartnerLevel(@RequestBody PartnerLevel partnerLevel , @PathVariable Long id){
        try{
            PartnerLevel updated=  partnerLevelService.updatePartnerLevel(partnerLevel , id );
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getPartnerLevelById(@PathVariable Long id) {
        PartnerLevel partnerLevel = partnerLevelService.getPartnerLevelById(id);
        if (partnerLevel != null) {
            return new ResponseEntity<>(partnerLevel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Partner Level not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletePartnerLevels(@RequestBody List<Long> partnerLevelIds) {
        try {
            partnerLevelService.deletePartnerLevel(partnerLevelIds);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}

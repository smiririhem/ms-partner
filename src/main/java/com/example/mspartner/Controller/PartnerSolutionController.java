package com.example.mspartner.Controller;


import com.example.mspartner.Model.PartnerLevel;
import com.example.mspartner.Model.PartnerSolution;
import com.example.mspartner.Service.PartnerSolutionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("partner_solution")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "Keycloak")
public class PartnerSolutionController {
    @Autowired
    private PartnerSolutionService partnerSolutionService;
    @Value("${nfs.upload.dir}")
    private String uploadDir;



    @PostMapping("/create")
    public ResponseEntity<PartnerSolution> create(@RequestBody PartnerSolution partnerSolution){
        partnerSolutionService.createPartnerSolution(partnerSolution);
        return new ResponseEntity<>(partnerSolution, HttpStatus.CREATED);
    }
    @PermitAll
    @GetMapping("/all")
    public ResponseEntity<List<PartnerSolution>> findAll(){
        List<PartnerSolution> partnerSolutions=partnerSolutionService.findAll();
        if (partnerSolutions.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(partnerSolutions,HttpStatus.OK);
        }}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteId(@PathVariable Long id){
        try {
            partnerSolutionService.deleteById(id);
            return new ResponseEntity<>("Partner solution deleted successfully", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Failed to delete partner solution", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PartnerSolution> updatePartnerSolution
            (@RequestBody PartnerSolution partnerSolution , @PathVariable Long id){
        try{
            PartnerSolution updated=  partnerSolutionService.updatePartnerSolution(partnerSolution , id );
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
        PartnerSolution partnerSolution = partnerSolutionService.getPartnerSolutionById(id);
        if (partnerSolution != null) {
            return new ResponseEntity<>(partnerSolution, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Partner Solution not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/deletall")
    public ResponseEntity<?> deletePartnerSolution(@RequestBody List<Long> ids){
        try {
            partnerSolutionService.deletePartnerSolution(ids);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile[] files) {
        try {
            partnerSolutionService.uploadFiles(files, uploadDir);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

package com.example.mspartner.ServiceImplTest;

import com.example.mspartner.Model.*;
import com.example.mspartner.Repo.*;
import com.example.mspartner.ServiceImpl.PartnerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class PartnerServiceImplTest {
    @Mock
    private PartnerRepo partnerRepo;

    @Mock
    private PartnerLevelRepo partnerLevelRepo;

    @Mock
    private PartnerContactRepo partnerContactRepo;

    @Mock
    private PartnerRequestRepo partnerRequestRepo;

    @Mock
    private PartnerAboutRepo partnerAboutRepo;

    @InjectMocks
    private PartnerServiceImpl partnerService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void createPartner_ShouldReturnCreatedPartner() {
        Partner partner = new Partner();
        PartnerContact partnerContact = new PartnerContact();
        PartnerAbout partnerAbout = new PartnerAbout();
        PartnerRequest partnerRequest = new PartnerRequest();
        partner.setPartnerContact(partnerContact);
        partner.setPartnerAbout(partnerAbout);
        partner.setPartnerRequest(partnerRequest);
        when(partnerRepo.save(partner)).thenReturn(partner);
        Partner createdPartner = partnerService.createPartner(partner);
        assertNotNull(createdPartner);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String expectedDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals(expectedDate, createdPartner.getDate());
        verify(partnerRepo, times(1)).save(partner);
    }

    @Test
    void getPartnerById_ShouldReturnPartnerIfExists() {
        Long partnerId = 1L;
        Partner partner = new Partner();
        when(partnerRepo.findById(partnerId)).thenReturn(Optional.of(partner));
        Partner retrievedPartner = partnerService.getPartnerById(partnerId);
        assertNotNull(retrievedPartner);
        assertEquals(partner, retrievedPartner);
        verify(partnerRepo, times(1)).findById(partnerId);
    }

    @Test
    void updatePartnerLevelAttribute_ShouldUpdatePartnerLevel() {
        Long partnerId = 1L;
        Long levelId = 2L;
        Partner partner = new Partner();
        PartnerLevel level = new PartnerLevel();
        when(partnerRepo.findById(partnerId)).thenReturn(Optional.of(partner));
        when(partnerLevelRepo.findById(levelId)).thenReturn(Optional.of(level));
        partnerService.updatePartnerLevelAttribute(partnerId, levelId);
        assertEquals(level, partner.getPartnerLevel());
        verify(partnerRepo, times(1)).save(partner);
    }

    @Test
    void updateStateAttribute_ShouldUpdatePartnerRequestState() {
        Long partnerId = 1L;
        State state = State.archived;
        Partner partner = new Partner();
        PartnerRequest partnerRequest = new PartnerRequest();
        partner.setPartnerRequest(partnerRequest);
        when(partnerRepo.findById(partnerId)).thenReturn(Optional.of(partner));

        partnerService.updateStateAttribute(partnerId, state);

        assertEquals(state, partner.getPartnerRequest().getState());
        verify(partnerRepo, times(1)).save(partner);
    }

    @Test
    void updateDisplay_ShouldUpdatePartnerDisplay() {
        Long partnerId = 1L;
        Boolean display = true;
        Partner partner = new Partner();
        when(partnerRepo.findById(partnerId)).thenReturn(Optional.of(partner));
        partnerService.updateDisplay(partnerId, display);

        assertEquals(display, partner.isDisplay());
        verify(partnerRepo, times(1)).save(partner);
    }

    @Test
    void deletePartner_ShouldDeletePartner() {
        Long partnerId = 1L;
        Partner partner = new Partner();
        when(partnerRepo.findById(partnerId)).thenReturn(Optional.of(partner));

        partnerService.DeletPartner(partnerId);

        verify(partnerRepo, times(1)).deleteById(partnerId);
    }

    @Test
    void deletePartnerMultiple_ShouldDeleteMultiplePartners() {
        List<Long> partnerIds = Arrays.asList(1L, 2L, 3L);
        List<Partner> partners = Arrays.asList(new Partner(), new Partner(), new Partner());
        when(partnerRepo.findAllById(partnerIds)).thenReturn(partners);

        partnerService.deletePartnerMultiple(partnerIds);

        verify(partnerRepo, times(1)).deleteAll(partners);
    }


    @Test
    void uploadFiles_ShouldUploadFiles() throws IOException {
        String UPLOAD_DIR = "C:\\Users\\DELL\\Desktop\\Nouveau\\";
        byte[] content = "file content".getBytes();
        MultipartFile[] files = {new MockMultipartFile("file1.txt", "file1.txt", "text/plain", content),
                new MockMultipartFile("file2.txt", "file2.txt", "text/plain", content)};

        partnerService.uploadFiles(files, UPLOAD_DIR);

    }


    @Test
    void findPartnerrequest_ShouldReturnFilteredPartners() {
        PartnerRequest pr1 = new PartnerRequest();
        pr1.setState(State.New);
        PartnerRequest pr2 = new PartnerRequest();
        pr2.setState(State.archived);
        PartnerRequest pr3 = new PartnerRequest();
        pr3.setState(State.archived);
        Partner partner1 = new Partner();
        partner1.setPartnerRequest(pr1);
        Partner partner2 = new Partner();
        partner2.setPartnerRequest(pr2);
        Partner partner3 = new Partner();
        partner3.setPartnerRequest(pr3);
        List<Partner> allPartners = Arrays.asList(partner1, partner2, partner3);
        Pageable pageable = Pageable.unpaged();
        when(partnerRepo.findByPartnerLevelIsNull(pageable)).thenReturn(new PageImpl<>(allPartners));
        Page<Partner> result = partnerService.findPartnerrequest(pageable);
        assertEquals(1, result.getContent().size());
    }



    @Test
    void getPartnersWithDisplayAndNonNullPartnerLevel_ShouldReturnPartners() {
        List<Partner> expectedPartners = Arrays.asList(new Partner(), new Partner());
        when(partnerRepo.findByDisplayIsTrueAndPartnerLevelIsNotNull()).thenReturn(expectedPartners);

        List<Partner> result = partnerService.getPartnersWithDisplayAndNonNullPartnerLevel();

        assertEquals(expectedPartners.size(), result.size());
    }

    @Test
    void findPartnerByPartnerLevel_ShouldReturnPartners() {
        List<Partner> expectedPartners = Arrays.asList(new Partner(), new Partner());
        String level = "Level";
        Pageable pageable = Pageable.unpaged();
        when(partnerRepo.findByPartnerLevel_NameIgnoreCase(level, pageable)).thenReturn(new PageImpl<>(expectedPartners));

        Page<Partner> result = partnerService.findPartnerByPartnerLevel(pageable, level);

        assertEquals(expectedPartners.size(), result.getContent().size());
    }

    @Test
    void getPartnerBystate_ShouldReturnPartners() {
        PartnerRequest pr1 = new PartnerRequest();
        PartnerRequest pr2 = new PartnerRequest();
        PartnerRequest pr3 = new PartnerRequest();

        Partner p1 = new Partner();
        p1.setPartnerRequest(pr1);
        Partner p2 = new Partner();
        p2.setPartnerRequest(pr2);
        Partner p3 = new Partner();
        p3.setPartnerRequest(pr3);

        List<Partner> partnersWithState = Arrays.asList(p1, p2, p3);
        Pageable pageable = Pageable.unpaged();

        State state = State.archived;
        pr2.setState(state);
        pr3.setState(state);

        when(partnerRepo.findByPartnerLevelIsNull(pageable)).thenReturn(new PageImpl<>(partnersWithState));

        Page<Partner> result = partnerService.findPartnerrequestByState(pageable, state);

        assertEquals(2, result.getContent().size());
    }

    @Test
    void getAllPartners_ShouldReturnAllPartners() {
        List<Partner> expectedPartners = Arrays.asList(new Partner(), new Partner());
        when(partnerRepo.findAll()).thenReturn(expectedPartners);

        List<Partner> result = partnerService.getAllPartners();

        assertEquals(expectedPartners.size(), result.size());
    }

   @Test
    void updatePartner_ShouldUpdatePartner() {
        Long partnerId = 1L;
        Partner updatedPartner = new Partner();
        updatedPartner.setCompanyName("Updated Company Name");
        updatedPartner.setCompanySize("");
        updatedPartner.setCountry("Updated Country");
       updatedPartner.setActivityArea("Updated ActivityArea");
       updatedPartner.setCity("Updated city");
       updatedPartner.setZipCode("Updated zipcode");
       updatedPartner.setPartnerAbout(updatedPartner.getPartnerAbout());
       updatedPartner.setPartnerLevel(updatedPartner.getPartnerLevel());
       updatedPartner.setPartnerContact(updatedPartner.getPartnerContact());
        Partner existingPartner = new Partner();
        existingPartner.setId(partnerId);
        when(partnerRepo.findById(partnerId)).thenReturn(Optional.of(existingPartner));
       when(partnerRepo.save(any(Partner.class))).thenReturn(existingPartner);

        Partner result = partnerService.updatePartner(partnerId, updatedPartner);

        assertNotNull(result);
        assertEquals(updatedPartner.getCompanyName(), result.getCompanyName());
        assertEquals(updatedPartner.getCompanySize(), result.getCompanySize());
        assertEquals(updatedPartner.getCountry(), result.getCountry());
        verify(partnerRepo, times(1)).save(existingPartner);
    }

    @Test
    void updatePartner_ShouldThrowExceptionWhenPartnerNotFound() {
        Long partnerId = 1L;
        Partner updatedPartner = new Partner();
        updatedPartner.setCompanyName("Updated Company Name");

        when(partnerRepo.findById(partnerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> partnerService.updatePartner(partnerId, updatedPartner));
    }





}







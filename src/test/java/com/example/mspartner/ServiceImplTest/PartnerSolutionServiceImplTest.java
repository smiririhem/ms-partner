package com.example.mspartner.ServiceImplTest;

import com.example.mspartner.Model.PartnerLevel;
import com.example.mspartner.Model.PartnerSolution;
import com.example.mspartner.Repo.PartnerSolutionRepo;
import com.example.mspartner.ServiceImpl.PartnerSolutionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import java.util.*;
public class PartnerSolutionServiceImplTest {
    @Mock
    private PartnerSolutionRepo partnerSolutionRepo;

    @InjectMocks
    private PartnerSolutionServiceImpl partnerSolutionService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void createPartnerSolution() {
        PartnerSolution partnerSolution = new PartnerSolution();
        when(partnerSolutionRepo.save(partnerSolution)).thenReturn(partnerSolution);
        assertEquals(partnerSolution, partnerSolutionService.createPartnerSolution(partnerSolution));
    }

    @Test
    void findAll() {
        List<PartnerSolution> partnerSolutions = new ArrayList<>();
        when(partnerSolutionRepo.findAll()).thenReturn(partnerSolutions);
        assertEquals(partnerSolutions, partnerSolutionService.findAll());
    }

    @Test
    void deleteById() {
        doNothing().when(partnerSolutionRepo).deleteById(1L);
        assertDoesNotThrow(() -> partnerSolutionService.deleteById(1L));
    }

    @Test
    void updatePartnerSolution() {
        PartnerSolution existing = new PartnerSolution();
        existing.setId(1L);
        when(partnerSolutionRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(partnerSolutionRepo.save(any(PartnerSolution.class))).thenReturn(existing);
        PartnerSolution partnerSolution = new PartnerSolution();
        partnerSolution.setName("Updated Name");
        partnerSolution.setDescription("Updated Description");
        partnerSolution.setLogo("Updated Logo");
        assertEquals(existing, partnerSolutionService.updatePartnerSolution(partnerSolution, 1L));
    }

    @Test
    void getPartnerSolutionById() {
        PartnerSolution partnerSolution = new PartnerSolution();
        partnerSolution.setId(1L);
        when(partnerSolutionRepo.findById(1L)).thenReturn(Optional.of(partnerSolution));
        assertEquals(partnerSolution, partnerSolutionService.getPartnerSolutionById(1L));
    }

    @Test
    void deletePartnerSolution() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        doNothing().when(partnerSolutionRepo).deleteById(1L);
        doNothing().when(partnerSolutionRepo).deleteById(2L);
        assertDoesNotThrow(() -> partnerSolutionService.deletePartnerSolution(ids));
    }
    @Test
    void uploadFiles_ShouldUploadFiles() throws IOException {
        String UPLOAD_DIR = "C:\\Users\\DELL\\Desktop\\Nouveau\\";
        byte[] content = "file content".getBytes();
        MultipartFile[] files = {new MockMultipartFile("file1.txt", "file1.txt", "text/plain", content),
                new MockMultipartFile("file2.txt", "file2.txt", "text/plain", content)};

        partnerSolutionService.uploadFiles(files, UPLOAD_DIR);

    }

}

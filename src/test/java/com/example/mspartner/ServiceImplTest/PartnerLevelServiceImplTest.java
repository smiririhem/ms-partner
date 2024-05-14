package com.example.mspartner.ServiceImplTest;

import com.example.mspartner.Model.PartnerLevel;
import com.example.mspartner.Repo.PartnerLevelRepo;
import com.example.mspartner.ServiceImpl.PartnerLevelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;
public class PartnerLevelServiceImplTest {
    @Mock
    private PartnerLevelRepo partnerLevelRepo;

    @InjectMocks
    private PartnerLevelServiceImpl partnerLevelService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testCreatePartnerLevel() {
        PartnerLevel partnerLevel = new PartnerLevel();
        when(partnerLevelRepo.save(partnerLevel)).thenReturn(partnerLevel);

        PartnerLevel savedPartnerLevel = partnerLevelService.createPartnerLevel(partnerLevel);

        assertNotNull(savedPartnerLevel);
        assertEquals(partnerLevel, savedPartnerLevel);
        verify(partnerLevelRepo, times(1)).save(partnerLevel);
    }

    @Test
    public void testFindAllAll() {
        List<PartnerLevel> partnerLevels = Collections.emptyList();
        when(partnerLevelRepo.findAll()).thenReturn(partnerLevels);

        List<PartnerLevel> result = partnerLevelService.findAllAll();

        assertNotNull(result);
        assertEquals(partnerLevels, result);
        verify(partnerLevelRepo, times(1)).findAll();
    }
    @Test
    public void testDeleteById() {
        Long id = 1L;
        partnerLevelService.deleteById(id);
        verify(partnerLevelRepo, times(1)).deleteById(id);
    }

   @Test
    public void testUpdatePartnerLevel() {
        Long id = 1L;
        PartnerLevel existingPartnerLevel = new PartnerLevel();
        existingPartnerLevel.setId(id);
       when(partnerLevelRepo.findById(id)).thenReturn(Optional.of(existingPartnerLevel));

       when(partnerLevelRepo.save(any(PartnerLevel.class))).thenReturn(existingPartnerLevel);

        PartnerLevel updatedPartnerLevel = new PartnerLevel();
        updatedPartnerLevel.setName("Updated Name");
        updatedPartnerLevel.setDescription("Updated Description");
        updatedPartnerLevel.setColor("Updated Color");

        PartnerLevel result = partnerLevelService.updatePartnerLevel(updatedPartnerLevel, id);

        assertNotNull(result);
        assertEquals(existingPartnerLevel, result);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("Updated Color", result.getColor());
        verify(partnerLevelRepo, times(1)).save(existingPartnerLevel);
    }

    @Test
    public void testGetPartnerLevelById() {
        Long id = 1L;
        PartnerLevel partnerLevel = new PartnerLevel();
        when(partnerLevelRepo.findById(id)).thenReturn(Optional.of(partnerLevel));

        PartnerLevel result = partnerLevelService.getPartnerLevelById(id);

        assertNotNull(result);
        assertEquals(partnerLevel, result);
        verify(partnerLevelRepo, times(1)).findById(id);
    }


    @Test
    public void testDeletePartnerLevel() {
        List<Long> partnerLevelIds = Collections.singletonList(1L);
        partnerLevelService.deletePartnerLevel(partnerLevelIds);
        verify(partnerLevelRepo, times(1)).deleteById(1L);
    }
}

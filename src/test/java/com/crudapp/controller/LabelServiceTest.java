package com.crudapp.controller;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;
import com.crudapp.services.LabelService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelService labelService;

    private Label label;
    private final Long labelId = 1L;

    @BeforeEach
    public void setUpLabel() {
        label = Label.builder()
                .id(labelId)
                .name("label")
                .build();
    }

    @Test
    public void testGetLabelById_Found() throws NotFoundException {
        Mockito.when(labelRepository.findById(labelId)).thenReturn(label);
        Label resultLabel = labelService.getLabelByID(labelId);
        Assertions.assertEquals(label, resultLabel);
        Mockito.verify(labelRepository).findById(labelId);
    }

    @Test
    public void testGetLabelById_NotFound() {
        Mockito.when(labelRepository.findById(labelId)).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> labelService.getLabelByID(labelId));
    }

    @Test
    public void testCreateLabel() {
        Label savedLabel = Label.builder()
                .id(labelId)
                .name("label")
                .build();
        Mockito.when(labelRepository.save(label)).thenReturn(savedLabel);
        Label resultLabel = labelService.createLabel(label);
        Assertions.assertEquals(savedLabel, resultLabel);
        Mockito.verify(labelRepository).save(label);
    }

    @Test
    public void testUpdateLabel_Found() throws NotFoundException {
        Mockito.when(labelRepository.update(label)).thenReturn(label);

        Label resultLabel = labelService.updateLabel(label);
        Assertions.assertEquals(label, resultLabel);
        Mockito.verify(labelRepository).update(label);
    }

    @Test
    public void testUpdateLabel_NotFound() {
        Mockito.when(labelRepository.update(label)).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> labelService.updateLabel(label));
    }

    @Test
    public void tesGetAllLabels() {
        List<Label> labelList = List.of(label, label, label);
        Mockito.when(labelRepository.getAll()).thenReturn(labelList);
        List<Label> resultList = labelService.getAllLabels();
        Assertions.assertEquals(labelList, resultList);
        Mockito.verify(labelRepository).getAll();
    }
}

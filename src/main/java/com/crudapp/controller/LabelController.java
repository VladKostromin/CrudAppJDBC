package com.crudapp.controller;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Label;
import com.crudapp.services.LabelService;

import java.util.List;

public class LabelController {
    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    public Label getLabel(Long id) throws NotFoundException {
        return labelService.getLabelByID(id);
    }

    public Label createLabel(String labelName) {
        return labelService.createLabel(Label.builder()
                .name(labelName)
                .build());
    }
    public Label updateLabel(String labelName, Long labelId) throws NotFoundException {
        return labelService.updateLabel(Label.builder()
                .id(labelId)
                .name(labelName)
                .build());
    }
    public void deleteLabel(Long id) {
        labelService.deleteLabelById(id);
    }
    public List<Label> getAllLabels(){
        return labelService.getAllLabels();
    }

}

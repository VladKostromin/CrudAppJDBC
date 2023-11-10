package com.crudapp.controller;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;

import java.util.List;

public class LabelController {
    private final LabelRepository labelRepository;

    public LabelController(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label getLabel(Long id) throws NotFoundException {
        Label label = labelRepository.findById(id);
        if(label == null) throw new NotFoundException("Label not found");
        return label;
    }

    public Label createLabel(String labelName, Long postId) {
        Label label = Label.builder()
                .name(labelName)
                .postId(postId)
                .build();
        labelRepository.save(label);
        return label;
    }
    public Label updateLabel(Label label) {
        Label labelToUpdate = labelRepository.update(label);
        return labelToUpdate;
    }
    public void deleteById(Long id) {
        labelRepository.deleteById(id);
    }
    public List<Label> getAllLabels(){
        return labelRepository.getAll();
    }

}

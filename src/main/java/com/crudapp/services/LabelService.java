package com.crudapp.services;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;
import com.crudapp.repository.jdbc.JdbcLabelRepositoryImpl;

import java.util.List;

public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService() {
        this.labelRepository = new JdbcLabelRepositoryImpl();
    }

    public Label getLabelByID(Long id) throws NotFoundException {
        Label label = labelRepository.findById(id);
        if(label == null) throw new NotFoundException("Label not found");
        return label;
    }

    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }
    public Label updateLabel(Label label) throws NotFoundException {
        Label labelToUpdate = labelRepository.update(label);
        if(labelToUpdate == null) throw new NotFoundException("Label not exist");
        return labelToUpdate;
    }
    public void deleteLabelById(Long id) {
        labelRepository.deleteById(id);
    }
    public List<Label> getAllLabels(){
        return labelRepository.getAll();
    }
}

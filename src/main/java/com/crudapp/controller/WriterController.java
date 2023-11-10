package com.crudapp.controller;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;

import java.util.ArrayList;
import java.util.List;

public class WriterController {
    private WriterRepository writerRepository;

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer getWriter(Long id) throws NotFoundException{
        Writer writer = writerRepository.findById(id);
        if(writer == null) throw new NotFoundException("Writer not exist");
        return writer;
    }

    public Writer createWriter(String firstName,String lastName) {
        return writerRepository.save(Writer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .posts(new ArrayList<>())
                .build());
    }
    public Writer updateWriter(String name, String lastname, Long id) throws NotFoundException {
        Writer writer = getWriter(id);
        writer.setFirstName(name);
        writer.setLastName(lastname);
        return writerRepository.update(writer);
    }
    public void deleteWriter(Long id) {
        writerRepository.deleteById(id);
    }
    public List<Writer> getAllWriters(){
        return writerRepository.getAll();
    }
}

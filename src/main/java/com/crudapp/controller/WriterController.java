package com.crudapp.controller;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Writer;
import com.crudapp.services.WriterService;

import java.util.ArrayList;
import java.util.List;

public class WriterController {
    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    public Writer getWriter(Long id) throws NotFoundException{
        return writerService.getWriterByID(id);
    }

    public Writer createNewWriter(String firstName,String lastName) {
        return writerService.createWriter(Writer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .posts(new ArrayList<>())
                .build());
    }
    public Writer updateWriter(String firstName, String lastname, Long id) throws NotFoundException {
        return writerService.updateWriter(Writer.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastname)
                .posts(new ArrayList<>())
                .build());
    }
    public void deleteWriter(Long id) {
        writerService.deleteWriterByID(id);
    }
    public List<Writer> getAllWriters(){
        return writerService.getAllWriters();
    }
}

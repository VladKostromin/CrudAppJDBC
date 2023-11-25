package com.crudapp.services;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import com.crudapp.repository.jdbc.JdbcWriterRepositoryImpl;

import java.util.List;

public class WriterService {
    private final WriterRepository writerRepository;

    public WriterService() {
        this.writerRepository = new JdbcWriterRepositoryImpl();
    }

    public Writer getWriterByID(Long id) throws NotFoundException {
        Writer writer = writerRepository.findById(id);
        if(writer == null) throw new NotFoundException("Writer not exist");
        return writer;

    }

    public Writer createWriter(Writer writer) {
        return writerRepository.save(writer);
    }

    public Writer updateWriter(Writer writer) throws NotFoundException {
        Writer writerToUpdate = writerRepository.update(writer);
        if(writerToUpdate == null) throw new NotFoundException("Writer not exist");
        return writerToUpdate;
    }
    public void deleteWriterByID(Long id) {
        writerRepository.deleteById(id);
    }
    public List<Writer> getAllWriters(){
        return writerRepository.getAll();
    }
}

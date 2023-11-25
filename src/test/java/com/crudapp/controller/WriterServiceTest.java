package com.crudapp.controller;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import com.crudapp.services.WriterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WriterServiceTest {

    @Mock
    private WriterRepository writerRepository;

    @InjectMocks
    private WriterService writerService;

    private Writer writer;
    private Long writerId = 1L;


    @BeforeEach
    public void setUp() {
        writer = Writer.builder()
                .id(writerId)
                .firstName("John")
                .lastName("Snow")
                .posts(new ArrayList<>())
                .build();
    }

    @Test
    public void testGetWriterById_Found() throws NotFoundException {
        Long id = 1L;
        Mockito.when(writerRepository.findById(id)).thenReturn(writer);
        Writer result = writerService.getWriterByID(id);
        Assertions.assertEquals(writer, result);
        Mockito.verify(writerRepository).findById(writerId);
    }

    @Test
    public void testGetWriterById_NotFound() {
        Mockito.when(writerRepository.findById(writerId)).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> writerService.getWriterByID(writerId));
    }

    @Test
    public void testCreateWriter() {
        Writer savedWriter = Writer.builder()
                .id(writerId)
                .firstName("John")
                .lastName("Snow")
                .posts(new ArrayList<>())
                .build();
        Mockito.when(writerRepository.save(writer)).thenReturn(savedWriter);
        Writer resultWriter = writerService.createWriter(writer);
        Assertions.assertEquals(savedWriter, resultWriter);
        Mockito.verify(writerRepository).save(writer);
    }


    @Test
    public void testUpdateWriter_Found() throws NotFoundException {
        Mockito.when(writerRepository.update(writer)).thenReturn(writer);

        Writer resultWriter = writerService.updateWriter(writer);
        Assertions.assertEquals(writer, resultWriter);
        Mockito.verify(writerRepository).update(writer);
    }

    @Test
    public void testUpdateWriter_NotFound() {
        Mockito.when(writerRepository.update(writer)).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> writerService.updateWriter(writer));
    }

    @Test
    public void getAllWritersTest() {
        List<Writer> writerList = List.of(writer, writer, writer);
        Mockito.when(writerRepository.getAll()).thenReturn(writerList);
        List<Writer> resultList = writerService.getAllWriters();
        Assertions.assertEquals(writerList, resultList);
        Mockito.verify(writerRepository).getAll();
    }

    @Test
    public void deleteWriterTest() {

    }
}

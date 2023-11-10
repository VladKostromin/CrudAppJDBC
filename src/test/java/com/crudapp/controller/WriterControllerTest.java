package com.crudapp.controller;

import com.crudapp.dbfactory.DatabaseConnectionFactory;
import com.crudapp.dbfactory.MySqlConnectionFactory;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Not;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Stubber;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class WriterControllerTest {
    @InjectMocks
    private WriterController writerController;
    @Mock
    private WriterRepository writerRepository;

    private Writer writer;
    private final DatabaseConnectionFactory factory = new MySqlConnectionFactory();


    @BeforeEach
    void setUp() {
        writer = Writer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Snow")
                .posts(new ArrayList<>())
                .build();
    }


    @Test
    void get_notExistWriter_throwsException () {
        Mockito.when(writerRepository.findById(writer.getId())).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class, () -> writerController.getWriter(writer.getId()));

    }

    @Test
    void createWriterTest() {

        Mockito.when(writerRepository.save(writer)).thenReturn(writer);
        Writer writerToTest = writerController.createWriter("John", "Snow");
        Assertions.assertTrue(writer.equals(writerToTest));
    }


    @Test
    void updateWriterTest()  {

    }

    @Test
    void getAllWritersTest() {

    }

    @Test
    void deleteWriterTest() {

    }
}

package com.crudapp.repository.jdbc;

import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;
import com.crudapp.utils.JdbcUtils;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {
    private final String FIND_BY_ID_SQL = "SELECT * FROM labels WHERE id = ?";
    private final String GET_ALL_SQL = "SELECT * FROM labels";
    private final String SAVE_SQL = "INSERT INTO labels (name) VALUES (?)";
    private final String UPDATE_SQL = "UPDATE labels SET name = ?";
    private final String DELETE_SQL = "DELETE FROM labels WHERE id = ?";

    @SneakyThrows
    private Label mapResultSetToLabel(ResultSet resultSet) {
        Long id = resultSet.getLong("id");
        Label label = Label.builder()
                .id(id)
                .name(resultSet.getString("name"))
                .build();
        return label;
    }

    @Override
    @SneakyThrows
    public Label findById(Long id) {
       try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(FIND_BY_ID_SQL)) {
           preparedStatement.setLong(1, id);
           ResultSet resultSet = preparedStatement.executeQuery();
           return mapResultSetToLabel(resultSet);
       }
    }

    @Override
    @SneakyThrows
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                labels.add(mapResultSetToLabel(resultSet));
            }
            return labels;
        }

    }

    @Override
    @SneakyThrows
    public Label save(Label label) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKeys(SAVE_SQL)) {
            preparedStatement.setString(1, label.getName());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0) {
                try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        Long generatedId = generatedKeys.getLong(1);
                        label.setId(generatedId);
                        return label;
                    }
                }
            }
            return null;
        }
    }

    @Override
    @SneakyThrows
    public Label update(Label label) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(UPDATE_SQL)) {
            preparedStatement.executeUpdate();
            return label;
        }
    }

    @Override
    @SneakyThrows
    public void deleteById(Long id) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

}

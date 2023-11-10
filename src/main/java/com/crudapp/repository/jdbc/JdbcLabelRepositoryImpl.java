package com.crudapp.repository.jdbc;

import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Label;
import com.crudapp.repository.LabelRepository;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {

    private Connection connection;

    public JdbcLabelRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Label findById(Long id) {
        Statement statement = null;
        ResultSet resultSet = null;
        Label resultLabel = null;
        try {
            String SQL = "SELECT * FROM crudtest.labels WHERE id = " + id;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            resultLabel = Label.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .postId(resultSet.getLong("post_id"))
                    .build();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultLabel;
    }

    @Override
    @SneakyThrows
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        String SQL = "SELECT * FROM crudtest.labels";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            labels.add(Label.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .postId(resultSet.getLong("post_id"))
                    .build());
        }
        statement.close();
        resultSet.close();
        return labels;

    }

    @Override
    public Label save(Label label) {
        String SQL = "INSERT INTO crudtest.labels (name, post_id) VALUES ('"
                + label.getName() + "', "
                + label.getPostId() + ")";
        Statement statement = null;
        int insertFlag = 0;
        try {
            statement = connection.createStatement();
            insertFlag = statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return insertFlag == 0 ? null : label;
    }

    @Override
    @SneakyThrows
    public Label update(Label label) {
        String SQL = "UPDATE crudtest.labels SET name = '" + label.getName() + "' " + "WHERE id = " + label.getId() + ";";
        Statement statement = connection.createStatement();
        int updateFlag = statement.executeUpdate(SQL);
        statement.close();
        return updateFlag == 0 ? null : findById(label.getId());
    }

    @Override
    @SneakyThrows
    public void deleteById(Long id) {
        String SQL = "DELETE FROM crudtest.labels WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(SQL);
        statement.close();
    }

}

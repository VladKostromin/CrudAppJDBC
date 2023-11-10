package com.crudapp.repository.jdbc;

import com.crudapp.enums.PostStatus;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private Connection connection;

    public JdbcWriterRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Writer findById(Long id) {
        Statement statement = null;
        ResultSet resultSet = null;
        Writer resultWriter = null;
        try {
            String SQL = "SELECT * FROM crudtest.writers WHERE id = " + id;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            if(!resultSet.next()) return null;
            resultWriter = Writer.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .posts(getAllPostByWriterId(resultSet.getLong("id")))
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

        return resultWriter;
    }

    @Override
    @SneakyThrows
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        String SQL = "SELECT * FROM crudtest.writers";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            writers.add(Writer.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .posts(getAllPostByWriterId(resultSet.getLong("id")))
                    .build());
        }
        statement.close();
        resultSet.close();
        return writers;
    }

    @Override
    @SneakyThrows
    public Writer save(Writer writer) {
        String SQL = "INSERT INTO crudtest.writers (firstName, lastName)" + " VALUES ('"
                + writer.getFirstName() + "', '"
                + writer.getLastName() + "')";
        Statement statement = connection.prepareStatement(SQL);
        int insertFlag = statement.executeUpdate(SQL);
        statement.close();
        return insertFlag == 0 ? null : writer;
    }

    @Override
    @SneakyThrows
    public Writer update(Writer writer) {
        String SQL = "UPDATE crudtest.writers SET firstName = '"
                + writer.getFirstName() + "', " +  "lastName = '"
                + writer.getLastName() + "' WHERE id = "
                + writer.getId() + ";";
        Statement statement = null;
        int updateFlag = 0;
        try {
            statement = connection.createStatement();
            updateFlag = statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
        }
        return updateFlag == 0 ? null : writer;
    }

    @Override
    @SneakyThrows
    public void deleteById(Long id) {
        String SQL = "DELETE FROM crudtest.writers WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(SQL);
        statement.close();
    }
    @SneakyThrows
    private List<Post> getAllPostByWriterId(Long writerId) {
        List<Post> posts = new ArrayList<>();
        String SQL = "SELECT * FROM crudtest.posts WHERE writer_id = " + writerId;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            PostStatus postStatus = PostStatus.valueOf(resultSet.getString("status"));
            if(!(postStatus == PostStatus.DELETED)) {
                posts.add(Post.builder()
                        .id(resultSet.getLong("id"))
                        .writerId(resultSet.getLong("writer_id"))
                        .content(resultSet.getString("content"))
                        .created(resultSet.getTimestamp("created"))
                        .updated(resultSet.getTimestamp("updated"))
                        .status(postStatus)
                        .labels(getAllLabelsByPostID(resultSet.getLong("id")))
                        .build());
            }
        }
        return posts;
    }
    @SneakyThrows
    private List<Label> getAllLabelsByPostID(Long postId) {
        List<Label> labels = new ArrayList<>();
        String SQL = "SELECT * FROM crudtest.labels WHERE post_id = " + postId;
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
}

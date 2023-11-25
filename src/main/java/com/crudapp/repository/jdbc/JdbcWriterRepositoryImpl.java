package com.crudapp.repository.jdbc;

import com.crudapp.enums.PostStatus;
import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.model.Writer;
import com.crudapp.repository.WriterRepository;
import com.crudapp.utils.JdbcUtils;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcWriterRepositoryImpl implements WriterRepository {


    private final String GET_POSTS_SQL = "SELECT p.id AS post_id, p.content, p.created, p.updated, p.status, " +
            "l.id AS label_id, l.name AS label_name " +
            "FROM posts p " +
            "LEFT JOIN post_labels pl ON p.id = pl.post_id " +
            "LEFT JOIN labels l ON pl.label_id = l.id " +
            "WHERE p.writer_id = ?;";
    private final String FIND_BY_ID_SQL = "SELECT * FROM writers WHERE id = ?";
    private final String GET_ALL_SQL = "SELECT * FROM writers";
    private final String SAVE_SQL = "INSERT INTO writers" +
            "(firstname, lastname) VALUES (?, ?)";
    private final String UPDATE_SQL = "UPDATE writers SET firstname = ?, lastname = ?";
    private final String DELETE_SQL = "DELETE FROM writers WHERE id = ?";




    @Override
    @SneakyThrows
    public Writer findById(Long id) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSetToWriter(resultSet);
        }

    }

    @Override
    @SneakyThrows
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                writers.add(mapResultSetToWriter(resultSet));
            }
            return writers;
        }

    }

    @Override
    @SneakyThrows
    public Writer save(Writer writer) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKeys(SAVE_SQL)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long generatedId = generatedKeys.getLong(1);
                        return findById(generatedId);
                    }
                }
            }
        }
        return null;
    }

    @Override
    @SneakyThrows
    public Writer update(Writer writer) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSetToWriter(resultSet);
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

    private Writer mapResultSetToWriter(ResultSet resultSet) {
        Writer writer;
        try {
            if(resultSet.isBeforeFirst()) resultSet.next();
            Long writerId = resultSet.getLong("id");
            writer = Writer.builder()
                    .id(writerId)
                    .firstName(resultSet.getString("firstname"))
                    .lastName(resultSet.getString("lastname"))
                    .posts(getAllPosts(writerId))
                    .build();
        } catch (SQLException e) {
            return null;
        }
        return writer;
    }
    @SneakyThrows
    private List<Post> getAllPosts(Long writerId) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_POSTS_SQL)) {
            preparedStatement.setLong(1, writerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Post> postsMap = new HashMap<>();
            while (resultSet.next()) {
                Long postId = resultSet.getLong("post_id");
                String content = resultSet.getString("content");
                Timestamp created = resultSet.getTimestamp("created");
                Timestamp updated = resultSet.getTimestamp("updated");
                PostStatus status = PostStatus.valueOf(resultSet.getString("status"));
                Post post = postsMap.computeIfAbsent(postId, id -> Post.builder()
                        .id(id)
                        .content(content)
                        .created(created)
                        .updated(updated)
                        .status(status)
                        .labels(new ArrayList<>())
                        .build());
                long labelId = resultSet.getLong("label_id");
                if (labelId > 0) {
                    Label label = Label.builder()
                            .id(labelId)
                            .name(resultSet.getString("label_name"))
                            .build();
                    if (!post.getLabels().contains(label)) {
                        post.getLabels().add(label);
                    }
                }
            }
            return new ArrayList<>(postsMap.values());
        }
    }
}

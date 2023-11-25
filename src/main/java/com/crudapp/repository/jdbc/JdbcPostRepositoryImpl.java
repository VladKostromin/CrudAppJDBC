package com.crudapp.repository.jdbc;

import com.crudapp.enums.PostStatus;
import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.model.Writer;
import com.crudapp.repository.PostRepository;
import com.crudapp.utils.JdbcUtils;
import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {


    private final String POST_LABEL_SQL = "INSERT INTO post_labels VALUES (?, ?)";
    private final String SAVE_SQL = "INSERT INTO posts" +
            "(content, created, status, writer_id) VALUES (?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE posts SET content = ?, updated = ?, status = ?";
    private final String DELETE_SQL = "UPDATE posts SET status = 'DELETED' WHERE id = ?";
    private final String GET_POST_SQL = "SELECT * FROM posts WHERE id = ?";
    private final String WRITER_SQL = "SELECT * FROM writers WHERE id = ?;";
    private final String GET_LABELS_SQL = "SELECT l.id, l.name " +
            "FROM labels l " +
            "JOIN post_labels pl ON l.id = pl.label_id " +
            "WHERE pl.post_id = ?;";
    private final String GET_ALL_POSTS_SQL = "SELECT * FROM posts";



    @Override
    @SneakyThrows
    public Post findById(Long id) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKeys(GET_POST_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapResultSetToPost(resultSet);
        }
    }

    @Override
    @SneakyThrows
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_ALL_POSTS_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(mapResultSetToPost(resultSet));
            }
            return posts;
        }
    }

    @Override
    @SneakyThrows
    public Post save(Post post) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKeys(SAVE_SQL)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setTimestamp(2, post.getCreated());
            preparedStatement.setString(3, post.getStatus().getDesc());
            preparedStatement.setLong(4, post.getWriter().getId());
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0) {
                try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if(generatedKeys.next()) {
                        Long generatedId = generatedKeys.getLong(1);
                        post.setId(generatedId);
                        return post;
                    }
                }
            }
            return null;
        }
    }

    @Override
    @SneakyThrows
    public Post update(Post post) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setTimestamp(2, post.getUpdated());
            preparedStatement.setString(3, post.getStatus().getDesc());
            preparedStatement.executeUpdate();

            return post;
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

    @Override
    @SneakyThrows
    public Post addLabelToPost(Long postId, Long labelId) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(POST_LABEL_SQL)) {
            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, labelId);
            preparedStatement.executeUpdate();
            return findById(postId);
        }
    }

    @Override
    @SneakyThrows
    public Writer getWriter(Long id) {
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(WRITER_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            return Writer.builder()
                    .id(id)
                    .firstName(resultSet.getString("firstname"))
                    .lastName(resultSet.getString("lastname"))
                    .build();
        }
    }

    @SneakyThrows
    private Post mapResultSetToPost(ResultSet resultSet) {
        if(resultSet.isBeforeFirst()) resultSet.next();
        Post post;
        try {
            Long postId = resultSet.getLong("id");
            String content = resultSet.getString("content");
            Timestamp created = resultSet.getTimestamp("created");
            Timestamp updated = resultSet.getTimestamp("updated");
            PostStatus postStatus = PostStatus.valueOf(resultSet.getString("status"));
            post = Post.builder()
                    .id(postId)
                    .content(content)
                    .created(created)
                    .updated(updated)
                    .status(postStatus)
                    .labels(getAllLabels(postId))
                    .build();
        } catch (Exception e) {
            return null;
        }
        return post;
    }

    @SneakyThrows
    private List<Label> getAllLabels(Long postId) {
        List<Label> labelsList = new ArrayList<>();
        try(PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_LABELS_SQL)){
            preparedStatement.setLong(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long labelId = resultSet.getLong("id");
                if(labelId > 0) {
                    labelsList.add(Label.builder()
                            .id(labelId)
                            .name(resultSet.getString("name"))
                            .build());
                }
            }
        }
        return labelsList;
    }
}

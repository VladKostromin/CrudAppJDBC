package com.crudapp.repository.jdbc;

import com.crudapp.enums.PostStatus;
import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.repository.PostRepository;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {
    private Connection connection;

    public JdbcPostRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Post findById(Long id) {
        Statement statement = null;
        ResultSet resultSet = null;
        Post post = null;
        try {
            String SQL = "SELECT * FROM crudtest.posts WHERE id = " + id;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL);
            if(!resultSet.next()) return null;
            PostStatus postStatus = PostStatus.valueOf(resultSet.getString("status"));
            post = Post.builder()
                    .id(resultSet.getLong("id"))
                    .content(resultSet.getString("content"))
                    .created(resultSet.getTimestamp("created"))
                    .updated(resultSet.getTimestamp("updated"))
                    .writerId(resultSet.getLong("writer_id"))
                    .status(postStatus)
                    .labels(getAllLabelsByPostID(resultSet.getLong("id")))
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
        return post;
    }

    @Override
    @SneakyThrows
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String SQL = "SELECT * FROM crudtest.posts";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        while (resultSet.next()) {
            PostStatus postStatus = PostStatus.valueOf(resultSet.getString("status"));
            if(!(postStatus == PostStatus.DELETED)) {
                posts.add(Post.builder()
                        .id(resultSet.getLong("id"))
                        .content(resultSet.getString("content"))
                        .created(resultSet.getTimestamp("created"))
                        .updated(resultSet.getTimestamp("updated"))
                        .writerId(resultSet.getLong("writer_id"))
                        .status(postStatus)
                        .labels(getAllLabelsByPostID(resultSet.getLong("id")))
                        .build());
            }

        }
        return posts;
    }

    @Override
    @SneakyThrows
    public Post save(Post post) {
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        post.setCreated(timestamp);
        String SQL = "INSERT INTO crudtest.posts (content, created, writer_id, status)" + " VALUES ('"
                + post.getContent() + "', '"
                + post.getCreated() + "', "
                + post.getWriterId() + ", '"
                + post.getStatus() + "');";
        Statement statement = connection.createStatement();
        int insertFlag = statement.executeUpdate(SQL);
        return insertFlag == 0 ? null : post;
    }

    @Override
    public Post update(Post post) {
        int updateFlag = 0;
        Statement statement = null;
        try {
            post.setUpdated(new Timestamp(new java.util.Date().getTime()));
            post.setStatus(PostStatus.UNDER_REVIEW);
            String SQL = "UPDATE crudtest.posts SET content = '"
                    + post.getContent() + "', " + "updated = '"
                    + post.getUpdated() + "', " + "status = '"
                    + post.getStatus() + "' WHERE id = "
                    + post.getId() + ";";
            statement = connection.createStatement();
            updateFlag = statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return updateFlag == 0 ? null : post;
    }

    @Override
    @SneakyThrows
    public void deleteById(Long id) {
        String SQL = "UPDATE crudtest.posts SET status = 'DELETED' WHERE id = " + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(SQL);
        statement.close();
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

package com.fif.zk.repository;

import com.fif.zk.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance = new UserRepository();

    private final String URL = "jdbc:postgresql://localhost:5432/zk-pengajuan-leasing";
    private final String USER = "appuser";
    private final String PASSWORD = "apppass";

    private UserRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static UserRepository getInstance() {
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Menambahkan user
    public void addUser(User user) {
        String sql = "INSERT INTO users (email, password, role, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Timestamp now = Timestamp.valueOf(user.getCreatedAt());
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole() != null ? user.getRole() : "USER");
            stmt.setTimestamp(4, now);
            stmt.setTimestamp(5, now);

            stmt.executeUpdate();

            // Ambil ID yang di-generate
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ambil semua user
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, email, password, created_at, updated_at, deleted_at \n" +
                "FROM users";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                Timestamp updatedTs = rs.getTimestamp("updated_at");
                if (updatedTs != null) user.setUpdatedAt(updatedTs.toLocalDateTime());

                Timestamp deletedTs = rs.getTimestamp("deleted_at");
                if (deletedTs != null) user.setDeletedAt(deletedTs.toLocalDateTime());

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Ambil user berdasarkan email
    public User getUserByEmail(String email) {
        String sql = "SELECT id, email, password, created_at, updated_at, deleted_at \n" +
                "FROM users \n" +
                "WHERE email = ?";
        User user = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                    Timestamp updatedTs = rs.getTimestamp("updated_at");
                    if (updatedTs != null) user.setUpdatedAt(updatedTs.toLocalDateTime());

                    Timestamp deletedTs = rs.getTimestamp("deleted_at");
                    if (deletedTs != null) user.setDeletedAt(deletedTs.toLocalDateTime());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Cek email sudah ada atau belum
    public boolean existsEmail(String email) {
        String sql = "SELECT 1 \n" +
                "FROM users \n" +
                "WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
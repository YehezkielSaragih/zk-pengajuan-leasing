package com.fif.zk.repository;

import com.fif.zk.model.Creditor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CreditorRepository {

    private static CreditorRepository instance = new CreditorRepository();

    private final String URL = "jdbc:postgresql://localhost:5432/zk-pengajuan-leasing";
    private final String USER = "appuser";
    private final String PASSWORD = "apppass";

    private CreditorRepository() {
        try {
            Class.forName("org.postgresql.Driver"); // load driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static CreditorRepository getInstance() {
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Creditor> findAll() {
        List<Creditor> list = new ArrayList<>();
        String sql = "SELECT id, name, age, address, income, created_at, updated_at, deleted_at " +
                "FROM creditors " +
                "WHERE deleted_at IS NULL " +
                "ORDER BY id ASC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Creditor c = mapResultSet(rs);
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Creditor findById(int id) {
        String sql = "SELECT id, name, age, address, income, created_at, updated_at, deleted_at " +
                "FROM creditors " +
                "WHERE id=? AND deleted_at IS NULL";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Creditor c) {
        String sql = "INSERT INTO creditors(name, age, address, income, created_at, updated_at) " +
                "VALUES(?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime now = LocalDateTime.now();
            ps.setString(1, c.getName());
            ps.setInt(2, c.getAge());
            ps.setString(3, c.getAddress());
            ps.setInt(4, c.getIncome());
            ps.setTimestamp(5, Timestamp.valueOf(now));
            ps.setTimestamp(6, Timestamp.valueOf(now));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                    c.setCreatedAt(now);
                    c.setUpdatedAt(now);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Creditor c) {
        String sql = "UPDATE creditors " +
                "SET name=?, age=?, address=?, income=?, updated_at=? " +
                "WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setInt(2, c.getAge());
            ps.setString(3, c.getAddress());
            ps.setInt(4, c.getIncome());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(6, c.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void softDelete(int id) {
        String sql = "UPDATE creditors " +
                "SET deleted_at=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Creditor mapResultSet(ResultSet rs) throws SQLException {
        Creditor c = new Creditor();
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setAge(rs.getInt("age"));
        c.setAddress(rs.getString("address"));
        c.setIncome(rs.getInt("income"));
        c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        c.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        Timestamp deleted = rs.getTimestamp("deleted_at");
        if (deleted != null) c.setDeletedAt(deleted.toLocalDateTime());
        return c;
    }
}
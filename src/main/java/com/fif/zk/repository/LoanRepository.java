package com.fif.zk.repository;

import com.fif.zk.model.Loan;
import com.fif.zk.model.Creditor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {

    private static LoanRepository instance = new LoanRepository();

    private final String URL = "jdbc:postgresql://localhost:5432/zk-pengajuan-leasing";
    private final String USER = "appuser";
    private final String PASSWORD = "apppass";

    private LoanRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static LoanRepository getInstance() {
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Loan> findAll() {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT id, creditor_id, loan_name, loan_type, loan_amount, down_payment, status, created_at, updated_at, deleted_at " +
                "FROM loans " +
                "WHERE deleted_at IS NULL " +
                "ORDER BY id ASC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Loan findById(int id) {
        String sql = "SELECT id, creditor_id, loan_name, loan_type, loan_amount, down_payment, status, created_at, updated_at, deleted_at " +
                "FROM loans " +
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

    public void save(Loan loan) {
        String sql = "INSERT INTO loans(creditor_id, loan_name, loan_type, loan_amount, down_payment, status, created_at, updated_at) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            LocalDateTime now = LocalDateTime.now();
            ps.setInt(1, loan.getCreditor().getId());
            ps.setString(2, loan.getLoanName());
            ps.setString(3, loan.getLoanType());
            ps.setInt(4, loan.getLoanAmount());
            ps.setInt(5, loan.getDownPayment());
            ps.setString(6, loan.getStatus());
            ps.setTimestamp(7, Timestamp.valueOf(now));
            ps.setTimestamp(8, Timestamp.valueOf(now));

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    loan.setId(rs.getInt(1));
                    loan.setCreatedAt(now);
                    loan.setUpdatedAt(now);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Loan loan) {
        String sql = "UPDATE loans " +
                "SET creditor_id=?, loan_name=?, loan_type=?, loan_amount=?, down_payment=?, status=?, updated_at=? " +
                "WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, loan.getCreditor().getId());
            ps.setString(2, loan.getLoanName());
            ps.setString(3, loan.getLoanType());
            ps.setInt(4, loan.getLoanAmount());
            ps.setInt(5, loan.getDownPayment());
            ps.setString(6, loan.getStatus());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(8, loan.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void softDelete(int id) {
        String sql = "UPDATE loans " +
                "SET deleted_at=? " +
                "WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Loan> findByCreditorId(int creditorId) {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT id, creditor_id, loan_name, loan_type, loan_amount, down_payment, status, created_at, updated_at, deleted_at " +
                "FROM loans " +
                "WHERE creditor_id=? AND deleted_at IS NULL";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, creditorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Loan mapResultSet(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setId(rs.getInt("id"));

        // Set creditor with just the ID (lazy fetch, can fetch full object if needed)
        Creditor c = new Creditor();
        c.setId(rs.getInt("creditor_id"));
        loan.setCreditor(c);

        loan.setLoanName(rs.getString("loan_name"));
        loan.setLoanType(rs.getString("loan_type"));
        loan.setLoanAmount(rs.getInt("loan_amount"));
        loan.setDownPayment(rs.getInt("down_payment"));
        loan.setStatus(rs.getString("status"));
        loan.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        loan.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        Timestamp deleted = rs.getTimestamp("deleted_at");
        if (deleted != null) loan.setDeletedAt(deleted.toLocalDateTime());

        return loan;
    }
}

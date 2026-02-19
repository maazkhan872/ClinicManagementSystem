/*package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Billing;
import com.clinic.models.BillingItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    public boolean generateInvoice(Billing b) {

        String sql =
                "INSERT INTO billing " +
                "(patient_id, appointment_id, total_amount, payment_status, billing_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement pstmt =
                        conn.prepareStatement(sql)

        ) {

            pstmt.setInt(
                    1,
                    b.getPatientId()
            );

            pstmt.setInt(
                    2,
                    getLatestAppointmentId(
                            b.getPatientId()
                    )
            );

            pstmt.setDouble(
                    3,
                    b.getTotalAmount()
            );

            pstmt.setString(
                    4,
                    b.getPaymentStatus()
            );

            pstmt.setDate(
                    5,
                    Date.valueOf(
                            b.getBillingDate()
                    )
            );

            int rows =
                    pstmt.executeUpdate();

            return rows > 0;

        }

        catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    public int getLatestAppointmentId(int patientId) {

        String sql =
                "SELECT appointment_id FROM appointments " +
                "WHERE patient_id=? ORDER BY appointment_id DESC LIMIT 1";

        try (

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement pstmt =
                        conn.prepareStatement(sql)

        ) {

            pstmt.setInt(
                    1,
                    patientId
            );

            ResultSet rs =
                    pstmt.executeQuery();

            if (rs.next())

                return rs.getInt(
                        "appointment_id"
                );

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }


    public List<BillingItem>
    getBillingItemsByPatient(int patientId) {

        List<BillingItem> list =
                new ArrayList<>();

        String sql =
                "SELECT s.service_name,bi.quantity,bi.unit_price " +
                "FROM billing_items bi " +
                "JOIN services s ON s.service_id=bi.service_id " +
                "WHERE bi.patient_id=?";

        try (

                Connection conn =
                        DatabaseConnection.getConnection();

                PreparedStatement pstmt =
                        conn.prepareStatement(sql)

        ) {

            pstmt.setInt(
                    1,
                    patientId
            );

            ResultSet rs =
                    pstmt.executeQuery();

            while (rs.next()) {

                list.add(

                        new BillingItem(

                                rs.getString(
                                        "service_name"
                                ),

                                rs.getInt(
                                        "quantity"
                                ),

                                rs.getDouble(
                                        "unit_price"
                                )

                        )

                );

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

}*/

package com.clinic.dao;

import com.clinic.connection.DatabaseConnection;
import com.clinic.models.Billing;
import com.clinic.models.BillingItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    public boolean generateInvoice(Billing b, List<BillingItem> items) {

        String billingSql =
                "INSERT INTO billing (patient_id, appointment_id, total_amount, payment_status, billing_date) VALUES (?, ?, ?, ?, ?)";

        String itemSql =
                "INSERT INTO billing_items (bill_id, patient_id, description, amount) VALUES (?, ?, ?, ?)";

        try {

            Connection conn = DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            // INSERT billing
            PreparedStatement billStmt =
                    conn.prepareStatement(billingSql, Statement.RETURN_GENERATED_KEYS);

            billStmt.setInt(1, b.getPatientId());
            billStmt.setInt(2, b.getAppointmentId());
            billStmt.setDouble(3, b.getTotalAmount());
            billStmt.setString(4, b.getPaymentStatus());
            billStmt.setDate(5, Date.valueOf(b.getBillingDate()));

            billStmt.executeUpdate();

            ResultSet rs = billStmt.getGeneratedKeys();

            int billId = 0;

            if (rs.next()) {

                billId = rs.getInt(1);

            }

            // INSERT billing_items
            PreparedStatement itemStmt =
                    conn.prepareStatement(itemSql);

            for (BillingItem item : items) {

                itemStmt.setInt(1, billId);

                itemStmt.setInt(2, b.getPatientId());

                itemStmt.setString(
                        3,
                        item.getItemName()
                );

                itemStmt.setDouble(
                        4,
                        item.getTotal()
                );

                itemStmt.addBatch();

            }

            itemStmt.executeBatch();

            conn.commit();

            conn.setAutoCommit(true);

            return true;

        }

        catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


    public int getLatestAppointmentId(int patientId) {

        String sql =
                "SELECT appointment_id FROM appointments WHERE patient_id=? ORDER BY appointment_id DESC LIMIT 1";

        try {

            Connection conn =
                    DatabaseConnection.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            pstmt.setInt(1, patientId);

            ResultSet rs =
                    pstmt.executeQuery();

            if (rs.next()) {

                return rs.getInt("appointment_id");

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }


    public List<BillingItem> getBillingItemsByPatient(int patientId) {

        List<BillingItem> list = new ArrayList<>();

        String sql =
                "SELECT medicine_name, quantity, price FROM prescription_items WHERE patient_id=?";

        try {

            Connection conn =
                    DatabaseConnection.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            pstmt.setInt(1, patientId);

            ResultSet rs =
                    pstmt.executeQuery();

            while (rs.next()) {

                list.add(
                        new BillingItem(
                                rs.getString("medicine_name"),
                                rs.getInt("quantity"),
                                rs.getDouble("price")
                        )
                );

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

        return list;

    }

}

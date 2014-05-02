/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.equip;

import java.io.File;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;




/**
 *
 * @author Mathis
 */
public class DataHelpers {

    public static ArrayList<String> mds_service_claimant = new ArrayList<String>();
    public static ArrayList<String> mds_device = new ArrayList<String>();
    public static ArrayList<String> mds_service_order = new ArrayList<String>();
    public static ArrayList<String> mds_testing = new ArrayList<String>();
    public static ArrayList<String> mds_test = new ArrayList<String>();
    public static ArrayList<String> mds_diagnosis = new ArrayList<String>();
    public static ArrayList<String> mds_repair = new ArrayList<String>();
    public static ArrayList<String> mds_sent_devices = new ArrayList<String>();
    public static ArrayList<String> mds_invoice = new ArrayList<String>();
    public static Connection conn;
    public static SessionFactory sessionFactory;

    public static void initializeVariables() {
        mds_service_claimant.add("name");
        mds_service_claimant.add("adress");
        mds_service_claimant.add("city");
        mds_service_claimant.add("country");
        mds_service_claimant.add("email");
        mds_service_claimant.add("phone_number");
        mds_service_claimant.add("legal_type");
        mds_device.add("imei");
        mds_device.add("type");
        mds_device.add("id_device_model");
        // mds_service_order.add("mds_service_order_registration_date");
        mds_service_order.add("fault_description");
        mds_service_order.add("id_device");
        mds_service_order.add("id_claimant");
        //  mds_testing.add("end_time");
        mds_testing.add("id_head_diagnostician");
        mds_testing.add("id_device");
        mds_test.add("type");
        mds_test.add("id_testing");
        mds_test.add("found_fault");
        mds_diagnosis.add("id_device");
        mds_diagnosis.add("specification");
        mds_diagnosis.add("id_diagnostician");
        mds_repair.add("id_diagnostician");
        mds_repair.add("id_diagnosis");
        mds_sent_devices.add("id_repair");
        mds_sent_devices.add("id_diagnostician");
        mds_invoice.add("price");
        mds_invoice.add("id_diagnostician");
        mds_invoice.add("id_repair");
    }

    public static void createConnection() {
        try {
            String ConUrl = "jdbc:mysql://localhost/MobileDeviceService";
            conn = (Connection) DriverManager.getConnection(ConUrl, "root", "root");
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(DataHelpers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void configureDBConnection() {
        Configuration config = new Configuration();
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mobiledeviceservice?zeroDateTimeBehavior=convertToNull");
        config.setProperty("hibernate.connection.username", "root");
        config.setProperty("hibernate.connection.password", "root");
        config.addJar(new File("target/lib/HibernateMapper-1.0-SNAPSHOT.jar"));
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
    }

    public static void configureLogging() {
        org.apache.log4j.PropertyConfigurator.configure("config/log4j.properties");
    }
    public static ResultSet selectFrom(String line) throws SQLException {
        ResultSet rs = null;
        try {

            Statement stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(line);
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rs;
    }

    public static void updateRow(String line) {
        try {

            Statement stmt = (Statement) conn.createStatement();
            System.out.println(line);
            stmt.executeUpdate(line);
            conn.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(DataHelpers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteRow(String line) throws SQLException {
        try {

            Statement stmt = (Statement) conn.createStatement();
            stmt.executeUpdate(line);
            conn.commit();
            System.out.println(line);
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(DataHelpers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Long insertInto(String line) throws SQLException {
        ResultSet generatedKey = null;
        Long id = null;
        try {

            Statement stmt = (Statement) conn.createStatement();
            System.out.println(line);
            stmt.executeUpdate(line, Statement.RETURN_GENERATED_KEYS);
            generatedKey = stmt.getGeneratedKeys();
            while (generatedKey.next()) {
                id = generatedKey.getLong(1);
            }
            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public static Long insertFromArray(ArrayList<String> arrayList, String table, ArrayList<String> collumnList) {
        Long generatedKey = null;
        String line = "INSERT INTO `" + table + "`" + "(";
        for (int i = 0; i < collumnList.size(); i++) {
            line += "`" + collumnList.get(i) + "`";
            if (i < collumnList.size() - 1) {
                line += ",";
            }

        }

        line += ")VALUES" + "('";
        Integer i = 0;
        for (String temp : arrayList) {
            line += temp;
            if (i < arrayList.size() - 1) {
                line += "','";
            }
            i++;
        }
        line += "')";
        try {
            generatedKey = DataHelpers.insertInto(line);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DataHelpers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generatedKey;
    }

}

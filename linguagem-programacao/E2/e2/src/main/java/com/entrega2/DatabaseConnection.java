package com.entrega2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/carrosdb";
    private static Connection conn = null;

    public static boolean connect(String user, String password) {
        try {
            conn = DriverManager.getConnection(URL, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static ObservableList<Carro> getCarros() {
        List<Carro> carrosList = getAllCarros();
        return FXCollections.observableArrayList(carrosList);
    }

    public static void insertCarro(Carro carro) {
        String SQL = "INSERT INTO carro(fabricante, placa, modelo, ano) VALUES(?,?,?,?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(SQL)) {
            pstmt.setString(1, carro.fabricante);
            pstmt.setString(2, carro.placa);
            pstmt.setString(3, carro.modelo);
            pstmt.setString(4, carro.ano);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<Carro> getAllCarros() {
        String SQL = "SELECT * FROM carro";
        List<Carro> carros = new ArrayList<>();
        try (PreparedStatement pstmt = getConnection().prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Carro carro = new Carro(
                    rs.getString("fabricante"),
                    rs.getString("placa"),
                    rs.getString("modelo"),
                    rs.getString("ano")
                );
                carros.add(carro);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return carros;
    }

    public static void deleterCarro(Carro carro) {
        String SQL = "DELETE FROM carro WHERE placa = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(SQL)) {
            pstmt.setString(1, carro.placa);
            pstmt.executeUpdate();
            System.out.println("Carro deletado com sucesso.");
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar carro.");
            System.out.println(ex.getMessage());
        }
    }

    public static void updateCarro(Carro carro, String placaOriginal) {
        String SQL = "UPDATE carro SET fabricante = ?, modelo = ?, ano = ?, placa = ? WHERE placa = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(SQL)) {
            pstmt.setString(1, carro.fabricante);
            pstmt.setString(2, carro.modelo);
            pstmt.setString(3, carro.ano);
            pstmt.setString(4, carro.placa);
            pstmt.setString(5, placaOriginal);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

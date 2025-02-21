package model.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.entity.Animal;
import model.entity.Enclos;

public class ZooDatabaseManager implements IZooDataAccess{
    private static final ZooDatabaseManager instance = new ZooDatabaseManager();

    private static Connection connection;

    private ZooDatabaseManager() {
        try {
            // Explicitly load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Create connection to database
            connection = DriverManager.getConnection("jdbc:sqlite:zoo.sqlite");

            // Validate connection
            if (connection == null) {
                throw new SQLException("Connection could not be established");
            }
            
//			createTableAnimaux(connection);
//			createTableEnclos(connection);
//			populateEnclos(connection);
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found: " + e.getMessage());
            // Optionally log or rethrow
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            // Optionally log or rethrow
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite:bibliotheque.sqlite");
//            throw new IllegalStateException("Database connection not initialized");
        }
        return connection;
    }


    public static ZooDatabaseManager getInstance() {
        return instance;
    }

    public static void createTableAnimaux(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS animaux(" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nom TEXT NOT NULL," +
                     "espece TEXT," +
                     "age INTEGER," +
                     "regime_alimentaire TEXT" +
                     ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table Animaux créé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableEnclos(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS enclos(" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nom TEXT NOT NULL," +
                     "capacite INTEGER," +
                     "type_habitat TEXT" +
                     ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table enclos créé avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean addAnimal(Animal animal) {
        String sql = "INSERT INTO animaux(nom, espece, age, regime_alimentaire) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, animal.getNom());
            pstmt.setString(2, animal.getEspece());
            pstmt.setInt(3, animal.getAge());
            pstmt.setString(4, animal.getRegimeAlimentaire());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

  

    @Override
    public Animal getAnimalById(int id) {
        String sql = "SELECT * FROM animaux WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Animal(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("espece"),
                    rs.getInt("age"),
                    rs.getString("regime_alimentaire")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

  
    @Override
    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animaux";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                animals.add(new Animal(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("espece"),
                    rs.getInt("age"),
                    rs.getString("regime_alimentaire")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

 

    @Override
    public boolean updateAnimal(Animal animal) {
        String sql = "UPDATE animaux SET nom = ?, espece = ?, age = ?, regime_alimentaire = ? WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setString(1, animal.getNom());
            pstmt.setString(2, animal.getEspece());
            pstmt.setInt(3, animal.getAge());
            pstmt.setString(4, animal.getRegimeAlimentaire());
            pstmt.setInt(5, animal.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAnimal(Animal animal) {
        String sql = "DELETE FROM animaux WHERE id = ?";
        try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, animal.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    @Override
    public List<Enclos> getAllEnclos() {
        List<Enclos> enclos  = new ArrayList<>();
        String sql = "SELECT * FROM enclos";
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
            	enclos.add(new Enclos(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getInt("capacite"),
                    rs.getString("type_habitat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enclos;
    }
    
    private static boolean populateEnclos(Connection conn) {
    
        String sql = "INSERT INTO enclos(nom, capacite, type_habitat) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "cage des animaux");
            pstmt.setInt(2, 20);
            pstmt.setString(3, "habitat des aves");
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        Connection conn;
		try {
			conn = ZooDatabaseManager.getConnection();
			createTableAnimaux(conn);
			createTableEnclos(conn);
			populateEnclos(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}
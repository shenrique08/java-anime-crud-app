package org.repository;

import lombok.extern.log4j.Log4j2;
import org.connection.ConnectionFactory;
import org.domain.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ProducerRepository {

    public static Optional<Producer> findById(Integer id) {
        String sql = "SELECT * FROM anime_store.producer WHERE id = ?;";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Producer producer = Producer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                return Optional.of(producer);
            }
        } catch (SQLException e) {
            log.error("Error trying to find the producer by id = '{}' ! ", id, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public static List<Producer> findByName(String name) {
        String sql = "SELECT * FROM anime_store.producer WHERE name LIKE ?;";
        List<Producer> producerList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Producer producer = Producer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                producerList.add(producer);
            }
        } catch (SQLException e) {
            log.error("Error trying to find the producer '{}' ! ", name, e);
            throw new RuntimeException(e);
        }
        return producerList;
    }

    public static List<Producer> findAll() {

        String sql = "SELECT * FROM anime_store.producer;";
        List<Producer> producerList = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Producer producer = Producer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build();
                producerList.add(producer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producerList;
    }

    public static void deleteById(Integer id) {
        String sql = "DELETE FROM anime_store.producer WHERE id=?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
                log.info("Producer with id = '{}' was deleted", id);
            else
                log.info("No Producer found with id = '{}'", id);

        } catch (SQLException e) {
            log.info("Error trying to delete Producer with id = '{}'", id);
            throw new RuntimeException(e);
        }
    }

    public static void save(Producer producer) {
        String sql = "INSERT IGNORE INTO `anime_store`.`producer` (`name`) VALUES (?);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, producer.getName());
            preparedStatement.executeUpdate();
            log.info("Producer was saved on the database");

        } catch (SQLException e) {
            log.error("Error trying to save the Producer in the database");
            throw new RuntimeException(e);
        }
    }

    public static void update(Producer producer) {

        if (producer == null || producer.getId() == null)
            throw new IllegalArgumentException("Producer or Producer ID cannot be null for update operation.");

        String sql = "UPDATE anime_store.producer SET name=? WHERE (id = ?);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, producer.getName());
            preparedStatement.setInt(2, producer.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
                log.info("Updated Producer with id = '{}'", producer.getId());
            else
                log.warn("Producer with id = '{}' not found", producer.getId());

        } catch (SQLException e) {
            log.error("Error trying to update Producer with id = '{}'", producer.getId());
            throw new RuntimeException(e);
        }
    }
}
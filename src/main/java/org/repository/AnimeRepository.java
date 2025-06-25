package org.repository;

import lombok.extern.log4j.Log4j2;
import org.connection.ConnectionFactory;
import org.domain.Anime;
import org.domain.Producer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class AnimeRepository {

    public static void deleteById(Integer id) {
        String sql = "DELETE FROM anime_store.anime WHERE id=?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0)
                log.info("Anime with id = '{}' was deleted", id);
            else
                log.error("Anime with id = '{}' was NOT deleted", id);

        } catch (SQLException e) {
            log.error("Error deleting Anime with id = '{}'", id, e);
            throw new RuntimeException(e);
        }
    }

    public static void update(Anime anime) {
        String sql = "UPDATE anime_store.anime SET name=?, episodes=?, producer_id=? WHERE id=?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, anime.getName());
            preparedStatement.setInt(2, anime.getEpisodes());
            preparedStatement.setInt(3, anime.getProducer().getId());
            preparedStatement.setInt(4, anime.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
                log.info("'{}' updated successfully", anime.getName());
            else
                log.warn("'{}' not updated", anime.getName());

        } catch (SQLException e) {
            log.error("Error trying to update '{}'", anime.getName());
            throw new RuntimeException(e);
        }
    }

    public static void save(Anime anime) {
        String sql = "INSERT INTO anime_store.anime (name, episodes, producer_id) VALUES(?, ?, ?);";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, anime.getName());
            preparedStatement.setInt(2, anime.getEpisodes());
            preparedStatement.setInt(3, anime.getProducer().getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0)
                log.info("Anime '{}' saved in the database: ", anime.getName());
            else
                log.error("Anime '{}' not saved in the database", anime.getName());

        } catch (SQLException e) {
            log.error("Error trying to save anime '{}'", anime.getName(), e);
            throw new RuntimeException(e);
        }
    }

    public static List<Anime> findAll() {
        String sql = "SELECT a.id, a.name, a.episodes, p.id as producer_id, p.name as producer_name " +
                     "FROM anime_store.anime a " +
                     "INNER JOIN anime_store.producer p ON a.producer_id = p.id " +
                     "ORDER BY a.id;";
        List<Anime> animeList = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            resultSetProducerAndAnime(animeList, preparedStatement);
        } catch (SQLException e) {
            log.error("Error trying to find all anime", e);
            throw new RuntimeException(e);
        }

        return animeList;
    }

    private static void resultSetProducerAndAnime(List<Anime> animeList, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Producer producer = Producer.builder()
                    .id(resultSet.getInt("producer_id"))
                    .name(resultSet.getString("producer_name"))
                    .build();

            Anime anime = Anime.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .episodes(resultSet.getInt("episodes"))
                    .producer(producer)
                    .build();

            animeList.add(anime);
        }
    }

    public static List<Anime> findByName(String name) {
        String sql = "SELECT a.id, a.name, a.episodes, p.id as producer_id, p.name as producer_name " +
                     "FROM anime_store.anime a " +
                     "INNER JOIN anime_store.producer p ON a.producer_id = p.id " +
                     "WHERE a.name LIKE ? " +
                     "ORDER BY a.id;";

        List<Anime> animeList = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + name + "%");
            resultSetProducerAndAnime(animeList, preparedStatement);

        } catch (SQLException e) {
            log.error("Error trying to find anime by name", e);
            throw new RuntimeException(e);
        }


        return animeList;
    }

    public static Optional<Anime> findById(Integer id) {
        String sql = "SELECT a.id, a.name, a.episodes, p.id as producer_id, p.name as producer_name " +
                     "FROM anime_store.anime a " +
                     "INNER JOIN anime_store.producer p ON a.producer_id = p.id " +
                     "WHERE a.id = ?;";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                return Optional.empty();

            Producer producer = Producer.builder()
                    .id(resultSet.getInt("producer_id"))
                    .name(resultSet.getString("producer_name"))
                    .build();

            Anime anime = Anime.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .episodes(resultSet.getInt("episodes"))
                    .producer(producer)
                    .build();

            return Optional.of(anime);

        } catch (SQLException e) {
            log.error("Error trying to find anime by id '{}'", id, e);
            throw new RuntimeException(e);
        }
    }
}

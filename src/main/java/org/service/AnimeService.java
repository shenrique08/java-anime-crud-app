package org.service;

import org.domain.Anime;
import org.repository.AnimeRepository;

import java.util.List;
import java.util.Optional;

public class AnimeService {

    public static List<Anime> findAll() {return AnimeRepository.findAll(); }

    public static List<Anime> findByName(String name) {return AnimeRepository.findByName(name);}

    public static void save(Anime anime) {
        if (anime.getName() == null || anime.getName().isBlank())
            throw new IllegalArgumentException("Anime name cannot be empty");
        AnimeRepository.save(anime);
    }

    public static void update(Anime anime) {
        if (anime.getId() == null || anime.getId() <= 0)
            throw new IllegalArgumentException("Invalid ID for update");
        if (anime.getName() == null || anime.getName().isBlank())
            throw new IllegalArgumentException("Anime name cannot be empty");
        AnimeRepository.update(anime);
    }

    public static Optional<Anime> findById(Integer id) { return AnimeRepository.findById(id);}

    public static void deleteById(Integer id) { AnimeRepository.deleteById(id);}
}

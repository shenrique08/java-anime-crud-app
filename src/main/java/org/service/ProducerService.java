package org.service;


import org.domain.Producer;
import org.repository.ProducerRepository;

import java.util.List;
import java.util.Optional;

public class ProducerService {

    public static List<Producer> findByName(String name) {
        return ProducerRepository.findByName(name);
    }

    public static List<Producer> findAll() {return ProducerRepository.findAll();}

    public static void deleteById(Integer id) { ProducerRepository.deleteById(id);}

    public static void save(Producer producer) {
        if (producer.getName() == null || producer.getName().isBlank())
            throw new IllegalArgumentException("Producer cannot be blank");

        ProducerRepository.save(producer);
    }

    public static void update(Producer producer) {
        if (producer.getId() == null || producer.getId() <= 0)
            throw new IllegalArgumentException("Invalid ID for update");
        if (producer.getName() == null || producer.getName().isBlank())
            throw new IllegalArgumentException("Anime name cannot be empty");
        ProducerRepository.update(producer);
    }

    public static Optional<Producer> findById(Integer id) { return ProducerRepository.findById(id);}
}

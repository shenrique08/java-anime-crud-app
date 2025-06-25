package org.application;

import org.domain.Anime;
import org.domain.Producer;
import org.service.AnimeService;
import org.service.ProducerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int op;
        while (true) {
            mainMenu();
            op = Integer.parseInt(SCANNER.nextLine());
            switch (op) {
                case 1 -> animeMenu();
                case 2 -> producerMenu();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void mainMenu() {
        System.out.println("--------------------");
        System.out.println("Select an option:");
        System.out.println("1. Anime CRUD");
        System.out.println("2. Producer CRUD");
        System.out.println("0. Exit");
        System.out.println("--------------------");
    }

    private static void animeMenu() {
        int op;
        while (true) {
            System.out.println("--- Anime CRUD ---");
            op = getOp();

            switch (op) {
                case 1 -> findAllAnimes();
                case 2 -> findAnimeByName();
                case 3 -> findAnimeById();
                case 4 -> saveAnime();
                case 5 -> updateAnime();
                case 6 -> deleteAnimeById();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static int getOp() {
        int op;
        System.out.println("1. Find all");
        System.out.println("2. Find by name");
        System.out.println("3. Find by id");
        System.out.println("4. Save");
        System.out.println("5. Update");
        System.out.println("6. Delete by id");
        System.out.println("0. Back to main menu");
        System.out.println("--------------------");

        op = Integer.parseInt(SCANNER.nextLine());
        return op;
    }

    private static void producerMenu() {
        int op;
        while (true) {
            System.out.println("--- Producer CRUD ---");
            op = getOp();

            switch (op) {
                case 1 -> findAllProducers();
                case 2 -> findProducerByName();
                case 3 -> findProducerById();
                case 4 -> saveProducer();
                case 5 -> updateProducer();
                case 6 -> deleteProducerById();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }


    private static void findAllAnimes() {
        System.out.println("--- All Animes ---");
        List<Anime> allAnimes = AnimeService.findAll();
        allAnimes.forEach(System.out::println);
    }

    private static void findAnimeByName() {
        System.out.println("Enter the name of the anime:");
        String name = SCANNER.nextLine();
        List<Anime> animes = AnimeService.findByName(name);
        animes.forEach(System.out::println);
    }

    private static void findAnimeById() {
        System.out.println("Enter the id of the anime:");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Anime> anime = AnimeService.findById(id);
        anime.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Anime with id " + id + " not found.")
        );
    }


    private static void saveAnime() {
        System.out.println("Enter the name of the anime:");
        String name = SCANNER.nextLine();
        System.out.println("Enter the number of episodes:");
        int episodes = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Enter the producer id:");
        int producerId = Integer.parseInt(SCANNER.nextLine());

        Optional<Producer> producer = ProducerService.findById(producerId);
        if (producer.isEmpty()) {
            System.out.println("Producer with id " + producerId + " not found.");
            return;
        }

        Anime anime = Anime.builder()
                .name(name)
                .episodes(episodes)
                .producer(producer.get())
                .build();
        AnimeService.save(anime);
        System.out.println("Anime saved successfully.");
    }

    private static void updateAnime() {
        System.out.println("Enter the id of the anime to update:");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Anime> animeOptional = AnimeService.findById(id);

        if (animeOptional.isEmpty()) {
            System.out.println("Anime with id " + id + " not found.");
            return;
        }

        Anime animeToUpdate = animeOptional.get();

        System.out.println("Enter the new name of the anime (current: " + animeToUpdate.getName() + "):");
        String newName = SCANNER.nextLine();
        System.out.println("Enter the new number of episodes (current: " + animeToUpdate.getEpisodes() + "):");
        int newEpisodes = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Enter the new producer id (current: " + animeToUpdate.getProducer().getId() + "):");
        int newProducerId = Integer.parseInt(SCANNER.nextLine());

        Optional<Producer> producer = ProducerService.findById(newProducerId);
        if (producer.isEmpty()) {
            System.out.println("Producer with id " + newProducerId + " not found.");
            return;
        }

        Anime updatedAnime = Anime.builder()
                .id(id)
                .name(newName)
                .episodes(newEpisodes)
                .producer(producer.get())
                .build();

        AnimeService.update(updatedAnime);
        System.out.println("Anime updated successfully.");
    }

    private static void deleteAnimeById() {
        System.out.println("Enter the id of the anime to delete:");
        int id = Integer.parseInt(SCANNER.nextLine());
        AnimeService.deleteById(id);
        System.out.println("Anime with id " + id + " deleted.");
    }

    private static void findAllProducers() {
        System.out.println("--- All Producers ---");
        List<Producer> allProducers = ProducerService.findAll();
        allProducers.forEach(System.out::println);
    }

    private static void findProducerByName() {
        System.out.println("Enter the name of the producer:");
        String name = SCANNER.nextLine();
        List<Producer> producers = ProducerService.findByName(name);
        producers.forEach(System.out::println);
    }

    private static void findProducerById() {
        System.out.println("Enter the id of the producer:");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Producer> producer = ProducerService.findById(id);
        producer.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Producer with id " + id + " not found.")
        );
    }

    private static void saveProducer() {
        System.out.println("Enter the name of the producer:");
        String name = SCANNER.nextLine();
        Producer producer = Producer.builder().name(name).build();
        ProducerService.save(producer);
        System.out.println("Producer saved successfully.");
    }

    private static void updateProducer() {
        System.out.println("Enter the id of the producer to update:");
        int id = Integer.parseInt(SCANNER.nextLine());
        Optional<Producer> producerOptional = ProducerService.findById(id);

        if (producerOptional.isEmpty()) {
            System.out.println("Producer with id " + id + " not found.");
            return;
        }

        Producer producerToUpdate = producerOptional.get();

        System.out.println("Enter the new name for the producer (current: " + producerToUpdate.getName() + "):");
        String newName = SCANNER.nextLine();
        Producer updatedProducer = Producer.builder()
                .id(id)
                .name(newName)
                .build();
        ProducerService.update(updatedProducer);
        System.out.println("Producer updated successfully.");
    }

    private static void deleteProducerById() {
        System.out.println("Enter the id of the producer to delete:");
        int id = Integer.parseInt(SCANNER.nextLine());
        ProducerService.deleteById(id);
        System.out.println("Producer with id " + id + " deleted.");
    }
}
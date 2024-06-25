package com.example.dndhub;

import com.example.dndhub.models.Duration;
import com.example.dndhub.models.Party;
import com.example.dndhub.models.Tag;
import com.example.dndhub.models.edition.Edition;
import com.example.dndhub.models.edition.EditionType;
import com.example.dndhub.models.place.OnlinePlatform;
import com.example.dndhub.models.place.OnlinePlatformType;
import com.example.dndhub.models.place.Place;
import com.example.dndhub.models.user.Player;
import com.example.dndhub.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * This class is used to populate the database with some initial data when the application starts.
 */
@Component
public class BootstrapData implements CommandLineRunner {

    private final PartyRepository partyRepository;
    private final PlayerRepository playerRepository;
    private final EditionRepository editionRepository;
    private final PlaceRepository placeRepository;
    private final TagRepository tagRepository;

    @Autowired
    public BootstrapData(PartyRepository partyRepository, EditionRepository editionRepository, PlaceRepository placeRepository,
                         PlayerRepository playerRepository,
                         TagRepository tagRepository) {
        this.partyRepository = partyRepository;
        this.editionRepository = editionRepository;
        this.placeRepository = placeRepository;
        this.playerRepository = playerRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(String... args) {
        if (playerRepository.count() > 0) {
            return;
        }
        Edition edition = Edition.builder()
                .name("Dungeons & Dragons 5th Edition")
                .releaseYear(2014)
                .type(EditionType.OFFICIAL)
                .build();
        edition.setId(editionRepository.save(edition).getId());
        HashSet<Place> places = createPlaces();
        HashSet<Player> players = createPlayers();
        HashSet<Party> parties = createParties(players, places);
    }


    private HashSet<Place> createPlaces() {
        HashSet<Place> places = new HashSet<>();
        places.add(OnlinePlatform.builder()
                .name("Roll20")
                .link("https://roll20.net/")
                .iconPath("/platforms/roll20.svg")
                .type(OnlinePlatformType.REGISTERED)
                .parties(new HashSet<>())
                .build());
        places.add(OnlinePlatform.builder()
                .name("Foundry VTT")
                .link("https://foundryvtt.com/")
                .iconPath("/platforms/foundry.svg")
                .type(OnlinePlatformType.REGISTERED)
                .parties(new HashSet<>())
                .build());
        places.add(OnlinePlatform.builder()
                .name("D&D Beyond")
                .link("https://www.dndbeyond.com/")
                .iconPath("/platforms/beyond.svg")
                .type(OnlinePlatformType.REGISTERED)
                .parties(new HashSet<>())
                .build());
        places.add(OnlinePlatform.builder()
                .name("Discord")
                .link("https://discord.com/")
                .iconPath("/platforms/discord.svg")
                .type(OnlinePlatformType.REGISTERED)
                .parties(new HashSet<>())
                .build());

        return new HashSet<>(placeRepository.saveAll(places));
    }

    protected HashSet<Party> createParties(HashSet<Player> players, HashSet<Place> places) {
        HashSet<Party> parties = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            HashSet<Tag> tags = new HashSet<>();
            tags.add(Tag.builder()
                    .value("Adults only")
                    .iconPath("/tags/adults.svg")
                    .build());
            tags.add(Tag.builder()
                    .value("Beginner friendly")
                    .iconPath("/tags/newbies.svg")
                    .build());
            tags.add(Tag.builder()
                    .value("Paid")
                    .iconPath("/tags/paid.svg")
                    .build());
            Party party = Party.builder()
                    .name("Party " + (i + 1))
                    .description("""
                            "Phandalver: Forge of Destiny" is a thrilling Dungeons & Dragons campaign set in the vast and vibrant world of Faerûn.
                            Players embark on an epic journey filled with intrigue, danger, and ancient mysteries waiting to be unraveled.
                                                       \n
                            As adventurers, they'll navigate through intricate dungeons, forge alliances with enigmatic factions, and confront formidable foes as they strive to shape the destiny of the realm.
                                                       \n
                            With rich storytelling, dynamic characters, and a blend of exploration, combat, and role-playing, "Phandalver" promises an immersive gaming experience where every choice matters. Venture forth, brave hero, and carve your legend in the annals of history.""")
                    .maxPlayers(i + 5)
                    .duration(Duration.builder()
                            .startingDate(LocalDate.now().plusDays(i))
                            .endingDate(LocalDate.now().plusDays(i * 7))
                            .build())
                    .participatingPlayers(players.stream().skip(i * 4 + 1).limit(5).collect(Collectors.toSet()))
                    .host(players.stream().skip(i * 4).findFirst().orElseThrow())
                    .playersSaved(new HashSet<>(players))
                    .edition(editionRepository.findById(1).orElseThrow())
                    .place(places.stream().skip(i % 4).findFirst().orElseThrow())
                    .tags(tags.stream().skip(i % 3).limit(2).collect(Collectors.toSet()))
                    .build();
            party.getDuration().setParty(party);
            for (Tag tag : party.getTags())
                tag.setParty(party);

            parties.add(partyRepository.save(party));
        }
        return parties;
    }

    private HashSet<Player> createPlayers() {
        HashSet<Player> players = new HashSet<>();
        players.add(Player.builder()
                .username("john_doe")
                .password("password123")
                .email("john.doeex@am.ple")
                .avatarPath("/avatars/1.png")
                .biography("Avid gamer and tech enthusiast.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("jane_smith")
                .password("password456")
                .email("jane.smith@example.com")
                .avatarPath("/avatars/2.png")
                .biography("Loves outdoor adventures and hiking.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("alice_jones")
                .password("password789")
                .email("alice.jones@example.com")
                .avatarPath("/avatars/3.png")
                .biography("Passionate about cooking and baking.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("bob_brown")
                .password("password101")
                .email("bob.brown@example.com")
                .avatarPath("/avatars/4.png")
                .biography("Enjoys painting and creating art.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("charlie_davis")
                .password("password102")
                .email("charlie.davis@example.com")
                .avatarPath("/avatars/5.png")
                .biography("Fitness trainer and health coach.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("david_ellis")
                .password("password103")
                .email("david.ellis@example.com")
                .avatarPath("/avatars/1.png")
                .biography("Professional photographer.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("eva_frank")
                .password("password104")
                .email("eva.frank@example.com")
                .avatarPath("/avatars/2.png")
                .biography("Software developer and tech geek.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("george_hall")
                .password("password105")
                .email("george.hall@example.com")
                .avatarPath("/avatars/3.png")
                .biography("Music lover and amateur guitarist.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("hannah_king")
                .password("password106")
                .email("hannah.king@example.com")
                .avatarPath("/avatars/4.png")
                .biography("Bookworm and aspiring writer.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("ian_lee")
                .password("password107")
                .email("ian.lee@example.com")
                .avatarPath("/avatars/5.png")
                .biography("History buff and museum guide.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("jack_miller")
                .password("password108")
                .email("jack.miller@example.com")
                .avatarPath("/avatars/1.png")
                .biography("Nature lover and environmentalist.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("kate_nelson")
                .password("password109")
                .email("kate.nelson@example.com")
                .avatarPath("/avatars/2.png")
                .biography("Marketing specialist and social media guru.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("liam_owen")
                .password("password110")
                .email("liam.owen@example.com")
                .avatarPath("/avatars/3.png")
                .biography("Startup founder and entrepreneur.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("maria_perez")
                .password("password111")
                .email("maria.perez@example.com")
                .avatarPath("/avatars/4.png")
                .biography("Travel blogger and photographer.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("nathan_quinn")
                .password("password112")
                .email("nathan.quinn@example.com")
                .avatarPath("/avatars/5.png")
                .biography("Pet lover and animal rescue volunteer.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("olivia_roberts")
                .password("password113")
                .email("olivia.roberts@example.com")
                .avatarPath("/avatars/1.png")
                .biography("DIY enthusiast and handyman.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("peter_smith")
                .password("password114")
                .email("peter.smith@example.com")
                .avatarPath("/avatars/2.png")
                .biography("Gamer and eSports competitor.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("quinn_taylor")
                .password("password115")
                .email("quinn.taylor@example.com")
                .avatarPath("/avatars/3.png")
                .biography("Fitness enthusiast and marathon runner.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("rachel_umpstead")
                .password("password116")
                .email("rachel.umpstead@example.com")
                .avatarPath("/avatars/4.png")
                .biography("Fashion designer and stylist.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("steven_valdez")
                .password("password117")
                .email("steven.valdez@example.com")
                .avatarPath("/avatars/5.png")
                .biography("Science teacher and astronomy fan.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("arthas")
                .password("password118")
                .email("arthas.some@exmpl.com")
                .avatarPath("/avatars/1.png")
                .biography("Arthas is a great player")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("chears")
                .password("password119")
                .email("chears.some@exmpl.com")
                .avatarPath("/avatars/2.png")
                .biography("Chears thinks he is a great player")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("dorin")
                .password("password120")
                .email("dorin.dorin@exmpl.com")
                .avatarPath("/avatars/3.png")
                .biography("Just a player")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("kali")
                .password("password121")
                .email("kali.k@exmpl.com")
                .avatarPath("/avatars/4.png")
                .biography(null)
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("lucy_bell")
                .password("password122")
                .email("lucy.bell@example.com")
                .avatarPath("/avatars/5.png")
                .biography("Art lover and museum curator.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("max_robinson")
                .password("password123")
                .email("max.robinson@example.com")
                .avatarPath("/avatars/1.png")
                .biography("Tech enthusiast and software developer.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("sophie_garcia")
                .password("password124")
                .email("sophie.garcia@example.com")
                .avatarPath("/avatars/2.png")
                .biography("Nature photographer and wildlife conservationist.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("alex_turner")
                .password("password125")
                .email("alex.turner@example.com")
                .avatarPath("/avatars/3.png")
                .biography("Music producer and DJ.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("mia_davis")
                .password("password126")
                .email("mia.davis@example.com")
                .avatarPath("/avatars/4.png")
                .biography("Fitness coach and personal trainer.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("noah_clark")
                .password("password127")
                .email("noah.clark@example.com")
                .avatarPath("/avatars/5.png")
                .biography("Adventure traveler and explorer.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("emily_hall")
                .password("password128")
                .email("emily.hall@example.com")
                .avatarPath("/avatars/1.png")
                .biography("Fashion blogger and stylist.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("liam_wilson")
                .password("password129")
                .email("liam.wilson@example.com")
                .avatarPath("/avatars/2.png")
                .biography("Entrepreneur and startup mentor.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("ava_morris")
                .password("password130")
                .email("ava.morris@example.com")
                .avatarPath("/avatars/3.png")
                .biography("Book lover and literature enthusiast.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("owen_jackson")
                .password("password131")
                .email("owen.jackson@example.com")
                .avatarPath("/avatars/4.png")
                .biography("Historian and researcher.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("ella_thomas")
                .password("password132")
                .email("ella.thomas@example.com")
                .avatarPath("/avatars/5.png")
                .biography("DIY enthusiast and home decorator.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("mia_thompson")
                .password("password133")
                .email("mia.thompson@example.com")
                .avatarPath("/avatars/2.png")
                .biography("Art student and aspiring painter.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("henry_carter")
                .password("password134")
                .email("henry.carter@example.com")
                .avatarPath("/avatars/3.png")
                .biography("Music enthusiast and guitarist.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("ella_gomez")
                .password("password135")
                .email("ella.gomez@example.com")
                .avatarPath("/avatars/4.png")
                .biography("Fitness instructor and yoga practitioner.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("leo_morgan")
                .password("password136")
                .email("leo.morgan@example.com")
                .avatarPath("/avatars/1.png")
                .biography("Travel enthusiast and adventure seeker.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());

        players.add(Player.builder()
                .username("amelia_reed")
                .password("password137")
                .email("amelia.reed@example.com")
                .avatarPath("/avatars/5.png")
                .biography("Fashion designer and stylist.")
                .registrationDate(LocalDate.now())
                .hostedParties(new HashSet<>())
                .savedParties(new HashSet<>())
                .participatingParties(new HashSet<>())
                .build());
        return new HashSet<>(playerRepository.saveAll(players));
    }
}
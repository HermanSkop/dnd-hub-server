package com.example.dndhub;

import com.example.dndhub.dtos.*;
import com.example.dndhub.models.edition.EditionType;
import com.example.dndhub.models.place.OnlinePlatformType;
import com.example.dndhub.services.EditionService;
import com.example.dndhub.services.PartyService;
import com.example.dndhub.services.PlaceService;
import com.example.dndhub.services.PlayerService;
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

    private final PartyService partyService;
    private final PlayerService playerService;
    private final EditionService editionService;
    private final PlaceService placeService;

    @Autowired
    public BootstrapData(PartyService partyService, PlayerService playerService, EditionService editionService, PlaceService placeService) {
        this.partyService = partyService;
        this.playerService = playerService;
        this.editionService = editionService;
        this.placeService = placeService;
    }

    @Override
    public void run(String... args) {
        EditionDto editionDto = EditionDto.builder()
                .name("Dungeons & Dragons 5th Edition")
                .releaseYear(2014)
                .type(EditionType.OFFICIAL)
                .build();
        editionService.saveEdition(editionDto);
        HashSet<PlaceDto> places = createPlaces();
        HashSet<PlayerDto> players = createPlayers();
        HashSet<PartyDto> parties = createParties(players, places);

    }

    private HashSet<PlaceDto> createPlaces() {
        HashSet<PlaceDto> places = new HashSet<>();
        places.add(new OnlinePlatformDto(1, "Roll20", "https://roll20.net/", "/platforms/roll20.svg",
                OnlinePlatformType.REGISTERED, new HashSet<>()));
        places.add(new OnlinePlatformDto(2, "Foundry VTT", "https://foundryvtt.com/", "/platforms/foundry.svg",
                OnlinePlatformType.REGISTERED, new HashSet<>()));
        places.add(new OnlinePlatformDto(3, "D&D Beyond", "https://www.dndbeyond.com/", "/platforms/beyond.svg",
                OnlinePlatformType.REGISTERED, new HashSet<>()));
        places.add(new OnlinePlatformDto(4, "Discord", "https://discord.com/", "/platforms/discord.svg",
                OnlinePlatformType.REGISTERED, new HashSet<>()));

        for (PlaceDto place : places) {
            place.setId(placeService.savePlace(place));
        }

        return places;
    }

    private HashSet<PartyDto> createParties(HashSet<PlayerDto> players, HashSet<PlaceDto> places) {
        for (int i = 0; i < 10; i++) {
            PartyDto partyDto = PartyDto.builder()
                    .name("Party " + (i + 1))
                    .description("""
                            "Phandalver: Forge of Destiny" is a thrilling Dungeons & Dragons campaign set in the vast and vibrant world of Faerûn.
                            Players embark on an epic journey filled with intrigue, danger, and ancient mysteries waiting to be unraveled.
                                                       \n
                            As adventurers, they'll navigate through intricate dungeons, forge alliances with enigmatic factions, and confront formidable foes as they strive to shape the destiny of the realm.
                                                       \n
                            With rich storytelling, dynamic characters, and a blend of exploration, combat, and role-playing, "Phandalver" promises an immersive gaming experience where every choice matters. Venture forth, brave hero, and carve your legend in the annals of history.""")
                    .maxPlayers(i + 5)
                    .duration(PartyDto.DurationDto.builder()
                            .startingDate(LocalDate.now())
                            .endingDate(LocalDate.now().plusDays(i + 1))
                            .build())
                    .participatingPlayers(players.stream().skip(i * 4 + 1).limit(5).collect(Collectors.toSet()))
                    .host(players.stream().skip(i * 4).findFirst().orElseThrow())
                    .playersSaved(new HashSet<>(players))
                    .edition(editionService.getEditionById(1))
                    .place(places.stream().skip(i % 4).findFirst().orElseThrow())
                    .build();

            partyService.saveParty(partyDto);
        }

        return new HashSet<>(partyService.getAllPartiesDeep());
    }

    private PlayerDto createPlayer(String username, String password, String email, String avatarPath, String biography) {
        return PlayerDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .registrationDate(LocalDate.now())
                .avatarPath(avatarPath)
                .biography(biography)
                .build();
    }

    private HashSet<PlayerDto> createPlayers() {
        HashSet<PlayerDto> players = new HashSet<>();
        players.add(createPlayer("john_doe", "password123", "john.doeex@am.ple", "/avatars/1.png", "Avid gamer and tech enthusiast."));
        players.add(createPlayer("jane_smith", "password456", "jane.smith@example.com", "/avatars/2.png", "Loves outdoor adventures and hiking."));
        players.add(createPlayer("alice_jones", "password789", "alice.jones@example.com", "/avatars/3.png", "Passionate about cooking and baking."));
        players.add(createPlayer("bob_brown", "password101", "bob.brown@example.com", "/avatars/4.png", "Enjoys painting and creating art."));
        players.add(createPlayer("charlie_davis", "password102", "charlie.davis@example.com", "/avatars/5.png", "Fitness trainer and health coach."));
        players.add(createPlayer("david_ellis", "password103", "david.ellis@example.com", "/avatars/1.png", "Professional photographer."));
        players.add(createPlayer("eva_frank", "password104", "eva.frank@example.com", "/avatars/2.png", "Software developer and tech geek."));
        players.add(createPlayer("george_hall", "password105", "george.hall@example.com", "/avatars/3.png", "Music lover and amateur guitarist."));
        players.add(createPlayer("hannah_king", "password106", "hannah.king@example.com", "/avatars/4.png", "Bookworm and aspiring writer."));
        players.add(createPlayer("ian_lee", "password107", "ian.lee@example.com", "/avatars/5.png", "History buff and museum guide."));
        players.add(createPlayer("jack_miller", "password108", "jack.miller@example.com", "/avatars/1.png", "Nature lover and environmentalist."));
        players.add(createPlayer("kate_nelson", "password109", "kate.nelson@example.com", "/avatars/2.png", "Marketing specialist and social media guru."));
        players.add(createPlayer("liam_owen", "password110", "liam.owen@example.com", "/avatars/3.png", "Startup founder and entrepreneur."));
        players.add(createPlayer("maria_perez", "password111", "maria.perez@example.com", "/avatars/4.png", "Travel blogger and photographer."));
        players.add(createPlayer("nathan_quinn", "password112", "nathan.quinn@example.com", "/avatars/5.png", "Pet lover and animal rescue volunteer."));
        players.add(createPlayer("olivia_roberts", "password113", "olivia.roberts@example.com", "/avatars/1.png", "DIY enthusiast and handyman."));
        players.add(createPlayer("peter_smith", "password114", "peter.smith@example.com", "/avatars/2.png", "Gamer and eSports competitor."));
        players.add(createPlayer("quinn_taylor", "password115", "quinn.taylor@example.com", "/avatars/3.png", "Fitness enthusiast and marathon runner."));
        players.add(createPlayer("rachel_umpstead", "password116", "rachel.umpstead@example.com", "/avatars/4.png", "Fashion designer and stylist."));
        players.add(createPlayer("steven_valdez", "password117", "steven.valdez@example.com", "/avatars/5.png", "Science teacher and astronomy fan."));
        players.add(createPlayer("arthas", "password118", "arthas.some@exmpl.com", "/avatars/1.png", "Arthas is a great player"));
        players.add(createPlayer("chears", "password119", "chears.some@exmpl.com", "/avatars/2.png", "Chears thinks he is a great player"));
        players.add(createPlayer("dorin", "password120", "dorin.dorin@exmpl.com", "/avatars/3.png", "Just a player"));
        players.add(createPlayer("kali", "password121", "kali.k@exmpl.com", "/avatars/4.png", null));
        players.add(createPlayer("lucy_bell", "password122", "lucy.bell@example.com", "/avatars/5.png", "Art lover and museum curator."));
        players.add(createPlayer("max_robinson", "password123", "max.robinson@example.com", "/avatars/1.png", "Tech enthusiast and software developer."));
        players.add(createPlayer("sophie_garcia", "password124", "sophie.garcia@example.com", "/avatars/2.png", "Nature photographer and wildlife conservationist."));
        players.add(createPlayer("alex_turner", "password125", "alex.turner@example.com", "/avatars/3.png", "Music producer and DJ."));
        players.add(createPlayer("mia_davis", "password126", "mia.davis@example.com", "/avatars/4.png", "Fitness coach and personal trainer."));
        players.add(createPlayer("noah_clark", "password127", "noah.clark@example.com", "/avatars/5.png", "Adventure traveler and explorer."));
        players.add(createPlayer("emily_hall", "password128", "emily.hall@example.com", "/avatars/1.png", "Fashion blogger and stylist."));
        players.add(createPlayer("liam_wilson", "password129", "liam.wilson@example.com", "/avatars/2.png", "Entrepreneur and startup mentor."));
        players.add(createPlayer("ava_morris", "password130", "ava.morris@example.com", "/avatars/3.png", "Book lover and literature enthusiast."));
        players.add(createPlayer("owen_jackson", "password131", "owen.jackson@example.com", "/avatars/4.png", "Historian and researcher."));
        players.add(createPlayer("ella_thomas", "password132", "ella.thomas@example.com", "/avatars/5.png", "DIY enthusiast and home decorator."));
        players.add(createPlayer("mia_thompson", "password133", "mia.thompson@example.com", "/avatars/2.png", "Art student and aspiring painter."));
        players.add(createPlayer("henry_carter", "password134", "henry.carter@example.com", "/avatars/3.png", "Music enthusiast and guitarist."));
        players.add(createPlayer("ella_gomez", "password135", "ella.gomez@example.com", "/avatars/4.png", "Fitness instructor and yoga practitioner."));
        players.add(createPlayer("leo_morgan", "password136", "leo.morgan@example.com", "/avatars/1.png", "Travel enthusiast and adventure seeker."));
        players.add(createPlayer("amelia_reed", "password137", "amelia.reed@example.com", "/avatars/5.png", "Fashion designer and stylist."));


        for (PlayerDto player : players) {
            player.setId(playerService.savePlayer(player));
        }

        return players;
    }
}
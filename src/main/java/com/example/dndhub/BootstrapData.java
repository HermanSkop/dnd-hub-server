package com.example.dndhub;

import com.example.dndhub.dtos.party.PartyDto;
import com.example.dndhub.dtos.user.PlayerDto;
import com.example.dndhub.services.PartyService;
import com.example.dndhub.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class BootstrapData implements CommandLineRunner {

    private final PartyService partyService;
    private final PlayerService playerService;

    @Autowired
    public BootstrapData(PartyService partyService, PlayerService playerService) {
        this.partyService = partyService;
        this.playerService = playerService;
    }

    @Override
    public void run(String... args) {
        HashSet<PlayerDto> players = createPlayers();
        HashSet<PartyDto> parties = createParties(players);

    }

    private HashSet<PartyDto> createParties(HashSet<PlayerDto> players) {
        for (int i = 0; i < 5; i++) {
            PartyDto partyDto = PartyDto.builder()
                    .name("Party " + (i + 1))
                    .description("Description " + (i + 1))
                    .maxPlayers(i + 5)
                    .duration(PartyDto.DurationDto.builder()
                            .startingDate(LocalDate.now())
                            .endingDate(LocalDate.now().plusDays(i + 1))
                            .build())
                    .participatingPlayers(players.stream().skip(i*4+1).limit(5).collect(Collectors.toSet()))
                    .host(players.stream().skip(i*4).findFirst().orElseThrow())
                    .playersSaved(new HashSet<>(players))
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
        players.add(createPlayer("john_doe", "password123", "john.doe@example.com", "/avatars/1.png", "Avid gamer and tech enthusiast."));
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


        for (PlayerDto player : players) {
            player.setId(playerService.savePlayer(player));
        }

        return players;
    }
}
package com.tourney.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourney.models.Tournament;
import com.webforj.Environment;
import com.webforj.utilities.Assets;

public class TournamentService {
    
    private int serial = 2;
    private static final String FILE_PATH = Assets.resolveWebServerUrl("ws://db.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void addTournament(Tournament newTournament) {
        List<Tournament> tournaments = getTournaments();

        Optional<Tournament> existingTournament = tournaments.stream()
            .filter(t -> t.getId() == newTournament.getId())
            .findFirst();

        if (existingTournament.isPresent()) {
            tournaments.replaceAll(t -> t.getId() == newTournament.getId() ? newTournament : t);
        } else {
            newTournament.setId(serial++);
            tournaments.add(newTournament);
        }

        saveTournaments(tournaments);
    }

    public List<Tournament> getTournaments() {
        Type listType = new TypeToken<List<Tournament>>() {}.getType();
        String content = Assets.contentOf(FILE_PATH);
        List<Tournament> tournaments = gson.fromJson(content, listType);
        return tournaments != null ? tournaments : new ArrayList<Tournament>();
    }

    public Tournament getTournament(Integer id) {
        return getTournaments().stream()
            .filter(t -> t.getId() == id)
            .findFirst()
            .orElse(null);
    }

    private void saveTournaments(List<Tournament> tournaments) {
        try {
            File file = new File(Environment.getCurrent().getClass().getClassLoader().getResource(Assets.resolveWebServerUrl("ws://db.json")).toURI());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
                gson.toJson(tournaments, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

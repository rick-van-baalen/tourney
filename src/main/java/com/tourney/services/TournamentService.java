package com.tourney.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tourney.models.Group;
import com.tourney.models.Participant;
import com.tourney.models.Tournament;
import com.webforj.Environment;
import com.webforj.environment.ObjectTable;
import com.webforj.utilities.Assets;

public class TournamentService {
    
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_PATH = Assets.resolveWebServerUrl("ws://db.json");
    private static final Boolean ENABLE_DB_UPDATES = true;

    public void addTournament(Tournament newTournament) {
        List<Tournament> tournaments = getTournaments();

        Optional<Tournament> existingTournament = tournaments.stream()
            .filter(t -> t.getId() == newTournament.getId())
            .findFirst();

        if (existingTournament.isPresent()) {
            tournaments.replaceAll(t -> t.getId() == newTournament.getId() ? newTournament : t);
        } else {
            newTournament.setId(getNextTournamentId());
            tournaments.add(newTournament);
        }

        if (ENABLE_DB_UPDATES) saveTournaments(tournaments);
    }

    public List<Tournament> getTournaments() {
        Type listType = new TypeToken<List<Tournament>>() {}.getType();
        String content = Assets.contentOf(FILE_PATH);
        List<Tournament> tournaments = gson.fromJson(content, listType);
        return tournaments != null ? tournaments : new ArrayList<Tournament>();
    }

    public Tournament getTournament(Integer id) {
        Tournament tournament = getTournaments().stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .orElse(null);
    
        if (tournament != null) updateGroupParticipants(tournament);
    
        return tournament;
    }

    private void updateGroupParticipants(Tournament tournament) {
        Map<Integer, Participant> participantMap = tournament.getParticipants().stream()
            .collect(Collectors.toMap(Participant::getId, p -> p));

        for (Group group : tournament.getGroups()) {
            for (Participant participant : group.getParticipants()) {
                Participant fullParticipant = participantMap.get(participant.getId());
                if (fullParticipant != null) participant.setName(fullParticipant.getName());
            }
        }
    }

    public void removeParticipant(int tournamentId, int participantId) {
        List<Tournament> tournaments = getTournaments();

        Optional<Tournament> optional = tournaments.stream()
            .filter(t -> t.getId() == tournamentId)
            .findFirst();
        
        if (optional.isPresent()) {
            Tournament tournament = optional.get();
            
            Participant pt = tournament.getParticipants().stream()
                .filter(t -> t.getId() == participantId)
                .findFirst()
                .orElse(null);
            
            if (pt != null) {
                tournament.getParticipants().remove(pt);
                if (ENABLE_DB_UPDATES) saveTournaments(tournaments);
            }
        }
    }

    public Participant editParticipant(int tournamentId, int participantId, Participant newParticipantData) {
        List<Tournament> tournaments = getTournaments();
        Participant participant = null;

        Optional<Tournament> optional = tournaments.stream()
            .filter(t -> t.getId() == tournamentId)
            .findFirst();
        
        if (optional.isPresent()) {
            Tournament tournament = optional.get();
            
            participant = tournament.getParticipants().stream()
                .filter(t -> t.getId() == participantId)
                .findFirst()
                .orElse(null);
            
            if (participant != null) {
                participant.setName(newParticipantData.getName());
                if (ENABLE_DB_UPDATES) saveTournaments(tournaments);
            }
        }

        return participant;
    }

    public Participant addParticipant(int tournamentId, Participant participant) {
        List<Tournament> tournaments = getTournaments();

        Optional<Tournament> optional = tournaments.stream()
            .filter(t -> t.getId() == tournamentId)
            .findFirst();
        
        if (optional.isPresent()) {
            Tournament tournament = optional.get();
            
            participant.setId(getNextParticipantId());
            tournament.getParticipants().add(participant);

            if (ENABLE_DB_UPDATES) saveTournaments(tournaments);
        }

        return participant;
    }

    public Group addGroup(int tournamentId, Group group) {
        List<Tournament> tournaments = getTournaments();

        Optional<Tournament> optional = tournaments.stream()
            .filter(t -> t.getId() == tournamentId)
            .findFirst();
        
        if (optional.isPresent()) {
            Tournament tournament = optional.get();
            
            group.setId(getNextGroupId());
            tournament.getGroups().add(group);

            if (ENABLE_DB_UPDATES) saveTournaments(tournaments);
        }

        return group;
    }

    public Group editGroup(int tournamentId, int groupId, Group newGroupData) {
        List<Tournament> tournaments = getTournaments();
        Group group = null;

        Optional<Tournament> optional = tournaments.stream()
            .filter(t -> t.getId() == tournamentId)
            .findFirst();
        
        if (optional.isPresent()) {
            Tournament tournament = optional.get();
            
            group = tournament.getGroups().stream()
                .filter(t -> t.getId() == groupId)
                .findFirst()
                .orElse(null);
            
            if (group != null) {
                group.setName(newGroupData.getName());
                group.setParticipants(newGroupData.getParticipants());
                if (ENABLE_DB_UPDATES) saveTournaments(tournaments);
            }
        }

        return group;
    }

    public void removeGroup(int tournamentId, int groupId) {
        List<Tournament> tournaments = getTournaments();

        Optional<Tournament> optional = tournaments.stream()
            .filter(t -> t.getId() == tournamentId)
            .findFirst();
        
        if (optional.isPresent()) {
            Tournament tournament = optional.get();
            
            Group group = tournament.getGroups().stream()
                .filter(t -> t.getId() == groupId)
                .findFirst()
                .orElse(null);
            
            if (group != null) {
                tournament.getGroups().remove(group);
                if (ENABLE_DB_UPDATES) saveTournaments(tournaments);
            }
        }
    }

    public void generateSchedule(int tournamentId) {
        Tournament tournament = getTournament(tournamentId);
    }

    private Integer getNextTournamentId() {
        Integer serial = null;

        if (ObjectTable.contains("TOURNAMENT_SERIAL")) {
            serial = (Integer) ObjectTable.get("TOURNAMENT_SERIAL");
            serial++;
        } else {
            serial = 2;
        }
        
        ObjectTable.put("TOURNAMENT_SERIAL", serial);

        return serial;
    }

    private Integer getNextParticipantId() {
        Integer serial = null;

        if (ObjectTable.contains("PARTICIPANT_SERIAL")) {
            serial = (Integer) ObjectTable.get("PARTICIPANT_SERIAL");
            serial++;
        } else {
            serial = 9;
        }
        
        ObjectTable.put("PARTICIPANT_SERIAL", serial);

        return serial;
    }

    private Integer getNextGroupId() {
        Integer serial = null;

        if (ObjectTable.contains("GROUP_SERIAL")) {
            serial = (Integer) ObjectTable.get("GROUP_SERIAL");
            serial++;
        } else {
            serial = 3;
        }
        
        ObjectTable.put("GROUP_SERIAL", serial);

        return serial;
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

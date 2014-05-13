package net.gamersbug.main.object;

public class ServerObject {

    private String category, map, game;

    private Integer id, number, onlineState, players, playing;

    public ServerObject(Integer id, String category, Integer number, String map, String game, Integer onlineState, Integer players, Integer playing) {

        this.id = id;
        this.category = category;
        this.number = number;
        this.map = map;
        this.game = game;
        this.onlineState = onlineState;
        this.players = players;
        this.playing = playing;

    }

    public Integer getId() {

        return this.id;

    }

    public String getCategory() {

        return this.category;

    }

    public Integer getNumber() {

        return this.number;

    }

    public String getMap() {

        return this.map;

    }

    public String getGame() {

        return this.game;

    }

    public boolean getOnlineState() {

        return this.onlineState.equals(1);

    }

    public Integer getPlayers() {

        return this.players;

    }

    public boolean isPlaying() {

        return this.playing.equals(1);

    }

}

package net.ivango.liderboard.types;

public class Player {
    private int place, score, id;
    private String name, avatarURL;

    public Player(int id, String name, String avatarURL) {
        this.id = id;
        this.name = name;
        this.avatarURL = avatarURL;
    }

    /* boilerplate code: */
    public int getPlace() { return place; }
    public void setPlace(int place) { this.place = place; }

    public int getScore() { return score;}
    public void setScore(int score) { this.score = score; }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getAvatarURL() { return avatarURL; }
}

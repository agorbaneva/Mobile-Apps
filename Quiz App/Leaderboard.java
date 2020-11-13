package alyss.example.quizapp;

public class Leaderboard {
    private String name;
    private int score;

    public Leaderboard(String name, int score){
        this.name = name;
        this.score = score;
    }

    public int getScore(){
        return score;
    }
    public String getName(){
        return name;
    }

    public int compareTo(Leaderboard hi){
        if(this.getScore()<hi.getScore()) return -1;
        else if (this.getScore()>hi.getScore()) return 1;
        else return 0;
    }

    public String toString(){
        return("name: " + name + " score: " + score);

    }
}

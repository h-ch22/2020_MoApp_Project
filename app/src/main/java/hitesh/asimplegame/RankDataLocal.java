package hitesh.asimplegame;

public class RankDataLocal {
    public String nickname;
    public String level;
    public int score;

    public RankDataLocal(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname(){
        return nickname;
    }

    public int getScore(){
        return score;
    }
}

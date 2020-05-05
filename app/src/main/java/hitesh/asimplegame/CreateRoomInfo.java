package hitesh.asimplegame;

public class CreateRoomInfo {
    private static String player1, player2, level, status, roomInfo;
    private static int player1_score, player2_score;

    public void setLevel(String level){
        this.level = level;
    }

    public void setPlayer1(String player1){
        this.player1 = player1;
    }

    public void setPlayer2(String player2){
        this.player2 = player2;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setRoomInfo(String roomInfo){
        this.roomInfo = roomInfo;
    }

    public void setPlayer1Score(int player1_score){
        this.player1_score = player1_score;
    }

    public void setPlayer2Score(int player2_score){
        this.player2_score = player2_score;
    }

    public String getPlayer1(){
        return player1;
    }

    public String getPlayer2(){
        return player2;
    }

    public String getLevel(){
        return level;
    }

    public String getRoomInfo(){
        return roomInfo;
    }
}

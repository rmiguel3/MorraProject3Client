import java.io.Serializable;

public class MorraInfo implements Serializable{

    private int p1Points;
    private int p2Points;
    private String p1Plays;
    private String p2Plays;
    private int pNum;
    private String playerString;

    public String getPlayerString() {
        return playerString;
    }

    public void setPlayerString(String playerString) {
        this.playerString = playerString;
    }

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public int getP1Points() {
        return p1Points;
    }

    public void setP1Points(int p1Points) {
        this.p1Points = p1Points;
    }

    public int getP2Points() {
        return p2Points;
    }

    public void setP2Points(int p2Points) {
        this.p2Points = p2Points;
    }

    public String getP1Plays() {
        return p1Plays;
    }

    public void setP1Plays(String p1Plays) {
        this.p1Plays = p1Plays;
    }

    public String getP2Plays() {
        return p2Plays;
    }

    public void setP2Plays(String p2Plays) {
        this.p2Plays = p2Plays;
    }

}

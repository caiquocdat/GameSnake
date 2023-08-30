package hehe.caiquocdat.snakegame.model;

public class HistoryModel {
    private int id;
    private int point;

    public HistoryModel(int id, int point) {
        this.id = id;
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

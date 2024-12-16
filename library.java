import java.io.*;


class Library implements Serializable {
    private int Bid;
    private String Bname;
    private String Aname;
    private int Qua;

    public Library() {
        Bid = 0;
        Bname = "";
        Aname = "";
        Qua = 0;
    }

    public Library(int Bid, String Bname, String Aname, int Qua) {
        this.Bid = Bid;
        this.Bname = Bname;
        this.Aname = Aname;
        this.Qua = Qua;
    }

    public int getBid() {
        return Bid;
    }

    public String getBname() {
        return Bname;
    }

    public String getAname() {
        return Aname;
    }

    public int getQua() {
        return Qua;
    }

    public void setQua(int Qua) {
        this.Qua = Qua;
    }
}


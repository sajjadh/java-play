package Models;

public class Reader {


    public String getReaderID() {
        return readerID;
    }

    public void setReaderID(String readerID) {
        this.readerID = readerID;
    }

    public int getReaderMobile() { return readerMobile; }

    public void setReaderMobile(int readerMobile) { this.readerMobile = readerMobile;
    }

    public String getReaderEmail() {
        return readerEmail;
    }

    public void setReaderEmail(String email) {
        this.readerEmail = readerEmail;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    private String readerID;
    private String readerName;
    private int readerMobile;
    private String readerEmail;


    public Reader(){
            }
    public Reader(String readerID, String readerName, int readerMobile, String readerEmail){
        this.readerID = readerID;
        this.readerMobile = readerMobile;
        this.readerEmail = readerEmail;
        this.readerName = readerName;
    }


    //ArrayList <Reader> readerList = new ArrayList<Reader>();


//
//    //display item reader details
//    public void readerDetails(int id){
//
//    }



}

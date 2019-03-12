package Models;

public class DVD extends LibraryItem {


    public String getAvailableSubtitles() {
        return availableSubtitles;
    }

    public void setAvailableSubtitles(String availableSubtitles) {
        this.availableSubtitles = availableSubtitles;
    }

    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(String availableLanguages) {
        this.availableLanguages = availableLanguages;
    }



    private String availableSubtitles;
    private String actor;
    private String producer;
    private String availableLanguages;

    public DVD(){
    }

    public DVD(int isbn, String title, String availableSubtitles, String producer, String availableLanguages, String sector,
               String actor, DateTime publicationDate, boolean available, DateTime borrowedDateTime, DateTime returnedDateTime,
               Reader reader, String type){

        super(isbn, sector, title, publicationDate, available, borrowedDateTime,returnedDateTime, reader, type);

        this.actor = actor;
        this.availableLanguages = availableLanguages;
        this.producer = producer;
        this.availableSubtitles = availableSubtitles;
    }


    @Override
    //obtain dvd details and availability
    public void getItemDetails() {

    }

    @Override
    //update returned borrowed items
    // status -> true, readerId -> null
    public void returnItem() {

    }

    @Override
    //obtain readerDetails
    // pass readerId
    public void readerDetails() {

    }

    @Override
    // update borrowing item
    // set readerId -> null, status -> false
    public void borrowItem() {

    }


}

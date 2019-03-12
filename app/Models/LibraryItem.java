package Models;

public abstract class LibraryItem {


    public LibraryItem() { }

    public int getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public DateTime getPublicationDate() { return publicationDate;}

    public boolean getAvailable() {
        return available;
    }

    public DateTime getBorrowedDateTime() {
        return borrowedDateTime;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublicationDate(DateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setAvailable(boolean status) {
        this.available = status;
    }

    public void setBorrowedDateTime(DateTime borrowedDateTime) {
        this.borrowedDateTime = borrowedDateTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public DateTime getReturnedDateTime() {
        return returnedDateTime;
    }

    public void setReturnedDateTime(DateTime returnedDateTime) {
        this.returnedDateTime = returnedDateTime;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }


    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //common attributes of book and dvd
    private int isbn;
    private String title;
    private String sector;
    private DateTime publicationDate;
    private boolean available;
    private DateTime borrowedDateTime;
    private DateTime returnedDateTime;
    private Reader reader;
    private String type;

    public LibraryItem(int isbn, String sector, String title, DateTime publicationDate, boolean available, DateTime borrowedDateTime,
                       DateTime returnedDateTime, Reader reader, String type){
        this.isbn = isbn;
        this.available = available;
        this. borrowedDateTime = borrowedDateTime;
        this.title = title;
        this.sector = sector;
        this.publicationDate = publicationDate;
        this.borrowedDateTime = borrowedDateTime;
        this.returnedDateTime = returnedDateTime;
        this.reader =  reader;
        this.type = type;
    }


    //abstract methods
    public abstract void getItemDetails();
    public abstract void returnItem();
    public abstract void readerDetails();
    public abstract void borrowItem();



}

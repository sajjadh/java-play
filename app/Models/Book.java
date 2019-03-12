package Models;

public class Book extends LibraryItem {


    public Book() {
        super();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getNo_Of_Pages() {
        return no_Of_Pages;
    }

    public void setNo_Of_Pages(int no_Of_Pages) {
        this.no_Of_Pages = no_Of_Pages;
    }


    private String author;
    private String publisher;
    private int no_Of_Pages;




    public Book(int isbn, String title, String sector, DateTime publicationDate, String author, String publisher, int no_Of_Pages,
                Reader reader, boolean available, DateTime borrowedDateTime, DateTime returnedDateTime, String type){

        super(isbn, sector, title, publicationDate, available, borrowedDateTime,returnedDateTime,reader, type);

        this.author = author;
        this.publisher = publisher;
        this.no_Of_Pages = no_Of_Pages;

    }


    @Override
    //Checking book status and details
    public void getItemDetails() {

    }

    @Override
    //Change Status and readerId -> null
    public void returnItem() {

    }

    @Override
    //obtain readers details for borrowed books
    public void readerDetails() {

    }

    @Override
    //SetReaderId
    //Change book status -> false
    public void borrowItem() {
    }
}

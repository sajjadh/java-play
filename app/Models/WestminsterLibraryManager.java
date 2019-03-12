package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WestminsterLibraryManager implements LibraryManager {
    Scanner input = new Scanner(System.in);

    public static List<LibraryItem> libraryItemsList = new ArrayList<>();
    static List<Reader> borrowItemQueue = new ArrayList<>();

    @Override
    //Add Item to arrayList
    public String addItem(LibraryItem item) {
        String message = null;
        for (int x = 0; x < libraryItemsList.size(); x++) {
            if (libraryItemsList.get(x).getIsbn() == item.getIsbn()) {
                message = "duplicate";
                System.out.println("item already exist - " + libraryItemsList.size());
                return message;
            }
        }
        int bookCount = 0;
        int dvdCount = 0;
        for (int x = 0; x < libraryItemsList.size(); x++) {
            boolean isBook = libraryItemsList.get(x) instanceof Book;
            if (isBook == true) {
                ++bookCount;
            } else {
                ++dvdCount;
            }
        }
//        bookCount = 150;
//        dvdCount = 50;
        boolean isBook = item instanceof Book;
        if (isBook == true) {
            if (bookCount <= 150) {
                message = "Book Added";
                libraryItemsList.add(item);
                System.out.println(libraryItemsList.size() + " => A BOOK Added successfully");

            } else if (bookCount == 150) {
                message = "Book not added";
                System.out.println("No space to add a book,");
            }

        }
        if (isBook == false) {
            if (dvdCount <= 49) {
                message = "DVD Added";
                libraryItemsList.add(item);
                System.out.println(libraryItemsList.size() + " => A DVD Added successfully");

            } else if (dvdCount == 50) {
                message = "DVD not added";
                System.out.println("No space to add a DVD,");
            }
        }
        return message;
    }


    @Override
    //Borrow Library Item
    public String borrowItem_update(Reader reader, int jsonIsbn, DateTime borrowedDate) {
        String availability = null;

        for (int x = 0; x < libraryItemsList.size(); x++) {

            if ((libraryItemsList.get(x).getIsbn() == jsonIsbn)) {
                System.out.println("Item found in database");

                //checking item status
                if (libraryItemsList.get(x).getAvailable() == true) {
                    availability = "Available";
                    libraryItemsList.get(x).setAvailable(false);
                    System.out.println(libraryItemsList.get(x).getIsbn() + "  - Item Is Available");

                    //setting reader details
                    libraryItemsList.get(x).setReader(reader);
                    System.out.println(libraryItemsList.get(x).getReader().getReaderEmail());

                    //setting borrowed date
                    libraryItemsList.get(x).setBorrowedDateTime(borrowedDate);
                    System.out.println(libraryItemsList.get(x).getBorrowedDateTime().getDay());


                    Boolean isBook = libraryItemsList.get(x) instanceof Book;

                    int year = libraryItemsList.get(x).getBorrowedDateTime().getYear();
                    int month = libraryItemsList.get(x).getBorrowedDateTime().getMonth();
                    int day = libraryItemsList.get(x).getBorrowedDateTime().getDay();


                    int Return_hour = libraryItemsList.get(x).getBorrowedDateTime().getHour();
                    int Return_Day = 0;
                    int Return_Month = 0;
                    int Return_Year = 0;

                    //Calculating return date
                    //FOR BOOK ITEMS
                    if (isBook = true) {
                        if ((day + 7) > 30) {
                            Return_Day = ((day + 7) - 30);
                            if (((month + 1)) > 12) {
                                Return_Month = (((month + 1) - 12));
                                Return_Year = ((year + 1));
                            } else {
                                Return_Month = ((month + 1));
                                Return_Year = (year);
                            }
                        } else {
                            Return_Day = (day + 7);
                            Return_Month = (month);
                            Return_Year = (year);
                        }
                    } else {
                        //FOR DVD ITEMS
                        if ((day + 3) > 30) {
                            Return_Day = ((day + 3) - 30);
                            if (((month + 1)) > 12) {
                                Return_Month = (((month + 1) - 12));
                                Return_Year = ((year + 1));
                            } else {
                                Return_Month = ((month + 1));
                                Return_Year = (year);
                            }
                        } else {
                            Return_Day = (day + 3);
                            Return_Month = (month);
                            Return_Year = (year);
                        }
                    }
                    //setting expected return date
                    DateTime returnDate = new DateTime(Return_Year, Return_Month, Return_Day, Return_hour);
                    libraryItemsList.get(x).setReturnedDateTime(returnDate);
                    System.out.println(libraryItemsList.get(x).getReturnedDateTime().getHour() + " " + libraryItemsList.get(x).getReturnedDateTime().getDay() + " " + libraryItemsList.get(x).getReturnedDateTime().getMonth() + " " +
                            libraryItemsList.get(x).getReturnedDateTime().getYear());
                    break;
                } else {
                    availability = "Not Available";
                    System.out.println("Library Item " + jsonIsbn + " Item is Already borrowed");
                    break;
                }

                //If isbn is invalid
            } else {
                availability = "Invalid ISBN";
                System.out.println("No match found");
            }
        }
        return availability;
    }


    @Override
    //Return Library item
    public String returnItem(int jsonIsbn, String returnedReaderID, DateTime returnedDate) {
        String itemStatus = null;

        for (int x = 0; x < libraryItemsList.size(); x++) {
            //Checking ISBN
            if (libraryItemsList.get(x).getIsbn() == jsonIsbn && (libraryItemsList.get(x).getAvailable() == false)) {
                System.out.println("Item detected");

                System.out.println(returnedReaderID);
                System.out.println(libraryItemsList.get(x).getReader().getReaderID());

                //Checking readerID
                if (libraryItemsList.get(x).getReader().getReaderID().equals(returnedReaderID)) {
                    itemStatus = "Item Matches";
                    System.out.println("Borrowed Item Detected");

                    //Checking date validity borrowed and returned dates
                    boolean isReturnDateValid = returnDateValidation(x, returnedDate);
                    if (isReturnDateValid == true) {
                        libraryItemsList.get(x).setAvailable(true);
                        break;
                    }
                    else {
                        itemStatus = "Invalid Date";
                        break;
                    }
                } else{
                    itemStatus = "Invalid ReaderID";
                    System.out.println("ReaderID is Invalid");
                    break;
                }
            } else {
                itemStatus = "Invalid ISBN";
                System.out.println("ISBN is Invalid");
            }
        }
        System.out.println("STATUS: " + itemStatus);
        return itemStatus;
    }


    //Validating return date
    static boolean returnDateValidation(int x, DateTime returnedDate) {
        boolean isValid = false;

        int bYear = libraryItemsList.get(x).getBorrowedDateTime().getYear();
        int bMonth = libraryItemsList.get(x).getBorrowedDateTime().getMonth();
        int bDay = libraryItemsList.get(x).getBorrowedDateTime().getDay();
        int bHour = libraryItemsList.get(x).getBorrowedDateTime().getHour();

        int RetYear = returnedDate.getYear();
        int RetMonth = returnedDate.getMonth();
        int RetDay = returnedDate.getDay();
        int RetHour = returnedDate.getHour();

        if (bYear == RetYear ) {
            if (bMonth == RetMonth) {
                if (bDay == RetDay) {
                    if (bHour <= RetHour) {
                        isValid = true;
                    }
                }else if (bDay < RetDay){
                    isValid = true;
                }
            }else if (bMonth < RetMonth) {
                        isValid = true;
                    }
              }else if (bYear < RetYear){
            isValid = true;
        }
        return isValid;
    }



//Calculating Fee for the item returned after the due date
   public static LibraryItemFee calculateLibraryItemFee(int jsonIsbn, DateTime returnedDate) {
        boolean feeStatus = false;
        double amount = 0;
        int x =0;
        for (int i =0; i < libraryItemsList.size() ; i++){
            if (libraryItemsList.get(i).getIsbn() == jsonIsbn){
                x = i;
            }
        }

        int actRetYear = returnedDate.getYear();
        int actRetMonth = returnedDate.getMonth();
        int actRetDay = returnedDate.getDay();
        int actRetHour = returnedDate.getHour();

        int exRetYear = libraryItemsList.get(x).getReturnedDateTime().getYear();
        int exRetMonth = libraryItemsList.get(x).getReturnedDateTime().getMonth();
        int exRetDay = libraryItemsList.get(x).getReturnedDateTime().getDay();
        int exptRetHour = libraryItemsList.get(x).getReturnedDateTime().getHour();


       if (exRetYear == actRetYear ) {
           if (exRetMonth == actRetMonth) {
               if (exRetDay == exRetDay) {
                   if (exptRetHour >= actRetHour){
                       feeStatus = false;
                   }else {
                       feeStatus = true;
                       int usedHour = actRetHour - exptRetHour;
                       amount = usedHour * 0.2;
                   }
               }else if (exRetDay > exRetDay){
                   feeStatus = false;
               }else{
                   feeStatus = true;
                   int usedDate = (actRetDay - exRetDay);
                   int usedHour = (usedDate * 24);
                   if (usedHour <= 72){
                       amount = usedHour * 0.2;
                   }else{
                       amount =((72*0.2)+ ((usedHour - 72) * 0.5));
                   }
               }
           }else if (exRetMonth > actRetMonth) {
               feeStatus = false;
           }else{
               feeStatus = true;
               int usedDate = (((actRetMonth - exRetMonth) * 30) + (actRetDay - exRetDay));
               int usedHour = usedDate * 24;
               if (usedHour <= 72) {
                   amount = usedHour * 0.2;
               } else {
                   amount = ((72 * 0.2) + (usedHour - 72) * 0.5);
               }
           }
       }else if (exRetYear > actRetYear){
           feeStatus = false;
       }else{
           feeStatus = true;
           int usedDate = (((actRetYear - exRetYear) * 365) + (((actRetMonth - exRetMonth) * 30) + (actRetDay - exRetDay)));
           int usedHour = usedDate * 24;
           if (usedHour <= 72) {
               amount = usedHour * 0.2;
           } else {
               amount = ((72 * 0.2) + (usedHour - 72) * 0.5);
           }
       }

        LibraryItemFee Fee = new LibraryItemFee(feeStatus, amount);
        System.out.println("Amount:- " + Fee.getAmount());
        return Fee;
    }


// public void generateReport(){
//        ArrayList report = new ArrayList();
//        for (int x=0 ; x <= libraryItemsList.size(); x++){
//            if (libraryItemsList.get(x).isAvailable() == false){
//                report.add(libraryItemsList.get(x));
//            }
//        }

// }



}


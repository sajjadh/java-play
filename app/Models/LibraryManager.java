package Models;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Calendar;
import java.util.List;

import static Models.WestminsterLibraryManager.borrowItemQueue;
import static Models.WestminsterLibraryManager.libraryItemsList;

public interface LibraryManager {

    public static List<LibraryItem> getDisplayItems() {
        return (libraryItemsList);
    }



    //Add Item to arrayList
    String addItem(LibraryItem item);


    //Borrow Library Item
    String borrowItem_update(Reader reader, int jsonIsbn, DateTime borrowedDate);


    //Return Library Item
    String returnItem(int jsonIsbn, String returnedReaderID, DateTime returnedDate);

    void somthing();

}









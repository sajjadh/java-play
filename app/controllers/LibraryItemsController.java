package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import Models.*;
import util.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Models.WestminsterLibraryManager.libraryItemsList;


public class LibraryItemsController extends Controller {
    WestminsterLibraryManager object = new WestminsterLibraryManager();

    //Create Books
    public Result addBook() {

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(util.createResponse(
                    "Expecting Json data", false));
        }

        if (json.get("isbn").equals("0")) {
            return badRequest(util.createResponse("isbn is null/invalid", false));
        }


        //Validating json fill values -> CHECK AGAIN
        if ((json.get("isbn") != null) && (json.get("title") != null) && (json.get("sector") != null) &&
                (json.get("publicationDate") != null) && (json.get("author") != null) && (json.get("publisher") != null) &&
                (json.get("no_Of_Pages") != null) && (json.get("reader") != null) && (json.get("borrowedDateTime") != null) &&
                (json.get("returnedDateTime") != null)) {

            ObjectMapper object_Mapper = new ObjectMapper();
            Book book_Obj = new Book(12345, "", "", null, "",
                    "", 10, null, true, null, null, null);

            try {
                book_Obj = object_Mapper.readValue(json.toString(), Book.class);
            } catch (IOException e) {
                System.out.println(e);
            }


            //Adding book to the arrayList
            String message = null;
            message = object.addItem(book_Obj);


            //Responding to the server after duplicate validation
            if (message == "Book Added") {
                ObjectNode response = Json.newObject();
                response.put("status", "success");
                return created(util.createResponse(response, true));
            } else if (message == "Book not added") {
                ObjectNode response = Json.newObject();
                response.put("status", "No Space");
                return created(util.createResponse(response, false));
            } else {
                ObjectNode response = Json.newObject();
                response.put("status", "duplicateEntry");
                response.put("ISBN", book_Obj.getIsbn());
                return created(util.createResponse(response, false));
            }

        } else {
            return created(util.createResponse("Data Not Valid", false));
        }
    }


    //Create DVDs
    public Result addDVD() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(util.createResponse(
                    "Expecting Json data", false));
        }

        ObjectMapper object_Mapper = new ObjectMapper();
        DVD dvd_Obj = new DVD(12345, "", "", "", "",
                "", "", null, true, null, null, null, null);

        try {
            dvd_Obj = object_Mapper.readValue(json.toString(), DVD.class);
        } catch (IOException e) {
            System.out.println(e);
        }

        //Adding book to the arrayList
        String message = null;
        message = object.addItem(dvd_Obj);

        //Responding to the server
        if (message == "DVD Added") {
            ObjectNode response = Json.newObject();
            response.put("status", "success");
            return created(util.createResponse(response, true));
        } else if (message == "DVD not added") {
            ObjectNode response = Json.newObject();
            response.put("status", "No Space");
            response.put("ISBN", dvd_Obj.getIsbn());
            return created(util.createResponse(response, false));
        } else {
            ObjectNode response = Json.newObject();
            response.put("status", "duplicateEntry");
            response.put("ISBN", dvd_Obj.getIsbn());
            return created(util.createResponse(response, false));
        }
    }




    //Borrow Library Item
    public Result borrowItem() {

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(util.createResponse(
                    "Expecting Json data", false));
        }

        System.out.println("Received Json: " + json);
        if (json.get("isbn").equals("0")) {
            return badRequest(util.createResponse("isbn is null/invalid", false));
        }

        int jsonIsbn = json.get("isbn").asInt();

        //Getting reader details
        String id = json.get("reader").get("readerID").asText();
        String name = json.get("reader").get("readerName").asText();
        String email = json.get("reader").get("readerEmail").asText();
        int mobile = json.get("reader").get("readerMobile").asInt();

        Reader reader = new Reader(id, name, mobile, email);

        //Getting Borrowed date
        int hour = json.get("borrowedDateTime").get("hour").asInt();
        int day = json.get("borrowedDateTime").get("day").asInt();
        int month = json.get("borrowedDateTime").get("month").asInt();
        int year = json.get("borrowedDateTime").get("year").asInt();

        DateTime borrowedDate = new DateTime(year, month, day, hour);

        String isAvaialable = object.borrowItem_update(reader, jsonIsbn, borrowedDate);

        //If the item is already borrowed check when the item will be available
        if (isAvaialable == "Available") {
            ObjectNode response = Json.newObject();
            response.put("status", "Success");
            return created(util.createResponse(response, true));
        } else if (isAvaialable == "Not Available")
        {
            int expectedReturnDay = 0;
            int expectedReturnMonth = 0;
            int expectedReturnYear = 0;

            for (int x = 0; x < libraryItemsList.size(); x++) {
                if (libraryItemsList.get(x).getIsbn() == jsonIsbn) {
                    expectedReturnDay = libraryItemsList.get(x).getReturnedDateTime().getDay();
                    expectedReturnMonth = libraryItemsList.get(x).getReturnedDateTime().getMonth();
                    expectedReturnYear = libraryItemsList.get(x).getReturnedDateTime().getYear();
                }
            }

            //Sending back response
            ObjectNode response = Json.newObject();
            response.put("status", "Already Borrowed");
            response.put("DAY", expectedReturnDay);
            response.put("MONTH", expectedReturnMonth);
            response.put("YEAR", expectedReturnYear);
            return created(util.createResponse(response, false));
        } else {
            ObjectNode response = Json.newObject();
            response.put("status", "Invalid ISBN");
            response.put("ISBN", jsonIsbn);
            return created(util.createResponse(response, false));
        }
    }


    //Return Library Item
    public Result returnItem() {

        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(util.createResponse(
                    "Expecting Json data", false));
        }
        System.out.println("Received Json: " + json);

        String returnedReaderID = json.get("reader").get("readerID").asText();
        System.out.println(returnedReaderID);
        int jsonIsbn = json.get("isbn").asInt();

        //Getting Actual Returned date
        int hour = json.get("returnDateTime").get("hour").asInt();
        int day = json.get("returnDateTime").get("day").asInt();
        int month = json.get("returnDateTime").get("month").asInt();
        int year = json.get("returnDateTime").get("year").asInt();

        DateTime returnedDate = new DateTime(year, month, day, hour);

        String itemStatus = object.returnItem(jsonIsbn, returnedReaderID, returnedDate);

        LibraryItemFee Fee = null;

        if (itemStatus == "Item Matches") {
            //calculate late return library item fee
            Fee = object.calculateLibraryItemFee(jsonIsbn, returnedDate);
        }
        System.out.println(itemStatus);
        if (itemStatus == "Item Matches" && Fee.isFeeStatus() == true) {
            ObjectNode response = Json.newObject();
            response.put("status", "success_WithFee");
            response.put("amount", Fee.getAmount());
            return created(util.createResponse(response, true));
        } else if (itemStatus == "Item Matches" && Fee.isFeeStatus() == false) {
            ObjectNode response = Json.newObject();
            response.put("status", "success_NoFee");
            response.put("amount", "No Fee");
            return created(util.createResponse(response, true));
        } else if (itemStatus == "Invalid ISBN") {
            ObjectNode response = Json.newObject();
            response.put("status", "Invalid ISBN");
            return created(util.createResponse(response, false));
        } else if (itemStatus == "Invalid ReaderID") {
            ObjectNode response = Json.newObject();
            response.put("status", "Invalid ReaderID");
            return created(util.createResponse(response, false));
        } else {
            ObjectNode response = Json.newObject();
            response.put("status", "Invalid Return Date");
            return created(util.createResponse(response, false));
        }
    }


    //Display array items
    public Result displayItem() {

        ArrayList a = new ArrayList();

        ObjectNode response = Json.newObject();
        if (libraryItemsList.size() > 0) {
            response.put("status", "success");
            ObjectMapper mapper = new ObjectMapper();
            for (int i = 0; i < libraryItemsList.size(); i++) {
                try {
                    mapper.writeValue(new File("d:\\file.json"), libraryItemsList.get(i));
                    String jsonInString = mapper.writeValueAsString(libraryItemsList.get(i));
                    a.add(jsonInString);

                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            response.put("Library_Items", String.valueOf(a));
            return created(util.createResponse(response, true));

        } else {
            response.put("status", "no items");
            return created(util.createResponse(response, false));
        }
    }


    public Result searchItem() {
        System.out.println(request());
        String title = request().body().asText();
        if (title == null) {
            return badRequest(util.createResponse(
                    "Expecting Json data", false));
        }

        ArrayList a = new ArrayList();

        ObjectNode response = Json.newObject();
        if (libraryItemsList.size() > 0) {
            System.out.println(title);
            ObjectMapper mapper = new ObjectMapper();
            for (int i = 0; i < libraryItemsList.size(); i++) {
                if (libraryItemsList.get(i).getTitle().toLowerCase().equals((title).toLowerCase())) {
                    System.out.println(libraryItemsList.get(i).getTitle());
                    try {
                        mapper.writeValue(new File("d:\\file.json"), libraryItemsList.get(i));
                        String jsonInString = mapper.writeValueAsString(libraryItemsList.get(i));
                        a.add(jsonInString);

                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
            if (a.size()==0){
                response.put("status", "No Match");
                return created(util.createResponse(response, false));
            }else {
                response.put("status", "Success");
                response.put("searchItems", String.valueOf(a));
                return created(util.createResponse(response, true));
            }
            }else {
                    response.put("status", "No Item");
                    return created(util.createResponse(response, false));
                }
            }


            //delete LibraryItem
            public Result deleteLibraryItem () {
                boolean status = false;

                String isbn = String.valueOf(request().body().asJson());
                if (isbn == null) {
                    return badRequest(util.createResponse(
                            "Expecting data", false));
                }


                for (int x = 0; x < libraryItemsList.size(); x++) {
                    String value = String.valueOf(libraryItemsList.get(x).getIsbn());
                    System.out.println(value);
                    System.out.println(isbn);
                    if (value.equals(isbn)) {
                        status = true;
                        System.out.println(value);
                        System.out.println(isbn);
                        libraryItemsList.remove(x);
                        System.out.println("Item successfully removed");
                        break;
                    } else {
                        status = false;
                        System.out.println("Item Not removed");
                    }
                }
                ArrayList a = new ArrayList();
                ObjectNode response = Json.newObject();

                if (status == true) {
                    ObjectMapper mapper = new ObjectMapper();
                    for (int i = 0; i < libraryItemsList.size(); i++) {
                        try {
                            mapper.writeValue(new File("d:\\file.json"), libraryItemsList.get(i));
                            String jsonInString = mapper.writeValueAsString(libraryItemsList.get(i));
                            a.add(jsonInString);

                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                    response.put("status", "success");
                    response.put("Library_Items", String.valueOf(a));
                    return created(util.createResponse(response, true));

                } else {
                    response.put("status", "Failed");
                    return created(util.createResponse(response, false));
                }
            }





        }


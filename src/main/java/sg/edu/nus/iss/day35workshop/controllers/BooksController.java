package sg.edu.nus.iss.day35workshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.day35workshop.services.BooksService;

@Controller
@RequestMapping(path="/api/book", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class BooksController {
    
    @Autowired
    private BooksService booksService;

    // GET /api/book/search?query=title
    @GetMapping(path="/search")
    @ResponseBody
    public ResponseEntity<String> search(@RequestParam(required=true) String query) {

        List<JsonObject> results = booksService.findBooksByTitle(query)
                                                .stream()
                                                .map(book -> Json.createObjectBuilder()
                                                                 .add("bookID", book.bookID())
                                                                    .add("title", book.title())
                                                                    .build())
                                                 .toList();

        JsonArray resp = Json.createArrayBuilder(results).build();

        return ResponseEntity.ok(resp.toString());
    }

}

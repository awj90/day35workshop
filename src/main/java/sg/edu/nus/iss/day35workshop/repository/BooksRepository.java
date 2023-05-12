package sg.edu.nus.iss.day35workshop.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.day35workshop.models.BookSummary;

@Repository
public class BooksRepository {
    
    @Autowired
	MongoTemplate mongoTemplate;

    // alternative method using text index
    public List<String> search(String keyword) {

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingPhrase(keyword);
        TextQuery query = TextQuery.queryText(textCriteria);
        List<Document> results = mongoTemplate.find(query, Document.class, "books");
        return results.stream().map(document -> document.getString("title")).collect(Collectors.toList());
    }

    /* 
    db.books.find(
        { title: { $regex: "harry", $options: "i"} }, 
        { title: 1, bookID: 1, _id: 0}
        ).sort({title: 1}).limit(20);
    */

    public List<BookSummary> findBooksByTitle(String title) {
        Criteria criteria = Criteria.where("title").regex(title, "i");
        Query query = Query.query(criteria)
                            .with(Sort.by(Direction.ASC, "title"))
                            .limit(20);
        query.fields().include("title", "bookID").exclude("_id");

        return mongoTemplate.find(query, Document.class, "books")
                             .stream()
                             .map(doc -> new BookSummary(doc.getInteger("bookID"), doc.getString("title")))
                             .toList();
    }

}

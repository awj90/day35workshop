package sg.edu.nus.iss.day35workshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day35workshop.models.BookSummary;
import sg.edu.nus.iss.day35workshop.repository.BooksRepository;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

        // alternative method using text index
    public List<String> search(String keyword) {
        return booksRepository.search(keyword);
    }

    public List<BookSummary> findBooksByTitle(String title) {
        return booksRepository.findBooksByTitle(title);
    }
}

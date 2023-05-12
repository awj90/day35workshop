## GET /api/book/search?query=Harry

- Queries MongoDB database for first 20 book records (bookID and title) with titles containing "harry" (case insensitive)
- Search results are displayed in Angular FrontEnd client (see github.com/awj90/day35workshop-client)

## MongoDB query:

    db.books.find(
        { title: { $regex: "harry", $options: "i"} },
        { title: 1, bookID: 1, _id: 0}
        ).sort({title: 1}).limit(20);

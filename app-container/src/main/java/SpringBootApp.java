import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.bind.annotation.RequestMapping;

//@SpringBootApplication(exclude = {
//        ElasticsearchAutoConfiguration.class,
//        ElasticsearchDataAutoConfiguration.class})

//public class Application implements CommandLineRunner {

@SpringBootApplication
//@EnableElasticsearchRepositories
public class SpringBootApp implements CommandLineRunner {

//    @Autowired
//    RestHighLevelClient highLevelClient;
//
//    @Autowired
//    private ElasticsearchOperations elasticsearchOperations;

//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;

//    @Autowired
//    private MyService myService;

    public static void main(String args[]) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    //    @Override
    public void run(String... args) throws Exception {

//        TestController tc = new TestController(elasticsearchOperations);

//        bookService.save(new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
//        bookService.save(new Book("1002", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
//        bookService.save(new Book("1003", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));

        //fuzzey search
//        Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 10));

        //List<Book> books = bookService.findByTitle("Elasticsearch Basics");

//        books.forEach(x -> System.out.println(x));
        System.out.println("WOAH");

        welcome();
    }

    @RequestMapping("/")
    public String welcome() {
        return "happy coding";
    }

    @RequestMapping("/wow")
    public String wow() {
        return "wow";
    }
}

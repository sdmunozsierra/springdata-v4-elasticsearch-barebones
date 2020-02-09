import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/")
public class TestController {

    private ElasticsearchOperations elasticsearchOperations;

    public TestController(ElasticsearchOperations elasticsearchOperations){
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @RequestMapping("/hi")
    public String hi(){
        return "hi";
    }
//    @GetMapping("/person/{id}")
//    public Person findById(@PathVariable("id")  Long id) {
//        Person person = elasticsearchOperations
//                .queryForObject(GetQuery.getById(id.toString()), Person.class);
//        return person;
//    }
}

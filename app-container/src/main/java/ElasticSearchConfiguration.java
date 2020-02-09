import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

import java.util.stream.Stream;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class ElasticSearchConfiguration {

    @Primary
    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate(ElasticsearchProperties configuration) {
        HttpHost[] nodes =  Stream.of(configuration.getClusterNodes().split(","))
                .map(HttpHost::create)
                .toArray(HttpHost[]::new);
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(nodes));
        CustomElasticSearchConverter converter = new CustomElasticSearchConverter(new SimpleElasticsearchMappingContext(), createConversionService());
//        return new ElasticsearchRestTemplate(client, converter, new DefaultResultMapper(converter));
        return new ElasticsearchRestTemplate(client, converter);

    }

    private DefaultConversionService createConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
//        conversionService.addConverter(new Jsr310Converters.StringToLocalDateConverter());
        return conversionService;
    }
}
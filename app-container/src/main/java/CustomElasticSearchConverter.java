import org.elasticsearch.common.Nullable;
import org.springframework.core.CollectionFactory;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.ClassUtils;

import java.util.*;

/**
 * Custom version of {@link MappingElasticsearchConverter} to support newest Spring Data Elasticsearch integration that supports ElasticSearch 7. Remove when Spring Data Elasticsearch 4.x is released.
 */
class CustomElasticSearchConverter extends MappingElasticsearchConverter {

    private CustomConversions conversions = new ElasticsearchCustomConversions(Collections.emptyList());

    CustomElasticSearchConverter(MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext) {
        super(mappingContext);
        setConversions(conversions);
    }

    CustomElasticSearchConverter(MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext, GenericConversionService conversionService) {
        super(mappingContext, conversionService);
        setConversions(conversions);
    }

    @Override
    protected <R> R readValue(@Nullable Object source, ElasticsearchPersistentProperty property,
                              TypeInformation<R> targetType) {

        if (source == null) {
            return null;
        }

        if (source instanceof List) {
            return readCollectionValue((List) source, property, targetType);
        }

        return super.readValue(source, property, targetType);
    }

    private Object readSimpleValue(@Nullable Object value, TypeInformation<?> targetType) {

        Class<?> target = targetType.getType();

        if (value == null || target == null || ClassUtils.isAssignableValue(target, value)) {
            return value;
        }

        if (conversions.hasCustomReadTarget(value.getClass(), target)) {
            return getConversionService().convert(value, target);
        }

        if (Enum.class.isAssignableFrom(target)) {
            return Enum.valueOf((Class<Enum>) target, value.toString());
        }

        return getConversionService().convert(value, target);
    }


    private <R> R readCollectionValue(@Nullable List<?> source, ElasticsearchPersistentProperty property,
                                      TypeInformation<R> targetType) {

        if (source == null) {
            return null;
        }

        Collection<Object> target = createCollectionForValue(targetType, source.size());

        for (Object value : source) {

            if (isSimpleType(value)) {
                target.add(
                        readSimpleValue(value, targetType.getComponentType() != null ? targetType.getComponentType() : targetType));
            } else {

                if (value instanceof List) {
                    target.add(readValue(value, property, property.getTypeInformation().getActualType()));
                } else {
                    target.add(readEntity(computeGenericValueTypeForRead(property, value), (Map) value));
                }
            }
        }

        return (R) target;
    }

    private Collection<Object> createCollectionForValue(TypeInformation<?> collectionTypeInformation, int size) {

        Class<?> collectionType = collectionTypeInformation.isCollectionLike()//
                ? collectionTypeInformation.getType() //
                : List.class;

        TypeInformation<?> componentType = collectionTypeInformation.getComponentType() != null //
                ? collectionTypeInformation.getComponentType() //
                : ClassTypeInformation.OBJECT;

        return collectionTypeInformation.getType().isArray() //
                ? new ArrayList<>(size) //
                : CollectionFactory.createCollection(collectionType, componentType.getType(), size);
    }

    private ElasticsearchPersistentEntity<?> computeGenericValueTypeForRead(ElasticsearchPersistentProperty property,
                                                                            Object value) {

        return ClassTypeInformation.OBJECT.equals(property.getTypeInformation().getActualType())
                ? getMappingContext().getRequiredPersistentEntity(value.getClass())
                : getMappingContext().getRequiredPersistentEntity(property.getTypeInformation().getActualType());
    }

    private boolean isSimpleType(Object value) {
        return isSimpleType(value.getClass());
    }

    private boolean isSimpleType(Class<?> type) {
        return conversions.isSimpleType(type);
    }

}
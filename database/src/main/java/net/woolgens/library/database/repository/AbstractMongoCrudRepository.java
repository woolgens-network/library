package net.woolgens.library.database.repository;

import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import net.woolgens.library.common.reflection.Reflections;
import net.woolgens.library.common.repository.CrudRepository;
import org.bson.Document;

import java.util.List;

@Getter
public abstract class AbstractMongoCrudRepository<T> implements CrudRepository<T> {

    private volatile Class<T> entityClass;
    private String collectionName;
    private String primaryField;
    private MongoCollection<T> collection;

    protected AbstractMongoCrudRepository(MongoDatabase database, String collectionName, String primaryField) {
        this.collectionName = collectionName;
        this.primaryField = primaryField;
        this.collection = database.getCollection(collectionName, getEntityClass(getClass()));
    }

    @Override
    public void create(String primaryKey, T entity) {
        this.collection.insertOne(entity);
    }

    @Override
    public T find(String primaryKey) {
        return this.collection.find(Filters.eq(primaryField, primaryKey)).first();
    }

    @Override
    public List<T> findAll() {
        List<T> list = Lists.newLinkedList();
        FindIterable<T> findIterable = collection.find();
        for(T value : findIterable){
            list.add(value);
        }
        return list;
    }

    @Override
    public void update(String primaryKey, T entity) {
        this.collection.updateOne(Filters.eq(primaryField, primaryKey), new Document("$set", entity));
    }

    @Override
    public void delete(String primaryKey) {
        this.collection.deleteOne(Filters.eq(primaryField, primaryKey));
    }

    @Override
    public boolean exists(String primaryKey) {
        return this.collection.find(Filters.eq(primaryField, primaryKey)).first() != null;
    }

    public void deleteAll() {
        this.collection.drop();
    }

    protected Class<T> getEntityClass(Class<?> clazz) {
        Class<T> tmp = entityClass;
        if (tmp == null) {
            synchronized (this) {
                tmp = entityClass;
                if (tmp == null) {
                    entityClass = tmp =  (Class<T>) Reflections.getGenericType(clazz, 0);
                }
            }
        }
        return tmp;
    }


}
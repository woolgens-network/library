package net.woolgens.library.file.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.woolgens.library.common.repository.CrudRepository;
import net.woolgens.library.file.gson.GsonFileEntity;
import net.woolgens.library.file.gson.GsonInterceptor;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractGsonCrudRepository<T> implements CrudRepository<T> {

    private Map<String, GsonFileEntity<T>> cache;
    private File directory;
    private TypeToken<T> typeToken;

    public AbstractGsonCrudRepository(String path) {
        this.directory = new File(path);
        this.directory.mkdirs();
        this.cache = Maps.newHashMap();
        this.typeToken = new TypeToken<T>(getClass()) {};
    }

    public void filter(GsonBuilder builder) {

    }


    public AbstractGsonCrudRepository(String path, TypeToken<T> typeToken) {
        this(path);
        this.typeToken = typeToken;
    }
    
    public GsonFileEntity<T> getOrLoad(String key) {
        if(cache.containsKey(key)) {
            return cache.get(key);
        }
        GsonFileEntity<T> file = new GsonFileEntity<T>(getCorrectDirectoryPath(), key, typeToken, new GsonInterceptor() {
            @Override
            public void intercept(GsonBuilder builder) {
                filter(builder);
            }
        });
        cache.put(key, file);
        return file;
    }
    
    public String getCorrectDirectoryPath() {
        return this.directory.getPath().concat(File.separator);
    }

    @Override
    public List<T> findAll() {
        List<T> list = Lists.newArrayList();
        for(File file : directory.listFiles()) {
            GsonFileEntity<T> fileEntity = new GsonFileEntity<>(getCorrectDirectoryPath(), file.getName().replace(".json", ""), typeToken, new GsonInterceptor() {
                @Override
                public void intercept(GsonBuilder builder) {
                    filter(builder);
                }
            });
            list.add(fileEntity.getEntity());
        }
        return list;
    }

    public void loadAll() {
        for(File file : directory.listFiles()) {
            String id = file.getName().replace(".json", "");
            GsonFileEntity<T> fileEntity = new GsonFileEntity<>(getCorrectDirectoryPath(), id, typeToken, new GsonInterceptor() {
                @Override
                public void intercept(GsonBuilder builder) {
                    filter(builder);
                }
            });
            this.cache.put(id, fileEntity);
        }
    }

    @Override
    public T find(String primaryKey) {
        try {
            GsonFileEntity<T> file = getOrLoad(primaryKey);
            return file.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(String primaryKey, T entity) {
        try {
            GsonFileEntity<T> file = getOrLoad(primaryKey);
            file.setEntity(entity);
            file.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exists(String primaryKey) {
        if(cache.containsKey(primaryKey)) {
            return true;
        }
        try {
            GsonFileEntity<T> file = getOrLoad(primaryKey);
            return file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(String primaryKey, T entity) {
        try {
            GsonFileEntity<T> file = getOrLoad(primaryKey);
            file.setEntity(entity);
            file.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String primaryKey) {
        try {
            GsonFileEntity<T> file = getOrLoad(primaryKey);
            file.delete();
            cache.remove(primaryKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<GsonFileEntity<T>> findAllCached() {
        return this.cache.values();
    }
}

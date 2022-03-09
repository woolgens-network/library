package net.woolgens.library.file.gson;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;

@Getter
public class GsonFileEntity<T> {


    @Setter
    private T entity;

    @Setter
    private Gson gson;

    private File file;
    private TypeToken<T> typeToken;
    private GsonInterceptor interceptor;

    public GsonFileEntity(String path, String file) {
        this.gson = createGson();

        File directory = new File(path);
        directory.mkdirs();
        this.file = new File(path.concat(file).concat(".json"));
        this.typeToken = new TypeToken<T>(getClass()) {};
        load();
    }

    public GsonFileEntity(String path, String file, GsonInterceptor interceptor) {
        this.interceptor = interceptor;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        interceptor.intercept(builder);
        this.gson = builder.create();

        File directory = new File(path);
        directory.mkdirs();
        this.file = new File(path.concat(file).concat(".json"));
        this.typeToken = new TypeToken<T>(getClass()) {};
        load();
    }


    public GsonFileEntity(String path, String file, TypeToken<T> token) {
        this.gson = createGson();

        File directory = new File(path);
        directory.mkdirs();
        this.file = new File(path.concat(file).concat(".json"));
        this.typeToken = token;
        load();
    }

    public GsonFileEntity(String path, String file, TypeToken<T> token, GsonInterceptor interceptor) {
        this.interceptor = interceptor;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        interceptor.intercept(builder);
        this.gson = builder.create();

        File directory = new File(path);
        directory.mkdirs();
        this.file = new File(path.concat(file).concat(".json"));
        this.typeToken = token;
        load();
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder()
                .setPrettyPrinting();
        intercept(builder);
        return builder.create();
    }

    @Deprecated
    public void intercept(GsonBuilder builder) {}

    public boolean exists() {
        return this.file.exists();
    }

    public void delete() {
        if (this.file.exists())
            this.file.delete();
    }

    public void load() {
        try {
            if (exists()) {
                @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), "UTF-8"));
                this.entity = (T) gson.fromJson(reader, this.typeToken.getType());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            if (!this.file.exists())
                this.file.createNewFile();
            String json = gson.toJson(this.entity);
            @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(this.file);
            @Cleanup OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "UTF-8");
            writer.write(json);
            writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

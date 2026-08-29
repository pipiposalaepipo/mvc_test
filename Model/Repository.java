package Model;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Repository {

    public SeedData loadInitialData() {
        Gson gson = new Gson();
        try (Reader reader = new InputStreamReader(
                new FileInputStream("Model/seed_data.json"), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, SeedData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
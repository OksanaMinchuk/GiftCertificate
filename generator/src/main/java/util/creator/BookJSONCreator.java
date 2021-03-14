package util.creator;

import com.github.javafaker.Faker;
import org.json.simple.JSONObject;

public class BookJSONCreator {

    public static JSONObject createBook(){
        Faker faker = new Faker();

        JSONObject bookObject = new JSONObject();
        bookObject.put("title", faker.book().title());
        bookObject.put("author", faker.book().author());
        return bookObject;
    }
}

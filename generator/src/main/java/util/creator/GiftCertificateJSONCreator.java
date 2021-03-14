package util.creator;

import com.github.javafaker.Faker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Random;

public class GiftCertificateJSONCreator {

    private static final int MAX_PRICE_VALUE = 100;
    private static final int MIN_PRICE_VALUE = 1;
    private static final int PRICE_MAX_NUMBER_OF_DECIMALS = 0;

    private static final int MAX_DURATION_VALUE = 100;
    private static final int MIN_DURATION_VALUE = 1;
    private static final int WRONG_MIN_DURATION_VALUE = -10;

    private static final int MAX_COUNT_TAGS = 5;
    private static final int MIN_COUNT_TAGS = 1;

    private static int tagsQuantity;

    public static JSONObject createValidGiftCertificate(){
        Faker faker = new Faker();

        JSONObject giftCertificateObject = new JSONObject();
        giftCertificateObject.put("name", faker.random().hex());
        giftCertificateObject.put("description", faker.backToTheFuture().quote());
        giftCertificateObject.put("price", faker.number().randomDouble(PRICE_MAX_NUMBER_OF_DECIMALS, MIN_PRICE_VALUE, MAX_PRICE_VALUE));
        giftCertificateObject.put("duration", faker.number().numberBetween(MIN_DURATION_VALUE, MAX_DURATION_VALUE));

        JSONArray tagObjectArray = new JSONArray();
        Random random = new Random();
        tagsQuantity = random.nextInt((MAX_COUNT_TAGS - MIN_COUNT_TAGS) + 1) + MIN_COUNT_TAGS;

        for (int i = 0; i < tagsQuantity; i++) {
            JSONObject tagObject = new JSONObject();
            tagObject.put("name", faker.name().username());
            tagObjectArray.add(tagObject);
        }
        giftCertificateObject.put("tags", tagObjectArray);
        return giftCertificateObject;
    }

    public static JSONObject createNonValidGiftCertificate(){
        Faker faker = new Faker();

        JSONObject giftCertificateObject = new JSONObject();
        giftCertificateObject.put("name", faker.friends().quote() + Instant.now().get(ChronoField.MILLI_OF_SECOND));
        giftCertificateObject.put("description", faker.backToTheFuture().quote());
        giftCertificateObject.put("price", faker.number().randomDouble(PRICE_MAX_NUMBER_OF_DECIMALS, MIN_PRICE_VALUE, MAX_PRICE_VALUE));
        giftCertificateObject.put("duration", faker.number().numberBetween(WRONG_MIN_DURATION_VALUE, 0));

        JSONArray tagObjectArray = new JSONArray();
        Random random = new Random();
        tagsQuantity = random.nextInt((MAX_COUNT_TAGS - MIN_COUNT_TAGS) + 1) + MIN_COUNT_TAGS;

        for (int i = 0; i < tagsQuantity; i++) {
            JSONObject tagObject = new JSONObject();
            tagObject.put("name", faker.name().username());
            tagObjectArray.add(tagObject);

        }
        giftCertificateObject.put("tags", tagObjectArray);

        return giftCertificateObject;
    }
}

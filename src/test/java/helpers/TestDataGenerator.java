package helpers;

import java.util.Random;

public class TestDataGenerator {

    private static final String VIN_CHARS =
            "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";

    private static final Random RANDOM =
            new Random();

    public static String generateUnitNumber(){

        return "A" +
                RANDOM.nextInt(100000000);
    }

    public static String generateVin(){

        StringBuilder vin =
                new StringBuilder();

        vin.append("A");
        vin.append("1");

        while (vin.length() < 17){

            int index = RANDOM.nextInt(
                    VIN_CHARS.length()
            );

            vin.append(
                    VIN_CHARS.charAt(index)
            );
        }

        return vin.toString();
    }
}
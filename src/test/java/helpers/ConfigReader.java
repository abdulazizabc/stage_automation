package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadRequired("config.properties");
        loadOptional("config.local.properties");
    }

    private ConfigReader() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing config property: " + key);
        }
        return value.trim();
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getLoginUrl() {
        return getBaseUrl() + get("login.path");
    }

    public static String getDashboardUrl() {
        return getBaseUrl() + get("dashboard.path");
    }

    public static String getUsername() {
        return get("test.username");
    }

    public static String getPassword() {
        return get("test.password");
    }

    public static int getWaitSeconds() {
        return Integer.parseInt(get("wait.seconds"));
    }

    private static void loadRequired(String resourceName) {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalStateException("Required config file not found on classpath: " + resourceName);
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load config: " + resourceName, e);
        }
    }

    private static void loadOptional(String resourceName) {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load config: " + resourceName, e);
        }
    }
}

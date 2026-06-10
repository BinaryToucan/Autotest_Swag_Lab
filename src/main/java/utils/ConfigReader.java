package utils;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/// Загрузка данных из конфигов проекта
public class ConfigReader {

    private static final Properties properties = new Properties();

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    static {
        try {
            FileInputStream fis =
                    new FileInputStream("src/main/resources/config.properties");

            properties.load(fis);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    ///  Получение значения из config.properties
    public static String getConfigProperty(String key) {
        return properties.getProperty(key);
    }

    ///  Получение значения из .env или переменных окружения
    public static String getEnvProperty(String key) {
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }

        return dotenv.get(key);
    }
}
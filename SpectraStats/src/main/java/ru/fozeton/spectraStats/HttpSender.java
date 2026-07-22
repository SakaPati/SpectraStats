package ru.fozeton.spectraStats;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class HttpSender {
    private final String BASE_URL;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Logger logger;

    public HttpSender(JavaPlugin plugin, Logger logger) {
        this.BASE_URL = plugin.getConfig().getString("backendUrl");
        this.logger = logger;
    }

    private void executeAsync(String endpoint, RequestBuilder builder) {
        Thread.startVirtualThread(() -> {
            try {
                HttpRequest request = builder.build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() >= 400)
                    logger.warning("Ошибка при отправке данных на " + endpoint + ". Code: " + response.statusCode());

            } catch (IOException e) {
                logger.severe("Не удалось связаться с бэкендом! Ошибка: " + e.getMessage());
            } catch (InterruptedException e) {
                logger.severe("Запрос был прерван: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        });
    }

    public void postVoid(String endpoint) {
        executeAsync(endpoint, () -> HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build());
    }

    public void postDate(String endpoint, String json) {
        executeAsync(endpoint, () -> HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build()
        );
    }

    @FunctionalInterface
    private interface RequestBuilder {
        HttpRequest build();
    }
}

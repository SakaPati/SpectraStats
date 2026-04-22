package ru.fozeton.spectraStats;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class HttpSender {
    private final static String BASE_URL = "http://localhost:8080/api";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Logger logger;

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
//
//    public void postPeriodic(String endpoint, String json, Duration second) {
//        Bukkit.getScheduler().runTaskTimer(plugin, () -> postDate(endpoint, json), 0L, Tick.tick().fromDuration(second));
//    }

    @FunctionalInterface
    private interface RequestBuilder {
        HttpRequest build();
    }
}

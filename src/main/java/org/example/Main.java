package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;


public class Main {
    public static String SITE = "https://api.nasa.gov/planetary/apod?api_key=";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите ваш ключ:");
        SITE = SITE + scan.nextLine();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        HttpGet request = new HttpGet(SITE);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);

        String info = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);

        JsonInfo nasaInfo = mapper.readValue(info, JsonInfo.class);
        System.out.println(nasaInfo);

        downloadJPG(nasaInfo, scan);
    }

    public static void downloadJPG(JsonInfo nasaInfo, Scanner scan) throws MalformedURLException {
        String urlDownload = null;
        System.out.println("Какую картнку вы жедаете скачать? \n" +
                "1. Версия HD \n" +
                "2. Обычная версия");
        String command = scan.nextLine();
        switch (command) {
            case "1":
                urlDownload = nasaInfo.getHdurl();
                break;
            case "2":
                urlDownload = nasaInfo.getUrl();
                break;
            default:
                System.out.println("Ошибка");
                downloadJPG(nasaInfo, scan);
        }
        String[] name = urlDownload.split("/");
        String nameJPEG = name[name.length - 1];
        URL url = new URL(urlDownload);
        try (InputStream in = url.openStream()) {
            Path outputPath = Path.of(nameJPEG);
            Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            System.out.println(new RuntimeException(e));
        }
    }
}
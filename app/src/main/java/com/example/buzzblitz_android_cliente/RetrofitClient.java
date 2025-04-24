package com.example.buzzblitz_android_cliente;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;

public class RetrofitClient {

    // Aquesta classe és la que s'encarrega de fer la connexió amb el servidor com una mena de pont.
    // Utilitza Retrofit i OkHttp per configurar-ho tot. La URL és la del localhost de l'emulador (10.0.2.2)
    // i posa un interceptor per veure els logs de les peticions (per si de cas algo peta, ho podem depurar).
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:8080/dsaApp/";

    // La funció getApiService() crea la instància de Retrofit només una vegada (singleton) i retorna el servei per fer les crides a l'API.
    public static BuzzBlitzService getApiService() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(BuzzBlitzService.class);
    }
}
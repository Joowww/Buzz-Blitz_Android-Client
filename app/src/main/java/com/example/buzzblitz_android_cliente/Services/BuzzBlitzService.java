package com.example.buzzblitz_android_cliente.Services;


import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.Models.Compra;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface BuzzBlitzService {
    @POST("dsaApp/usuarios/register")
    Call<Usuario> registerUsuario(@Body Usuario usuario);

    @POST("dsaApp/usuarios/login")
    Call<Usuario> loginUsuario(@Body Usulogin usuario);

    @PUT("dsaApp/usuarios/comprar")
    Call<Compra> comprarObjeto(@Body Compra usuario);

    @GET("dsaApp/usuarios/tienda/armas")
    Call<List<Objeto>> getArmas();

    @POST("dsaApp/usuarios/tienda/skins")
    Call<List<Objeto>> getSkin();
}

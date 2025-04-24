package com.example.buzzblitz_android_cliente.Services;


import com.example.buzzblitz_android_cliente.Models.UsuReg;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.Models.Compra;
import com.example.buzzblitz_android_cliente.Models.ConsultaTienda;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;


public interface BuzzBlitzService {
    @POST("usuarios/register")
    Call<Void> registerUsuario(@Body UsuReg usuario);

    @POST("usuarios/login")
    Call<Usuario> loginUsuario(@Body Usulogin usuario);

    @PUT("usuarios/comprar")
    Call<Compra> comprarObjeto(@Body Compra usuario);

    @GET("usuarios/tienda/armas")
    Call<ConsultaTienda> getArmas();

    @GET("usuarios/tienda/skins")
    Call<ConsultaTienda> getSkin();

    @DELETE("usuarios/{id}")
    Call<Void> deleteUsuario(@Path("id") String id);

}

package com.example.buzzblitz_android_cliente.Services;

import com.example.buzzblitz_android_cliente.Models.UsuReg;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.Models.UsuarioEnviar;
import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.Models.Compra;
import com.example.buzzblitz_android_cliente.Models.ConsultaTienda;
import com.example.buzzblitz_android_cliente.Models.ForumPost;
import com.example.buzzblitz_android_cliente.Models.ChatMessage;
import com.example.buzzblitz_android_cliente.Models.DevolverCompra;
import com.example.buzzblitz_android_cliente.Models.OlvContra;
import com.example.buzzblitz_android_cliente.Models.Intercambio;
import com.example.buzzblitz_android_cliente.Models.Info;
import com.example.buzzblitz_android_cliente.Models.InfoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BuzzBlitzService {
    // Registro y autenticación
    @POST("usuarios/register")
    Call<Void> registerUsuario(@Body UsuReg usuario);

    @POST("usuarios/login")
    Call<UsuarioEnviar> loginUsuario(@Body Usulogin usuario);

    // Ranking con posición del usuario
    @GET("usuarios/informacion/{userId}")
    Call<InfoList> getInfo(@Path("userId") String userId);

    // Recuperación de contraseña
    @GET("usuarios/login/recordarContraseña")
    Call<String> obtenerPreguntaSeguridad(@Query("id") String id);

    @POST("usuarios/login/recuperarCuenta")
    Call<Usuario> recuperarCuenta(@Body OlvContra datos);

    @POST("usuarios/login/cambiarContraseña")
    Call<Void> cambiarContraseña(@Body Usulogin datos);

    // Gestión de usuario
    @DELETE("usuarios/{id}")
    Call<Void> deleteUsuario(@Path("id") String id);

    // Tienda y compras
    @PUT("usuarios/comprar")
    Call<DevolverCompra> comprarObjeto(@Body Compra compra);

    @GET("usuarios/tienda/armas")
    Call<ConsultaTienda> getArmas();

    @GET("usuarios/tienda/skins")
    Call<ConsultaTienda> getSkins();

    // BuzzBlitzService.java (corregir URLs)
    @GET("usuarios/tienda/{id}/armas")
    Call<ConsultaTienda> getArmasUsuario(@Path("id") String usuarioId);

    @GET("usuarios/tienda/{id}/skins")
    Call<ConsultaTienda> getSkinsUsuario(@Path("id") String usuarioId);

    @PUT("usuarios/tienda/{id}/intercambio")
    Call<Intercambio> intercambiarFlores(@Path("id") String usuarioId);

    // Foro y chat (sin cambios, pero marcados como pendientes)
    @GET("forum/posts")
    Call<List<ForumPost>> getForumPosts();

    @POST("forum/posts")
    Call<Void> createForumPost(@Body ForumPost post);

    @GET("chat/messages")
    Call<List<ChatMessage>> getChatMessages();

    @POST("chat/messages")
    Call<Void> sendMessage(@Body ChatMessage message);
}
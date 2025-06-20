package com.example.buzzblitz_android_cliente.Services;

import com.example.buzzblitz_android_cliente.Models.BadgesResponse;
import com.example.buzzblitz_android_cliente.Models.ChatIndividual;
import com.example.buzzblitz_android_cliente.Models.Forum;
import com.example.buzzblitz_android_cliente.Models.Issue;
import com.example.buzzblitz_android_cliente.Models.ListFreqQuest;
import com.example.buzzblitz_android_cliente.Models.Objeto;
import com.example.buzzblitz_android_cliente.Models.PartidaGuardada;
import com.example.buzzblitz_android_cliente.Models.Question;
import com.example.buzzblitz_android_cliente.Models.UsuReg;
import com.example.buzzblitz_android_cliente.Models.Usuario;
import com.example.buzzblitz_android_cliente.Models.UsuarioEnviar;
import com.example.buzzblitz_android_cliente.Models.Usulogin;
import com.example.buzzblitz_android_cliente.Models.Usuario_objeto;
import com.example.buzzblitz_android_cliente.Models.DevolverCompra;
import com.example.buzzblitz_android_cliente.Models.OlvContra;
import com.example.buzzblitz_android_cliente.Models.Intercambio;
import com.example.buzzblitz_android_cliente.Models.InfoList;
import com.example.buzzblitz_android_cliente.Models.VideoListDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GameBuzzBlitzService {

    @POST("usuarios/register")
    Call<Void> registerUsuario(@Body UsuReg usuario);

    @POST("usuarios/login")
    Call<UsuarioEnviar> loginUsuario(@Body Usulogin usuario);

    @DELETE("usuarios/{id}")
    Call<Void> deleteUsuario(@Path("id") String id);

    @GET("usuarios/informacion/{id}")
    Call<InfoList> getInfo(@Path("id") String id);

    @GET("usuarios/login/obtenerPregunta")
    Call<String> obtenerPreguntaSeguridad(@Query("id") String userId);

    @POST("usuarios/login/recuperarCuenta")
    Call<String> recuperarCuenta(@Body OlvContra datos);

    @POST("usuarios/login/cambiarContraseña")
    Call<Void> cambiarContraseña(@Body Usulogin datos);

    @PUT("usuarios/comprar")
    Call<DevolverCompra> comprarObjeto(@Body Usuario_objeto usuarioobjeto);

    @GET("usuarios/tienda/armas")
    //Call<ConsultaTienda> getArmas();
    Call<List<Objeto>> getArmas();

    @GET("usuarios/tienda/skins")
    //Call<ConsultaTienda> getSkins();
    Call<List<Objeto>> getSkins();

    @GET("usuarios/tienda/{id}/armas")
    Call<List<Objeto>> getArmasUsuario(@Path("id") String usuarioId);

    @GET("usuarios/tienda/{id}/skins")
    Call<List<Objeto>> getSkinsUsuario(@Path("id") String usuarioId);

    @PUT("usuarios/tienda/{id}/intercambio")
    Call<Intercambio> intercambiarFlores(@Path("id") String usuarioId);

    @GET("usuarios/media")
    Call<VideoListDTO> getVideos();

    @GET("usuarios/badges/{userId}/badges")
    Call<BadgesResponse> getUserBadges(@Path("userId") String userId);

    @GET("usuarios/faqs")
    Call<ListFreqQuest> getFaqs ();

    @POST("usuarios/question")
    Call<Void> submitQuestion(@Body Question question);

    @POST("usuarios/issue")
    Call<Void> reportIssue(@Body Issue issue);

    @GET("usuarios/issue/all")
    Call<List<Issue>> getIssues();

    @PUT("usuarios/resetData/{id}")
    Call<Void> resetUserData(@Path("id") String userId);

    @GET("usuarios/forum/messages")
    Call<List<Forum>> getForumMessages();

    @POST("usuarios/forum/post")
    Call<Void> postForumMessage(@Body Forum forum);

    @GET("usuarios/chat/users")
    Call<List<Usuario>> getAllUsers();

    @GET("usuarios/chat/{user1}/{user2}")
    Call<List<ChatIndividual>> getPrivateMessages(
            @Path("user1") String user1,
            @Path("user2") String user2
    );

    @POST("usuarios/chat/send")
    Call<Void> sendPrivateMessage(@Body ChatIndividual chat);

    @POST("usuarios/partida/guardar")
    Call<Void> guardarPartida(@Body PartidaGuardada partida);
}
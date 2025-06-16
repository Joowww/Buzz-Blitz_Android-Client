package com.example.buzzblitz_android_cliente.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthUtil {
    public static final String PREFS_NAME = "MyAppPrefs";

    // Keys para las preferencias
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "currentUserId";
    private static final String KEY_USER_NAME = "currentUserName";
    private static final String KEY_USER_SURNAME = "currentUserSurname";
    private static final String KEY_USER_EMAIL = "currentUserEmail";
    private static final String KEY_TARROS_MIEL = "currentTarrosMiel";
    private static final String KEY_FLOR = "currentFlor";
    private static final String KEY_FLORE_GOLD = "currentFloreGold";
    private static final String KEY_BEST_SCORE = "currentBestScore";
    private static final String KEY_NUM_PARTIDAS = "currentNumPartidas";

    // Nueva clave para contraseña temporal
    private static final String KEY_USER_PSWD = "currentUserPassword";

    // Métodos para isLoggedIn
    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void setUserLoggedIn(Context context, boolean isLoggedIn) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    // Métodos para userId
    public static String getCurrentUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USER_ID, "");
    }

    public static void setCurrentUserId(Context context, String userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
    }

    // Métodos para userName
    public static String getCurrentUserName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USER_NAME, "");
    }

    public static void setCurrentUserName(Context context, String userName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
    }

    // Métodos para userEmail
    public static String getCurrentUserEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USER_EMAIL, "");
    }

    public static void setCurrentUserEmail(Context context, String email) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER_EMAIL, email);
        editor.apply();
    }

    // Métodos para tarrosMiel
    public static int getCurrentTarrosMiel(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_TARROS_MIEL, 0);
    }

    public static void setCurrentTarrosMiel(Context context, int tarros) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_TARROS_MIEL, tarros);
        editor.apply();
    }

    // Métodos para flor
    public static int getCurrentFlor(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_FLOR, 0);
    }

    public static void setCurrentFlor(Context context, int flor) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_FLOR, flor);
        editor.apply();
    }

    // Métodos para floreGold
    public static int getCurrentFloreGold(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_FLORE_GOLD, 0);
    }

    public static void setCurrentFloreGold(Context context, int floreGold) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_FLORE_GOLD, floreGold);
        editor.apply();
    }

    // Métodos para bestScore
    public static int getCurrentBestScore(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_BEST_SCORE, 0);
    }

    public static void setCurrentBestScore(Context context, int bestScore) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_BEST_SCORE, bestScore);
        editor.apply();
    }

    // Métodos para numPartidas
    public static int getCurrentNumPartidas(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_NUM_PARTIDAS, 0);
    }

    public static void setCurrentNumPartidas(Context context, int numPartidas) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_NUM_PARTIDAS, numPartidas);
        editor.apply();
    }

    // Nuevos métodos para contraseña temporal
    public static void setCurrentUserPassword(Context context, String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_USER_PSWD, password);
        editor.apply();
    }

    public static String getCurrentUserPassword(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USER_PSWD, "");
    }



    public static void saveUserData(Context context, UsuarioEnviar usuario) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.putString(KEY_USER_ID, usuario.getId());
        editor.putString(KEY_USER_NAME, usuario.getName());
        editor.putString(KEY_USER_EMAIL, usuario.getMail());
        editor.putInt(KEY_TARROS_MIEL, usuario.getTarrosMiel());
        editor.putInt(KEY_FLOR, usuario.getFlor());
        editor.putInt(KEY_FLORE_GOLD, usuario.getFloreGold());
        editor.putInt(KEY_BEST_SCORE, usuario.getMejorPuntuacion());
        editor.putInt(KEY_NUM_PARTIDAS, usuario.getNumPartidas());
        editor.apply();
    }
}
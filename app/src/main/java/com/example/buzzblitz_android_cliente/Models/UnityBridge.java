package com.example.buzzblitz_android_cliente.Models;

import android.content.Context;
import android.content.Intent;

import com.example.buzzblitz_android_cliente.Activities.MainActivity;

public class UnityBridge {
    public static void returnToMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }
}

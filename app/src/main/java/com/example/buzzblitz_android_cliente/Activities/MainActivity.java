package com.example.buzzblitz_android_cliente.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.airbnb.lottie.LottieAnimationView;
import com.example.buzzblitz_android_cliente.Models.AuthUtil;
import com.example.buzzblitz_android_cliente.Models.DevolverCompra;
import com.example.buzzblitz_android_cliente.Models.PartidaGuardada;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.GameBuzzBlitzService;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private boolean isMenuOpen = false;
    private CardView sideMenuCard;
    private LinearLayout menuContent;
    private ImageView ivArrow;
    private LottieAnimationView bees1, bees2, bees3;
    private TextView tvWelcomeMessage;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("showWelcome", false)) {
            String userId = sharedPreferences.getString("currentUserId", "");
            tvWelcomeMessage.setText("Welcome, " + userId);
            tvWelcomeMessage.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                tvWelcomeMessage.setVisibility(View.GONE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("showWelcome", false);
                editor.apply();
            }, 3000);
        }

        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(AuthUtil.getCurrentUserId(this));

        bees1 = findViewById(R.id.lottieBees1);
        bees2 = findViewById(R.id.lottieBees2);
        bees3 = findViewById(R.id.lottieBees3);

        new Handler().postDelayed(() -> bees2.setVisibility(View.VISIBLE), 1000);
        new Handler().postDelayed(() -> bees3.setVisibility(View.VISIBLE), 2000);

        findViewById(R.id.btnChatBuzz).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ChatBotActivity.class)));

        findViewById(R.id.btnOptions).setOnClickListener(v ->
                startActivity(new Intent(this, OpcionesActivity.class)));

        findViewById(R.id.btnExit).setOnClickListener(v ->
                startActivity(new Intent(this, CerrarSesionActivity.class)));

        findViewById(R.id.btnSupportVideos).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, VideosActivity.class));

        });

        sideMenuCard = findViewById(R.id.sideMenuCard);
        menuContent = findViewById(R.id.menuContent);
        ivArrow = findViewById(R.id.ivArrow);
        setupMenuToggle();
        setupLottieAnimations();
    }

    private void setupMenuToggle() {
        sideMenuCard.setOnClickListener(v -> toggleMenu());
        ivArrow.setOnClickListener(v -> toggleMenu());
    }

    private void toggleMenu() {
        if (isMenuOpen) {
            menuContent.setVisibility(View.GONE);
            ivArrow.setVisibility(View.VISIBLE);
            ivArrow.setImageResource(R.drawable.ic_arrow_left);
            sideMenuCard.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.card_collapsed_width);
        } else {
            menuContent.setVisibility(View.VISIBLE);
            ivArrow.setVisibility(View.GONE);
            sideMenuCard.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.card_expanded_width);
        }
        isMenuOpen = !isMenuOpen;
        sideMenuCard.requestLayout();
    }

    private void setupLottieAnimations() {
        int[] animationIds = {R.id.lottieShop, R.id.lottieUser, R.id.lottieSettings, R.id.lottieTrophy, R.id.lottieXat};
        int[] rawFiles = {R.raw.tienda, R.raw.user, R.raw.settings, R.raw.trophy, R.raw.xat};
        Class<?>[] targetActivities = {BeforeTiendaActivity.class, UserProfileActivity.class, SettingsActivity.class, RankingActivity.class, BeforeChatActivity.class};

        for (int i = 0; i < animationIds.length; i++) {
            LottieAnimationView animView = findViewById(animationIds[i]);
            animView.setAnimation(rawFiles[i]);
            final Class<?> activity = targetActivities[i];

            animView.setOnClickListener(v -> {
                animView.playAnimation();
                startActivity(new Intent(MainActivity.this, activity));
            });
        }
    }

    public void jugarClick(View view) {
        try {
            Intent i = new Intent();
            i.setComponent(new ComponentName(
                    "com.DefaultCompany.Buzzblitz",
                    "com.unity3d.player.UnityPlayerGameActivity"
            ));

            i.putExtra("UserID", AuthUtil.getCurrentUserId(this));
            i.putExtra("TarrosDeMiel", String.valueOf(AuthUtil.getCurrentTarrosMiel(this)));
            i.putExtra("Flor", String.valueOf(AuthUtil.getCurrentFlor(this)));
            i.putExtra("GoldenFlor", String.valueOf(AuthUtil.getCurrentFloreGold(this)));
            i.putExtra("record", String.valueOf(AuthUtil.getCurrentBestScore(this)));

            startActivityForResult(i, 0);
        } catch (Exception e) {
            Toast.makeText(this, "You need to install the unity app", Toast.LENGTH_SHORT).show();
            Log.e("UnityLaunchError", "Error launching the Unity app", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            // Actualizar recursos desde Unity
            int newHoney = data.getIntExtra("updatedHoney", 0);
            int newFlowers = data.getIntExtra("updatedFlowers", 0);
            int newGoldenFlowers = data.getIntExtra("updatedGoldenFlowers", 0);
            int newBestScore = data.getIntExtra("updatedBestScore", 0);
            int newPartidas = AuthUtil.getCurrentNumPartidas(this) + 1;

            // Actualizar SharedPreferences usando AuthUtil
            AuthUtil.setCurrentTarrosMiel(this, newHoney);
            AuthUtil.setCurrentFlor(this, newFlowers);
            AuthUtil.setCurrentFloreGold(this, newGoldenFlowers);
            AuthUtil.setCurrentBestScore(this, newBestScore);
            AuthUtil.setCurrentNumPartidas(this, newPartidas);

            // Crear objeto PartidaGuardada
            PartidaGuardada partida = new PartidaGuardada(
                    AuthUtil.getCurrentUserId(this),
                    newFlowers,
                    newBestScore,
                    newPartidas,
                    newGoldenFlowers
            );

            guardarPartidaEnBackend(partida);

            Toast.makeText(this, "¡Game data updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarPartidaEnBackend(PartidaGuardada partida) {
        GameBuzzBlitzService api = RetrofitClient.getApiService();
        Call<Void> call = api.guardarPartida(partida);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("GuardarPartida", "Game saved successfully");
                } else {
                    Log.e("GuardarPartida", "Error saving the game: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("GuardarPartida", "Network error: " + t.getMessage());
            }
        });
    }
}

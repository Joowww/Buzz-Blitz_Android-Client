package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.airbnb.lottie.LottieAnimationView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private boolean isMenuOpen = false;
    private CardView sideMenuCard;
    private LinearLayout menuContent;
    private ImageView ivArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar botones principales
        AppCompatButton btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginBuzzBlitz.class));
            finishAffinity();
        });

        findViewById(R.id.btnPlay).setOnClickListener(v ->
                startActivity(new Intent(this, PlayActivity.class)));

        findViewById(R.id.btnOptions).setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));

        findViewById(R.id.btnCredits).setOnClickListener(v ->
                startActivity(new Intent(this, CreditsActivity.class)));

        // Configurar menú desplegable
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
            // Cerrar menú
            menuContent.setVisibility(View.GONE);
            ivArrow.setVisibility(View.VISIBLE);
            ivArrow.setImageResource(R.drawable.ic_arrow_left);
            sideMenuCard.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.card_collapsed_width);
        } else {
            // Abrir menú
            menuContent.setVisibility(View.VISIBLE);
            ivArrow.setVisibility(View.GONE);
            sideMenuCard.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.card_expanded_width);
        }
        isMenuOpen = !isMenuOpen;
        sideMenuCard.requestLayout();
    }

    private void setupLottieAnimations() {
        int[] animationIds = {
                R.id.lottieShop,
                R.id.lottieUser,
                R.id.lottieSettings,
                R.id.lottieTrophy
        };

        int[] rawFiles = {
                R.raw.tienda,
                R.raw.user,
                R.raw.settings,
                R.raw.trophy
        };

        Class<?>[] targetActivities = {
                ShopActivity.class,
                UserProfileActivity.class,
                SettingsActivity.class,
                TrophyActivity.class
        };

        for (int i = 0; i < animationIds.length; i++) {
            LottieAnimationView animView = findViewById(animationIds[i]);
            animView.setAnimation(rawFiles[i]);
            animView.setProgress(0f);
            final Class<?> activity = targetActivities[i];

            animView.setOnClickListener(v -> {
                animView.playAnimation();
                startActivity(new Intent(MainActivity.this, activity));
            });
        }
    }
}
package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import com.airbnb.lottie.LottieAnimationView;

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
        setupMainButtons();

        // Configurar elementos del menú
        sideMenuCard = findViewById(R.id.sideMenuCard);
        menuContent = findViewById(R.id.menuContent);
        ivArrow = findViewById(R.id.ivArrow);

        setupMenuToggle();
        setupLottieAnimations();
    }

    private void setupMainButtons() {
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
    }

    private void setupMenuToggle() {
        sideMenuCard.setOnClickListener(v -> toggleMenu());
        ivArrow.setOnClickListener(v -> toggleMenu());
    }

    private void toggleMenu() {
        if (isMenuOpen) {
            // Animación de cierre
            menuContent.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .withEndAction(() -> menuContent.setVisibility(View.GONE));

            ivArrow.animate()
                    .rotationBy(180f)
                    .setDuration(300);

            ivArrow.setVisibility(View.VISIBLE);
        } else {
            // Animación de apertura
            menuContent.setAlpha(0f);
            menuContent.setVisibility(View.VISIBLE);
            menuContent.animate()
                    .alpha(1f)
                    .setDuration(300);

            ivArrow.animate()
                    .rotationBy(-180f)
                    .setDuration(300)
                    .withEndAction(() -> ivArrow.setVisibility(View.GONE));
        }

        // Actualizar dimensiones
        ViewGroup.LayoutParams params = sideMenuCard.getLayoutParams();
        params.width = getResources().getDimensionPixelSize(
                isMenuOpen ? R.dimen.card_collapsed_width : R.dimen.card_expanded_width
        );
        sideMenuCard.setLayoutParams(params);

        isMenuOpen = !isMenuOpen;
    }

    private void setupLottieAnimations() {
        Object[][] configs = {
                {R.id.lottieShop, R.raw.tienda, ShopActivity.class},
                {R.id.lottieUser, R.raw.user, UserProfileActivity.class},
                {R.id.lottieSettings, R.raw.settings, SettingsActivity.class},
                {R.id.lottieTrophy, R.raw.trophy, TrophyActivity.class}
        };

        for (Object[] config : configs) {
            LottieAnimationView animView = findViewById((Integer) config[0]);
            animView.setAnimation((Integer) config[1]);
            animView.setScaleType(LottieAnimationView.ScaleType.CENTER_INSIDE);
            animView.setPadding(16, 16, 16, 16);

            final Class<?> targetActivity = (Class<?>) config[2];
            animView.setOnClickListener(v -> {
                animView.playAnimation();
                v.postDelayed(() ->
                                startActivity(new Intent(MainActivity.this, targetActivity)),
                        500
                );
            });
        }
    }
}
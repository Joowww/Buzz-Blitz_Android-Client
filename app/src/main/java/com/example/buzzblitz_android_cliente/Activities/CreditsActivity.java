package com.example.buzzblitz_android_cliente.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.buzzblitz_android_cliente.R;

public class CreditsActivity extends AppCompatActivity {
    private int clickCount = 0;
    private long lastClickTime = 0;
    private static final int CLICK_THRESHOLD = 3;
    private static final long CLICK_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        final LottieAnimationView spaceAnimation = findViewById(R.id.spaceParticleAnimation);
        ImageView logo = findViewById(R.id.ivLogo);

        logo.setOnClickListener(v -> handleLogoClick(spaceAnimation));

        spaceAnimation.setOnClickListener(v -> {
            spaceAnimation.cancelAnimation();
            spaceAnimation.setVisibility(View.INVISIBLE);
        });

        findViewById(R.id.btnShare).setOnClickListener(v -> shareGame());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void handleLogoClick(LottieAnimationView animation) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastClickTime < CLICK_TIMEOUT) {
            clickCount++;
            if (clickCount >= CLICK_THRESHOLD) {
                triggerAnimation(animation);
                clickCount = 0;
            }
        } else {
            clickCount = 1;
        }
        lastClickTime = currentTime;
    }

    private void triggerAnimation(LottieAnimationView animation) {
        animation.setVisibility(View.VISIBLE);
        animation.playAnimation();
        animation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                animation.postDelayed(() -> {
                    if (animation.getVisibility() == View.VISIBLE) {
                        animation.setVisibility(View.INVISIBLE);
                    }
                }, 500);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    private void shareGame() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareMessage = "¡Descubre BuzzBlitz!\nUn juego adictivo de estrategia y acción.\nDescárgalo aquí: [ENLACE]";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Compartir via"));
        } catch (Exception e) {
            Toast.makeText(this, "Error al compartir", Toast.LENGTH_SHORT).show();
        }
    }
}
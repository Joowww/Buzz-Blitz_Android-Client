package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.example.buzzblitz_android_cliente.R;

import android.animation.Animator;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        LottieAnimationView moneyAnim = findViewById(R.id.lottieExchange);
        moneyAnim.setAnimation(R.raw.exchange);
        moneyAnim.setProgress(0f);
        moneyAnim.setOnClickListener(v -> {
            moneyAnim.playAnimation();
            moneyAnim.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    startActivity(new Intent(ShopActivity.this, MoneyExchangeActivity.class));
                    moneyAnim.removeAllAnimatorListeners();
                }
                @Override
                public void onAnimationCancel(Animator animation) { }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
        });

        LottieAnimationView comprarAnim = findViewById(R.id.lottieCompra);
        comprarAnim.setAnimation(R.raw.compra);
        comprarAnim.setProgress(0f);
        comprarAnim.setOnClickListener(v -> {
            comprarAnim.playAnimation();
            comprarAnim.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) { }
                @Override
                public void onAnimationEnd(Animator animation) {
                    startActivity(new Intent(ShopActivity.this, ComprarActivity.class));
                    comprarAnim.removeAllAnimatorListeners();
                }
                @Override
                public void onAnimationCancel(Animator animation) { }
                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
        });
    }
}

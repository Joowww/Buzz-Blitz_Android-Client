package com.example.buzzblitz_android_cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        findViewById(R.id.btnShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareGame();
            }
        });
    }

    private void shareGame() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            String shareMessage = "¡Descubre BuzzBlitz!\n"
                    + "Un juego adictivo de estrategia y acción.\n"
                    + "Descárgalo aquí: [ENLACE]";

            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            startActivity(Intent.createChooser(shareIntent, "Compartir via"));

        } catch (Exception e) {
            Toast.makeText(this, "No se encontraron aplicaciones para compartir", Toast.LENGTH_SHORT).show();
        }
    }
}

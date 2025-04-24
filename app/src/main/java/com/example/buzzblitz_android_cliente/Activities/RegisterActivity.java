package com.example.buzzblitz_android_cliente.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.buzzblitz_android_cliente.Models.UsuReg;
import com.example.buzzblitz_android_cliente.R;
import com.example.buzzblitz_android_cliente.RetrofitClient;
import com.example.buzzblitz_android_cliente.Services.BuzzBlitzService;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmailRegister, etPasswordRegister, etRepeatPassword;
    private EditText etRespuesta1, FullNameRegister, IdUserRegister;
    private Spinner spinnerPregunta1;
    private Button btnRegister, btnGoToLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FullNameRegister = findViewById(R.id.FullName);
        IdUserRegister = findViewById(R.id.IdUserRegister);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        spinnerPregunta1 = findViewById(R.id.spinnerPregunta1);
        etRespuesta1 = findViewById(R.id.etRespuesta1);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoToLogin = findViewById(R.id.btnGoToLogin);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        // Configura el Spinner de preguntes de seguretat
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.security_questions,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPregunta1.setAdapter(adapter);

        btnRegister.setOnClickListener(v -> registerUser(v));
        btnGoToLogin.setOnClickListener(v -> finish());
    }

    public void registerUser(View view) {
        String firstName = FullNameRegister.getText().toString().trim();
        String userId = IdUserRegister.getText().toString().trim();
        String email = etEmailRegister.getText().toString().trim();
        String password = etPasswordRegister.getText().toString().trim();
        String confirmPassword = etRepeatPassword.getText().toString().trim();
        String question1 = spinnerPregunta1.getSelectedItem().toString();
        String answer1 = etRespuesta1.getText().toString().trim();

        if (firstName.isEmpty() || userId.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showToast("Fill in all required fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("The passwords do not match");
            return;
        }

        // Crea l'objecte amb les dades del usuari (LA CONTRASENYA ES ENVIADA EN TEXT CLAR)
        UsuReg newUser = new UsuReg(userId, firstName, password, email, question1, answer1);
        BuzzBlitzService api = RetrofitClient.getApiService();

        // Envia la petició POST a l'API
        Call<Void> call = api.registerUsuario(newUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    guardarDatosLocales(email, question1, answer1); // Guarda pregunta i resposta (hash)
                    showToast("Registration successful!");
                    finish();
                } else {
                    showToast("Registration error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("Network error: " + t.getMessage());
            }
        });
    }

    // Guarda la pregunta i resposta (en hash) a SharedPreferences
    private void guardarDatosLocales(String email, String pregunta, String respuesta) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(email + "_q1", pregunta);
        editor.putString(email + "_a1", hash(respuesta));
        editor.apply();
    }

    // Per garantir la protecció de dades sensibles, en lloc d’emmagatzemar la resposta de seguretat
    // en text clar (per exemple, "El meu gat es diu Whiskers"), es guarda el seu hash (com ara "d4e5f6a7b8...").
    // Això significa que, fins i tot si algú accedeix a les SharedPreferences, no podrà llegir la resposta original.
    // Durant la validació segura, quan l’usuari intenti recuperar el compte, es compararà el hash de la nova resposta amb el valor emmagatzemat,
    // sense revelar mai la resposta real. Aquesta metodologia es basa en la irreversibilitat del SHA-256,
    // un algorisme criptogràficament segur que fa pràcticament impossible revertir un hash per obtenir el text original.
    // Això proporciona una capa addicional de seguretat i privadesa per a les dades sensibles dels usuaris.

    // Em falta implementarla a la contraseña i ampliar excepcions com NoSuchAlgorithmException o UnsupportedEncodingException
    // i es podria afegir un salt per evitar atacs amb diccionaris. Òbviament no pasara a la nostra app però esta be saber-ho.
    //El salt seria un valor aleatori que es genera i s'afegeix a la contrasenya abans de fer el hash.
    // Això faria que dues contrasenyes idèntiques tinguin hashes diferents, la qual cosa dificulta els atacs de diccionari.
    private String hash(String input) {
        try {
            // Crea una instància de SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Converteix la cadena d'entrada a bytes (usant UTF-8)
            byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
            // Converteix els bytes a format hexadecimal
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); // Conversió a hex
                if (hex.length() == 1) hexString.append('0'); // Afegeix un zero si només té 1 dígit
                hexString.append(hex);
            }
            return hexString.toString(); // Retorna un string de 64 caràcters
        } catch (Exception ex) {
            throw new RuntimeException("Hash error", ex);
        }
    }

    private void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
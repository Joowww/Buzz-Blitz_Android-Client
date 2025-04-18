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

import com.example.buzzblitz_android_cliente.Models.Usuario;
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

            // Inicializar vistas
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

            // Configurar spinner de preguntas de seguridad
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.security_questions,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPregunta1.setAdapter(adapter);

            // Listeners de botones
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
                showToast("Rellena todos los campos obligatorios");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showToast("Las contraseñas no coinciden");
                return;
            }

            if (answer1.isEmpty()) {
                showToast("Responde la pregunta de seguridad");
                return;
            }

            Usuario newUser = new Usuario(userId, firstName, password, email);
            BuzzBlitzService api = RetrofitClient.getClient("http://10.0.2.2:8080/dsaApp/").create(BuzzBlitzService.class);

            Call<Usuario> call = api.registerUsuario(newUser);
            call.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()) {
                        guardarDatosLocales(email, question1, answer1);
                        showToast("Registro exitoso!");
                        finish();
                    } else {
                        showToast("Error en registro: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    showToast("Error de red: " + t.getMessage());
                }
            });
        }

        private void guardarDatosLocales(String email, String pregunta, String respuesta) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(email + "_q1", pregunta);
            editor.putString(email + "_a1", hash(respuesta));
            editor.apply();
        }

        private String hash(String input) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch (Exception ex) {
                throw new RuntimeException("Error en hash", ex);
            }
        }

        private void showToast(String mensaje) {
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

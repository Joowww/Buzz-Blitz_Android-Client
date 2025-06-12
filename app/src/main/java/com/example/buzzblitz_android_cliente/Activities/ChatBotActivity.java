package com.example.buzzblitz_android_cliente.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buzzblitz_android_cliente.Adapters.MessageAdapter;
import com.example.buzzblitz_android_cliente.Models.Message;
import com.example.buzzblitz_android_cliente.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ChatBotActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView welcomeTextView;
    private EditText messageEditText;
    private ImageButton sendButton;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    private static final String OPENAI_API_KEY = "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";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Inicializa vistas
        recyclerView      = findViewById(R.id.recycler_view);
        welcomeTextView   = findViewById(R.id.welcome_text);
        messageEditText   = findViewById(R.id.message_edit_text);
        sendButton        = findViewById(R.id.send_btn);

        // Lista y adapter
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        // Botón enviar
        sendButton.setOnClickListener(v -> {
            String question = messageEditText.getText().toString().trim();
            if (!question.isEmpty()) {
                addToChat(question, Message.SENT_BY_ME);
                messageEditText.setText("");
                welcomeTextView.setVisibility(View.GONE);
                callAPI(question);
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addToChat(String message, String sentBy) {
        runOnUiThread(() -> {
            messageList.add(new Message(message, sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    private void callAPI(String question) {
        // Indicamos al usuario que el bot está escribiendo
        addToChat("Typing...", Message.SENT_BY_BOT);

        try {
            // Construcción del JSON para Chat Completions
            JSONObject userMessage = new JSONObject()
                    .put("role", "user")
                    .put("content", question);

            JSONArray messages = new JSONArray()
                    .put(userMessage);

            JSONObject jsonBody = new JSONObject()
                    .put("model", "gpt-3.5-turbo")
                    .put("messages", messages)
                    .put("max_tokens", 128)
                    .put("temperature", 0.0);

            RequestBody body = RequestBody.create(jsonBody.toString(), JSON);

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .header("Authorization", "Bearer " + OPENAI_API_KEY)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    addResponse("Failed to load response: " + e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        String bodyString = response.body().string();
                        try {
                            JSONObject json = new JSONObject(bodyString);
                            JSONArray choices = json.getJSONArray("choices");
                            String result = choices
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");
                            addResponse(result.trim());
                        } catch (JSONException e) {
                            addResponse("Failed to parse response: " + e.getMessage());
                        }
                    } else {
                        // Leemos el errorBody real
                        okhttp3.ResponseBody eb = response.body();
                        String errorJson = (eb != null) ? eb.string() : "";

                        // Intentamos parsear el JSON de error
                        try {
                            JSONObject errObj = new JSONObject(errorJson).getJSONObject("error");
                            String code = errObj.optString("code");
                            String msg  = errObj.optString("message");

                            if ("insufficient_quota".equals(code)) {
                                // Mostramos un Toast avisando de cuota agotada
                                runOnUiThread(() -> Toast.makeText(
                                        ChatBotActivity.this,
                                        "Se ha agotado tu cuota. Revisa tu plan en la consola de OpenAI.",
                                        Toast.LENGTH_LONG
                                ).show());
                            } else {
                                addResponse("Error: " + msg);
                            }
                        } catch (JSONException je) {
                            // Si no es un JSON típico de OpenAI
                            addResponse("Failed to load response: " + errorJson);
                        }
                    }
                }
            });
        } catch (JSONException e) {
            addResponse("Failed to build request JSON: " + e.getMessage());
        }
    }


    private void addResponse(String response) {
        // Quitamos el "Typing..." y añadimos la respuesta real
        runOnUiThread(() -> {
            if (!messageList.isEmpty()) {
                messageList.remove(messageList.size() - 1);
            }
            messageList.add(new Message(response, Message.SENT_BY_BOT));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Si quisieras cancelar todas las peticiones pendientes:
        client.dispatcher().cancelAll();
    }
}

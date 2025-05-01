//package com.example.buzzblitz_android_cliente.Activities;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import com.example.buzzblitz_android_cliente.R;
//
//public class BeforeChatActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_beforechat);
//
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
//        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
//        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));
//
//        findViewById(R.id.btnBack).setOnClickListener(v -> {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        });
//
//        findViewById(R.id.btnChat).setOnClickListener(v ->
//                startActivity(new Intent(this, ChatActivity.class))
//        );
//
//        findViewById(R.id.btnForum).setOnClickListener(v ->
//                startActivity(new Intent(this, ForumActivity.class))
//        );
//    }
//}

package com.example.buzzblitz_android_cliente.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.buzzblitz_android_cliente.R;

public class BeforeChatActivity extends BaseActivity { // Cambia AppCompatActivity por BaseActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beforechat); // Carga tu layout dentro del FrameLayout

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        TextView tvUserIdCorner = findViewById(R.id.tvUserIdCorner);
        tvUserIdCorner.setText(sharedPreferences.getString("currentUserId", ""));

        // Elimina el botón BACK del layout (ya no es necesario)
        // findViewById(R.id.btnBack).setOnClickListener(...);

        findViewById(R.id.btnChat).setOnClickListener(v ->
                startActivity(new Intent(this, ChatActivity.class))
        );

        findViewById(R.id.btnForum).setOnClickListener(v ->
                startActivity(new Intent(this, ForumActivity.class))
        );
    }
}
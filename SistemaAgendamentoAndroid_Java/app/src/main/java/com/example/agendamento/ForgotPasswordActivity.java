package com.example.agendamento;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText edtEmail = findViewById(R.id.edtEmailRecuperacao);

        findViewById(R.id.btnEnviarRecuperacao).setOnClickListener(v -> {
            String email = edtEmail.getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor, insira seu e-mail.", Toast.LENGTH_SHORT).show();
            } else {
                // Simulação de envio
                Toast.makeText(this, "Instruções enviadas para " + email, Toast.LENGTH_LONG).show();
                finish();
            }
        });

        findViewById(R.id.txtVoltarLogin).setOnClickListener(v -> finish());
    }
}

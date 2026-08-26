package com.example.agendamento;

import android.content.Intent;
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
                // Simulação de geração de código (6 dígitos)
                String code = String.valueOf((int)(Math.random() * 900000) + 100000);
                
                // Exibe o código para o usuário testar (Simulando o e-mail recebido)
                Toast.makeText(this, "CÓDIGO ENVIADO: " + code, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, ResetPasswordActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("verificationCode", code);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.txtVoltarLogin).setOnClickListener(v -> finish());
    }
}

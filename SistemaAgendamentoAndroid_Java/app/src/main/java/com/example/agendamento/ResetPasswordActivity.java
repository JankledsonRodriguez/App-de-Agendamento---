package com.example.agendamento;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResetPasswordActivity extends AppCompatActivity {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        String email = getIntent().getStringExtra("email");
        String correctCode = getIntent().getStringExtra("verificationCode");

        // UI Elements
        TextView txtTitle = findViewById(R.id.txtTitle);
        TextView txtSubtitle = findViewById(R.id.txtSubtitle);
        LinearLayout layoutStep1 = findViewById(R.id.layoutStep1);
        LinearLayout layoutStep2 = findViewById(R.id.layoutStep2);
        
        EditText edtCodigo = findViewById(R.id.edtCodigoVerificacao);
        EditText edtNovaSenha = findViewById(R.id.edtNovaSenha);
        EditText edtConfirmar = findViewById(R.id.edtConfirmarNovaSenha);

        // --- STEP 1: Code Verification ---
        findViewById(R.id.btnVerificarCodigo).setOnClickListener(v -> {
            String inputCode = edtCodigo.getText().toString();
            if (inputCode.equals(correctCode)) {
                // Avançar para Step 2
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.VISIBLE);
                txtTitle.setText(R.string.nova_senha);
                txtSubtitle.setText(R.string.msg_senha_forte);
            } else {
                Toast.makeText(this, "Código incorreto. Tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });

        // --- STEP 2: Save New Password ---
        findViewById(R.id.btnSalvarNovaSenha).setOnClickListener(v -> {
            String nova = edtNovaSenha.getText().toString();
            String confirma = edtConfirmar.getText().toString();

            if (nova.isEmpty() || confirma.isEmpty()) {
                Toast.makeText(this, "Preencha ambos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nova.equals(confirma)) {
                Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                return;
            }

            executor.execute(() -> {
                try {
                    ClinicaRepository repo = new ClinicaRepository();
                    boolean ok = repo.atualizarSenha(email, nova);
                    runOnUiThread(() -> {
                        if (ok) {
                            Toast.makeText(this, "Senha atualizada com sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Erro ao atualizar senha no banco.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            });
        });
    }
}

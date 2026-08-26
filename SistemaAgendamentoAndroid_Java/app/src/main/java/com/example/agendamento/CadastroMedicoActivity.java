package com.example.agendamento;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CadastroMedicoActivity extends AppCompatActivity {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_medico);

        EditText edtNome = findViewById(R.id.edtNomeCadastro);
        EditText edtEmail = findViewById(R.id.edtEmailCadastro);
        EditText edtEspecialidade = findViewById(R.id.edtEspecialidadeCadastro);
        EditText edtCRM = findViewById(R.id.edtCRMCadastro);
        EditText edtSenha = findViewById(R.id.edtSenhaCadastro);

        findViewById(R.id.btnFinalizarCadastro).setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            String email = edtEmail.getText().toString();
            String especialidade = edtEspecialidade.getText().toString();
            String crm = edtCRM.getText().toString();
            String senha = edtSenha.getText().toString();

            if (nome.isEmpty() || email.isEmpty() || especialidade.isEmpty() || crm.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            executor.execute(() -> {
                try {
                    ClinicaRepository repo = new ClinicaRepository();
                    // Aqui chamaremos o método de autocadastro que será criado no Repositório
                    boolean ok = repo.autocadastroMedico(nome, email, especialidade, crm, senha);
                    
                    runOnUiThread(() -> {
                        if (ok) {
                            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Erro ao realizar cadastro.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            });
        });

        findViewById(R.id.txtVoltarLogin).setOnClickListener(v -> finish());
    }
}

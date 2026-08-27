package com.example.agendamento;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    private final ExecutorService executor=Executors.newSingleThreadExecutor();

    @Override protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_login);
        EditText email=findViewById(R.id.edtEmail), senha=findViewById(R.id.edtSenha);
        findViewById(R.id.btnLogin).setOnClickListener(v -> executor.execute(() -> {
            try{
                boolean ok=new ClinicaRepository().login(email.getText().toString(),senha.getText().toString());
                runOnUiThread(() -> {
                    if(ok){startActivity(new Intent(this,MainActivity.class));finish();}
                    else
                        Toast.makeText(this,"Login inválido",Toast.LENGTH_SHORT).show();
                });
            }catch(Exception e){runOnUiThread(() -> Toast.makeText(this,"Erro MySQL: "+e.getMessage(),Toast.LENGTH_LONG).show());}
        }));

        findViewById(R.id.txtEsqueceuSenha).setOnClickListener(v -> 
            startActivity(new Intent(this, ForgotPasswordActivity.class))
        );

        findViewById(R.id.btnIrParaCadastro).setOnClickListener(v -> 
            startActivity(new Intent(this, CadastroMedicoActivity.class))
        );
    }
}

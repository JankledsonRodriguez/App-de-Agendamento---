package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.agendamento.R;
import com.example.agendamento.repository.AgendamentoRepository;
import java.util.concurrent.Executors;

public class NovoProfissionalFragment extends Fragment {
    public NovoProfissionalFragment() { super(R.layout.fragment_novo_profissional); }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EditText edtNome = view.findViewById(R.id.edtNomeProfissional);
        EditText edtEspecialidade = view.findViewById(R.id.edtEspecialidade);
        EditText edtTelefone = view.findViewById(R.id.edtTelefoneProfissional);

        view.findViewById(R.id.btnSalvarProfissional).setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            String especialidade = edtEspecialidade.getText().toString();
            String telefone = edtTelefone.getText().toString();

            if (nome.isEmpty() || especialidade.isEmpty() || telefone.isEmpty()) {
                Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    boolean sucesso = new AgendamentoRepository().inserirProfissional(nome, especialidade, telefone);
                    getActivity().runOnUiThread(() -> {
                        if (sucesso) {
                            Toast.makeText(getContext(), "Profissional cadastrado!", Toast.LENGTH_SHORT).show();
                            getParentFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getContext(), "Erro ao cadastrar!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            });
        });
    }
}

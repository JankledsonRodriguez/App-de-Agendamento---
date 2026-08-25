package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.agendamento.R;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.concurrent.Executors;

public class NovoMedicoFragment extends Fragment {
    public NovoMedicoFragment() { super(R.layout.fragment_novo_medico); }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EditText edtNome = view.findViewById(R.id.edtNomeProfissional);
        EditText edtEspecialidade = view.findViewById(R.id.edtEspecialidade);
        EditText edtCRM = view.findViewById(R.id.edtTelefoneProfissional);

        view.findViewById(R.id.btnSalvarProfissional).setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            String especialidade = edtEspecialidade.getText().toString();
            String crm = edtCRM.getText().toString();

            if (nome.isEmpty() || especialidade.isEmpty() || crm.isEmpty()) {
                Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    boolean sucesso = new ClinicaRepository().inserirMedico(nome, especialidade, crm);
                    if (isAdded() && getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (sucesso) {
                                Toast.makeText(getContext(), "Médico cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                getParentFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getContext(), "Erro ao cadastrar médico!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    if (isAdded() && getActivity() != null) {
                        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    }
                }
            });
        });
    }
}

package com.example.agendamento.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.agendamento.R;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;

public class NovoPacienteFragment extends Fragment {
    public NovoPacienteFragment() { super(R.layout.fragment_novo_paciente); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText edtNome = view.findViewById(R.id.edtNome);
        EditText edtCPF = view.findViewById(R.id.edtCPF);
        EditText edtDataNasc = view.findViewById(R.id.edtDataNasc);
        EditText edtTelefone = view.findViewById(R.id.edtTelefone);
        EditText edtEmail = view.findViewById(R.id.edtEmail);
        EditText edtEndereco = view.findViewById(R.id.edtEndereco);

        edtDataNasc.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
                String date = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                edtDataNasc.setText(date);
            }, 1990, 0, 1).show();
        });

        view.findViewById(R.id.btnSalvarCliente).setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            String cpf = edtCPF.getText().toString();
            String dataNasc = edtDataNasc.getText().toString();
            String telefone = edtTelefone.getText().toString();
            String email = edtEmail.getText().toString();
            String endereco = edtEndereco.getText().toString();

            if (nome.isEmpty() || telefone.isEmpty()) {
                Toast.makeText(getContext(), "Nome e Telefone são obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    boolean sucesso = new ClinicaRepository().inserirPaciente(nome, cpf, dataNasc, telefone, email, endereco);
                    if (isAdded() && getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (sucesso) {
                                Toast.makeText(getContext(), R.string.msg_paciente_sucesso, Toast.LENGTH_SHORT).show();
                                getParentFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getContext(), "Erro ao registrar no banco de dados!", Toast.LENGTH_SHORT).show();
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

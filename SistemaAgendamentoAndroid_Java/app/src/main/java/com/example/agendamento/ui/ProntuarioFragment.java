package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.model.Consulta;
import com.example.agendamento.model.Paciente;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.List;
import java.util.concurrent.Executors;

public class ProntuarioFragment extends Fragment {
    private final int pacienteId;
    private RecyclerView recycler;

    public ProntuarioFragment(int pacienteId) {
        super(R.layout.fragment_prontuario);
        this.pacienteId = pacienteId;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        TextView txtNome = v.findViewById(R.id.txtProntuarioNome);
        TextView txtDetalhes = v.findViewById(R.id.txtProntuarioDetalhes);
        TextView txtContato = v.findViewById(R.id.txtProntuarioContato);
        TextView txtEndereco = v.findViewById(R.id.txtProntuarioEndereco);
        recycler = v.findViewById(R.id.recyclerHistorico);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        carregarDados(txtNome, txtDetalhes, txtContato, txtEndereco);
    }

    private void carregarDados(TextView n, TextView d, TextView c, TextView e) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ClinicaRepository repo = new ClinicaRepository();
                Paciente p = repo.buscarPacientePorId(pacienteId);
                List<Consulta> historico = repo.listarConsultasPorPaciente(pacienteId);

                if (isAdded() && getActivity() != null && p != null) {
                    getActivity().runOnUiThread(() -> {
                        n.setText(p.getNome());
                        d.setText("CPF: " + (p.getCpf() != null ? p.getCpf() : "--") + 
                                  " | Nasc: " + (p.getDataNascimento() != null ? p.getDataNascimento() : "--"));
                        c.setText(p.getTelefone() + " | " + (p.getEmail() != null ? p.getEmail() : "--"));
                        e.setText("End: " + (p.getEndereco() != null ? p.getEndereco() : "Não informado"));
                        
                        // Usar o ConsultaAdapter para mostrar o histórico (reaproveitando a lógica de cores e status)
                        recycler.setAdapter(new ConsultaAdapter(historico, (consulta, novoStatus) -> {
                            // No prontuário, a alteração de status também funciona
                            atualizarStatus(consulta.getId(), novoStatus, n, d, c, e);
                        }));
                    });
                }
            } catch (Exception err) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao carregar prontuário", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void atualizarStatus(int id, String status, TextView n, TextView d, TextView c, TextView e) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                new ClinicaRepository().atualizarStatusConsulta(id, status);
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> carregarDados(n, d, c, e));
                }
            } catch (Exception ignored) {}
        });
    }
}

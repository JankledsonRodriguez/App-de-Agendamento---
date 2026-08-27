package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.model.Especialidade;
import com.example.agendamento.repository.ClinicaRepository;
import java.util.List;
import java.util.concurrent.Executors;

public class EspecialidadesFragment extends Fragment {
    private RecyclerView recyclerView;

    public EspecialidadesFragment() { super(R.layout.fragment_especialidades); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerServicos);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        carregarEspecialidades();
    }

    private void carregarEspecialidades() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ClinicaRepository repository = new ClinicaRepository();
                List<Especialidade> lista = repository.listarEspecialidades();
                
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        if (lista.isEmpty()) {
                            Toast.makeText(getContext(), "Nenhuma especialidade com médico cadastrado.", Toast.LENGTH_SHORT).show();
                        }
                        
                        EspecialidadeAdapter adapter = new EspecialidadeAdapter(lista, especialidade -> {
                            ((MainActivity)requireActivity()).abrir(new CorpoClinicoFragment(especialidade.getNome()));
                        });
                        recyclerView.setAdapter(adapter);
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> 
                        Toast.makeText(getContext(), "Erro ao carregar especialidades: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}

package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.R;
import com.example.agendamento.repository.ClinicaRepository;

public class EspecialidadesFragment extends Fragment {
    public EspecialidadesFragment() { super(R.layout.fragment_especialidades); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerServicos);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        ClinicaRepository repository = new ClinicaRepository();
        EspecialidadeAdapter adapter = new EspecialidadeAdapter(repository.listarEspecialidades(), especialidade -> {
            Toast.makeText(requireContext(), "Especialidade: " + especialidade.getNome(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}

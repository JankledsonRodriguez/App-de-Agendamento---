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
import com.example.agendamento.repository.AgendamentoRepository;

public class ServicosFragment extends Fragment {
    public ServicosFragment(){super(R.layout.fragment_servicos);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerServicos);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        AgendamentoRepository repository = new AgendamentoRepository();
        EspecialidadeAdapter adapter = new EspecialidadeAdapter(repository.listarEspecialidades(), especialidade -> {
            Toast.makeText(requireContext(), "Selecionado: " + especialidade.getNome(), Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}

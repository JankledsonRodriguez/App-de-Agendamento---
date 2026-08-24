package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.repository.AgendamentoRepository;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ProfissionaisFragment extends Fragment {
    public ProfissionaisFragment(){super(R.layout.fragment_profissionais);}

    @Override
    public void onViewCreated(View v, Bundle b){
        RecyclerView r = v.findViewById(R.id.recyclerProfissionais);
        r.setLayoutManager(new LinearLayoutManager(requireContext()));

        v.findViewById(R.id.btnNovoProfissional).setOnClickListener(x -> 
            ((MainActivity)requireActivity()).abrir(new NovoProfissionalFragment())
        );

        carregarProfissionais(r);
    }

    private void carregarProfissionais(RecyclerView r) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ArrayList<String> list = new ArrayList<>();
                for(com.example.agendamento.model.Profissional p : new AgendamentoRepository().listarProfissionais())
                    list.add(p.getNome() + "\n" + p.getEspecialidade() + " | " + p.getTelefone());
                
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> r.setAdapter(new SimpleAdapter(list)));
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}

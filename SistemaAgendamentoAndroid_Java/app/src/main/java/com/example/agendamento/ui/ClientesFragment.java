package com.example.agendamento.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.repository.AgendamentoRepository;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class ClientesFragment extends Fragment {
    public ClientesFragment(){super(R.layout.fragment_clientes);}
    @Override public void onViewCreated(View v,Bundle b){
        RecyclerView r=v.findViewById(R.id.recyclerClientes);r.setLayoutManager(new LinearLayoutManager(requireContext()));
        v.findViewById(R.id.btnNovoCliente).setOnClickListener(x->((MainActivity)requireActivity()).abrir(new NovoClienteFragment()));
        Executors.newSingleThreadExecutor().execute(()->{
            try{
                ArrayList<String> list=new ArrayList<>();
                for(com.example.agendamento.model.Cliente c:new AgendamentoRepository().listarClientes())
                    list.add("#"+c.getId()+" - "+c.getNome()+"\n"+c.getTelefone()+" | "+c.getEmail());
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

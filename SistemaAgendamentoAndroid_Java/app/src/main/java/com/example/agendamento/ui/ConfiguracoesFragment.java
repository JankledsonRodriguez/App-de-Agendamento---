package com.example.agendamento.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.agendamento.LoginActivity;
import com.example.agendamento.R;
public class ConfiguracoesFragment extends Fragment {
 public ConfiguracoesFragment(){super(R.layout.fragment_configuracoes);}
 @Override public void onViewCreated(View v,Bundle b){
  v.findViewById(R.id.btnSair).setOnClickListener(x->{startActivity(new Intent(requireContext(),LoginActivity.class));requireActivity().finish();});
 }
}

package com.example.agendamento.ui;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
public class MenuFragment extends Fragment {
 public MenuFragment(){super(R.layout.fragment_menu);}
 @Override public void onViewCreated(View v,Bundle b){
  MainActivity a=(MainActivity)requireActivity();
  v.findViewById(R.id.btnServicos).setOnClickListener(x->a.abrir(new ServicosFragment()));
  v.findViewById(R.id.btnProfissionais).setOnClickListener(x->a.abrir(new ProfissionaisFragment()));
  v.findViewById(R.id.btnHorarios).setOnClickListener(x->a.abrir(new HorariosFragment()));
  v.findViewById(R.id.btnRelatorios).setOnClickListener(x->a.abrir(new RelatoriosFragment()));
  v.findViewById(R.id.btnConfiguracoes).setOnClickListener(x->a.abrir(new ConfiguracoesFragment()));
 }
}

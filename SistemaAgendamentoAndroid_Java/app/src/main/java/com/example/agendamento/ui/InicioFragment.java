package com.example.agendamento.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.agendamento.MainActivity;
import com.example.agendamento.R;
import com.example.agendamento.repository.ClinicaRepository;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

public class InicioFragment extends Fragment {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private SharedPreferences prefs;

    public InicioFragment() { super(R.layout.fragment_inicio); }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        prefs = requireActivity().getSharedPreferences("CliniquePrefs", Context.MODE_PRIVATE);
        
        TextView txtHoje = v.findViewById(R.id.txtHoje);
        TextView txtProximas = v.findViewById(R.id.txtProximas);
        TextView txtConfirmadas = v.findViewById(R.id.txtConfirmadas);
        TextView txtPendentes = v.findViewById(R.id.txtPendentes);
        TextView txtCanceladas = v.findViewById(R.id.txtCanceladas);
        TextView txtTotalPacientes = v.findViewById(R.id.txtTotalPacientes);
        TextView txtTotalMedicos = v.findViewById(R.id.txtTotalMedicos);

        MainActivity activity = (MainActivity) requireActivity();
        String hojeStr = sdf.format(new Date());

        v.findViewById(R.id.cardHoje).setOnClickListener(x -> 
            activity.abrir(new AgendaMedicaFragment(null, hojeStr, hojeStr)));

        v.findViewById(R.id.cardProximas).setOnClickListener(x -> {
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DAY_OF_YEAR, 1);
            activity.abrir(new AgendaMedicaFragment(null, sdf.format(tomorrow.getTime()), "2099-12-31"));
        });

        v.findViewById(R.id.cardConfirmadas).setOnClickListener(x -> 
            activity.abrir(new AgendaMedicaFragment("CONFIRMADO", null, null)));

        v.findViewById(R.id.cardPendentes).setOnClickListener(x -> 
            activity.abrir(new AgendaMedicaFragment("AGENDADO", null, null)));

        v.findViewById(R.id.cardCanceladas).setOnClickListener(x -> 
            activity.abrir(new AgendaMedicaFragment("CANCELADO", null, null)));

        v.findViewById(R.id.cardTotalPacientes).setOnClickListener(x -> 
            activity.abrir(new PacientesFragment()));

        v.findViewById(R.id.cardTotalMedicos).setOnClickListener(x -> 
            activity.abrir(new CorpoClinicoFragment()));

        setupThemeToggle(v);
        carregarEstatisticas(txtHoje, txtProximas, txtConfirmadas, txtPendentes, txtCanceladas, txtTotalPacientes, txtTotalMedicos);
    }

    private void setupThemeToggle(View v) {
        View btnToggle = v.findViewById(R.id.btnThemeToggle);
        View layoutDay = v.findViewById(R.id.layoutDay);
        View layoutNight = v.findViewById(R.id.layoutNight);

        boolean isDark = prefs.getBoolean("dark_mode", false);
        if (isDark) {
            layoutDay.setVisibility(View.GONE);
            layoutNight.setVisibility(View.VISIBLE);
        } else {
            layoutDay.setVisibility(View.VISIBLE);
            layoutNight.setVisibility(View.GONE);
        }

        btnToggle.setOnClickListener(x -> {
            boolean currentMode = prefs.getBoolean("dark_mode", false);
            prefs.edit().putBoolean("dark_mode", !currentMode).apply();
            
            if (!currentMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    private void carregarEstatisticas(TextView hoje, TextView prox, TextView conf, TextView pend, TextView canc, TextView pac, TextView med) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Map<String, Integer> stats = new ClinicaRepository().getEstatisticasDashboard();
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        hoje.setText(String.valueOf(stats.getOrDefault("hoje", 0)));
                        prox.setText(String.valueOf(stats.getOrDefault("proximas", 0)));
                        conf.setText(String.valueOf(stats.getOrDefault("confirmadas", 0)));
                        pend.setText(String.valueOf(stats.getOrDefault("pendentes", 0)));
                        canc.setText(String.valueOf(stats.getOrDefault("canceladas", 0)));
                        pac.setText(String.valueOf(stats.getOrDefault("pacientes", 0)));
                        med.setText(String.valueOf(stats.getOrDefault("medicos", 0)));
                    });
                }
            } catch (Exception e) {
                if (isAdded() && getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erro ao carregar dashboard: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }
        });
    }
}

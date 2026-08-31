package com.example.agendamento;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.example.agendamento.ui.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override protected void onCreate(Bundle b) {
        prefs = getSharedPreferences("CliniquePrefs", MODE_PRIVATE);
        applyTheme();

        java.util.Locale locale = new java.util.Locale("pt", "BR");
        java.util.Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        super.onCreate(b);
        setContentView(R.layout.activity_main);

        setupNavigation();
    }

    private void applyTheme() {
        boolean isDark = prefs.getBoolean("dark_mode", false);
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setupNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navInicio) abrir(new InicioFragment());
            else if (id == R.id.navAgenda) abrir(new ConsultasFragment());
            else if (id == R.id.navClientes) abrir(new PacientesFragment());
            else if (id == R.id.navMenu) abrir(new MenuFragment());
            return true;
        });

        if (getSupportFragmentManager().findFragmentById(R.id.container) == null) {
            bottomNav.setSelectedItemId(R.id.navInicio);
            abrir(new InicioFragment());
        }
    }

    public void abrir(Fragment f) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, f)
            .commit();
    }
}

package com.example.agendamento;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.agendamento.ui.*;
import com.google.android.material.navigationrail.NavigationRailView;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle b) {
        java.util.Locale locale = new java.util.Locale("pt", "BR");
        java.util.Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        super.onCreate(b);
        setContentView(R.layout.activity_main);

        NavigationRailView navRail = findViewById(R.id.navigation_rail);

        navRail.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navInicio) abrir(new InicioFragment());
            else if (id == R.id.navAgenda) abrir(new ConsultasFragment());
            else if (id == R.id.navClientes) abrir(new PacientesFragment());
            else if (id == R.id.navMenu) abrir(new MenuFragment());
            return true;
        });

        // Select first item by default
        if (b == null) {
            navRail.setSelectedItemId(R.id.navInicio);
            abrir(new InicioFragment());
        }
    }

    public void abrir(Fragment f) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, f)
            .commit();
    }
}

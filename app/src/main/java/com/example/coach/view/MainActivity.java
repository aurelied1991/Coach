package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coach.R;

/**
 * Activité principale de l'application
 *  * Sert de menu principal pour accéder aux différentes parties de l'application :
 *  * - Calcul d'IMG (CalculActivity)
 *  * - Historique des profils (HistoActivity)
 */
public class MainActivity extends AppCompatActivity {
    // Bouton pour accéder à l'activité de calcul d'IMG
    private ImageButton btnMonIMG;
    // Bouton pour accéder à l'activité de l'historique
    private ImageButton btnMonHistorique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Active l'affichage plein écran moderne (EdgeToEdge)
        EdgeToEdge.enable(this);
        // Charge le layout XML associé
        setContentView(R.layout.activity_main);
        // Ajuste automatiquement les marges pour les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialisation des objets et menu
        init();
    }

    /**
     * Initialise les composants et crée le menu
     */
    private void init(){
        chargeObjetsGraphiques(); // Récupération des boutons dans le layout
        creerMenu(); // Associe les boutons aux actions
    }

    /**
     * Associe les variables Java aux composants du layout XML
     */
    private void chargeObjetsGraphiques(){
        btnMonIMG = findViewById(R.id.btnMonIMG);
        btnMonHistorique = findViewById(R.id.btnMonHistorique);
    }

    /**
     * Configure les actions des boutons du menu
     */
    private void creerMenu(){
        // Au clic sur le bouton IMG → ouvrir CalculActivity
        btnMonIMG.setOnClickListener(v -> ecouteMenu(CalculActivity.class));
        // Au clic sur le bouton Historique → ouvrir HistoActivity
        btnMonHistorique.setOnClickListener(v -> ecouteMenu(HistoActivity.class));
    }

    /**
     * Méthode générique pour lancer une activité
     * @param classe classe de l'activité à lancer
     */
    private void ecouteMenu(Class classe){
        Intent intent = new Intent(MainActivity.this, classe); // Crée l'intent
        startActivity(intent); // Démarre l'activité
    }
}
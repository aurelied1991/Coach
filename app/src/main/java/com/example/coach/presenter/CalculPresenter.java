package com.example.coach.presenter;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Date;
import com.google.gson.Gson;

/**
 * Presenter du calcul d'IMG
 * Sert d'intermédiaire entre la vue et le modèle Profil
 */
public class CalculPresenter {
    private ICalculView vue; // Interface pour afficher les résultats
    private Profil profil; // Modèle contenant les données du profil
    private static final String NOM_FIC = "coach_fic";
    private static final String PROFIL_CLE = "profil_json";
    private Gson gson;
    private SharedPreferences prefs;

    /**
     * Constructeur du presenter
     * @param vue vue associée pour afficher les résultats
     */
    public CalculPresenter(ICalculView vue, Context context) {
        this.vue = vue;
        this.prefs = context.getSharedPreferences(NOM_FIC, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    /**
     * Crée un profil avec les données fournies et envoie le résultat à la vue
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        profil = new Profil(poids, taille, age, sexe, new Date());
        sauvegarderProfil(profil);

        // On pousse les résultats vers la vue
        vue.afficherResultat(
                profil.getImage(), // Nom de l'image à afficher
                profil.getImg(), // Valeur IMG calculée
                profil.getMessage(), // Message associé (ex: normal, surpoids)
                profil.normal() // Indique si le profil est normal (true/false)
        );
    }

    private void sauvegarderProfil(Profil profil) {
        String json = gson.toJson(profil);
        prefs.edit().putString(PROFIL_CLE, json).apply();
    }

    public void chargerProfil() {
        String json = prefs.getString(PROFIL_CLE, null);
        if (json != null) {
            Profil profil = gson.fromJson(json, Profil.class);
            // Pousser les informations vers la vue
            vue.remplirChamps(
                    profil.getPoids(),
                    profil.getTaille(),
                    profil.getAge(),
                    profil.getSexe()
            );
        }
    }
}

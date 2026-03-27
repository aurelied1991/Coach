package com.example.coach.presenter;
import android.util.Log;

import com.example.coach.api.CoachApi;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter du calcul d'IMG (pattern MVP)
 * Rôle : récupère les données de la vue, utilise le modèle (Profil), communique avec l'API,
 * renvoie les résultats à la vue
 */
public class CalculPresenter {
    private ICalculView vue; // Interface pour afficher les résultats
    private Profil profil; // Modèle contenant les données du profil

    /**
     * Constructeur du presenter : on injecte la vue
     * @param vue vue associée pour afficher les résultats
     */
    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
    }

    /**
     *  Crée un profil + envoie à l'API + affiche le résultat
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        // Création du modèle (et calcul automatique de l'IMG)
        profil = new Profil(poids, taille, age, sexe, new Date());
        // envoie les résultats à la vue
        vue.afficherResultat(profil.getImage(), profil.getImg(),
                profil.getMessage(), profil.normal());
        // Conversion en JSON pour l'API
        String profilJson = CoachApi.getGson().toJson(profil);
        // sollicite l'api et récupère la réponse
        HelperApi.call(HelperApi.getApi().creerProfil(profilJson), new ICallbackApi<Integer>(){
            @Override
            public void onSuccess(Integer result) {
                if (result == 1) {
                    vue.afficherMessage("profil enregistré");
                }else{
                    vue.afficherMessage("échec enregistrement profil");
                }
            }
            @Override
            public void onError() {
                vue.afficherMessage("échec enregistrement profil");
            }
        });
    }

    /**
     * Récupère le dernier profil enregistré depuis l'API et remplit les champs de la vue
     */
    public void chargerDernierProfil() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> result) {
                if(result != null){
                    List<Profil> profils = result;
                    if (profils != null && !profils.isEmpty()) {
                        // récupérer le plus récent
                        Profil dernier = Collections.max(profils,
                                (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                        );
                        vue.remplirChamps(dernier.getPoids(), dernier.getTaille(),
                                dernier.getAge(), dernier.getSexe());
                    }else{
                        vue.afficherMessage("échec récupération dernier profil");
                    }
                }else{
                    vue.afficherMessage("échec récupération dernier profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec récupération dernier profil");
            }
        });
    }
}

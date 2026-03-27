package com.example.coach.presenter;
import android.util.Log;

import com.example.coach.api.CoachApi;
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
        // Conversion en JSON pour l'API
        String profilJson = CoachApi.getGson().toJson(profil);
        // Création du service API
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);
        // Préparation de l'appel HTTP
        Call<ResponseApi<Integer>> call = api.creerProfil(profilJson);
        // Exécution ASYNCHRONE (important en Android)
        call.enqueue(new Callback<ResponseApi<Integer>>() {
            /**
             * Appelé si le serveur répond
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<ResponseApi<Integer>> call, Response<ResponseApi<Integer>> response) {
                // Log debug (super pratique pour vérifier)
                Log.d("API", "code : " + response.body().getCode() +
                        " message : " + response.body().getMessage() +
                        " result : " + response.body().getResult()
                );
                // Vérifie que tout est OK côté serveur
                if (response.isSuccessful() && response.body().getResult() == 1) {
                    // envoie les résultats à la vue
                    vue.afficherResultat(
                            profil.getImage(),
                            profil.getImg(),
                            profil.getMessage(),
                            profil.normal()
                    );
                } else {
                    // Erreur côté API
                    Log.e("API", "Erreur API: " + response.code());
                }
            }

            /**
             * Appelé si erreur réseau (pas de connexion, serveur down…)
             * @param call
             * @param throwable
             */
            @Override
            public void onFailure(Call<ResponseApi<Integer>> call, Throwable throwable) {
                Log.e("API", "Échec d'accès à l'api", throwable);
            }
        });
    }

    /**
     * Récupère le dernier profil enregistré depuis l'API et remplit les champs de la vue
     */
    public void chargerDernierProfil() {
        // Crée l'objet d'accès à l'api avec les différentes méthodes d'accès
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);
        // crée l'objet call qui envoie la demande
        Call<ResponseApi<List<Profil>>> call = api.getProfils();
        // exécute la requête en mode asynchrone
        call.enqueue(new Callback<ResponseApi<List<Profil>>>() {
            @Override
            public void onResponse(Call<ResponseApi<List<Profil>>> call, Response<ResponseApi<List<Profil>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Profil> profils = response.body().getResult();
                    if (profils != null && !profils.isEmpty()) {
                        // récupérer le profil le plus récent
                        Profil dernier = Collections.max(
                                profils,
                                (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                        );
                        // Remplir les champs dans la vue
                        vue.remplirChamps(dernier.getPoids(), dernier.getTaille(),
                                dernier.getAge(), dernier.getSexe());
                    } else {
                        Log.d("API", "Aucun profil trouvé");
                    }
                } else {
                    Log.e("API", "Erreur API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<Profil>>> call, Throwable t) {
                Log.e("API", "Échec d'accès à l'api", t);
            }
        });
    }
}

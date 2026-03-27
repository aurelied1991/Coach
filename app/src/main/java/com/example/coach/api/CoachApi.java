package com.example.coach.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe utilitaire permettant de configurer et fournir une instance de Retrofit
 * pour communiquer avec l'API REST du projet Coach.
 * Implémente un pattern de type Singleton afin d'éviter de recréer plusieurs instances de Retrofit.
 */
public class CoachApi {
    // URL de base de l'API (10.0.2.2 = localhost depuis l'émulateur Android)
    private static final String API_URL = "http://10.0.2.2/rest_coach/";
    // Instance unique de Retrofit (Singleton)
    private static Retrofit retrofit = null;
    // Instance de Gson configurée pour gérer le format des dates
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * Retourne une instance unique de Retrofit.
     * Si elle n'existe pas encore, elle est créée avec la configuration nécessaire.
     * @return instance de Retrofit configurée pour l'API
     */
    public static Retrofit getRetrofit(){
        // Vérifie si l'instance existe déjà
        if (retrofit == null) {
            // Création de l'instance Retrofit avec :
            // - l'URL de base de l'API
            // - le convertisseur Gson pour transformer JSON ↔ objets Java
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        // Retourne l'instance existante ou nouvellement créée
        return retrofit;
    }

    /**
     * Retourne l'instance de Gson utilisée pour la sérialisation/désérialisation JSON
     * @return instance de Gson configurée
     */
    public static Gson getGson() {
        return gson;
    }
}

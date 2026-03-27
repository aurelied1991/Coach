package com.example.coach.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Classe helper pour simplifier l'appel à l'API avec Retrofit
 * Elle centralise la logique pour gérer les réponses et erreurs
 */
public class HelperApi {
    // Instance unique d'accès à l'API
    private static final IRequestApi api = CoachApi.getRetrofit() //récupère l'instance unique d'accès à l'api
            .create(IRequestApi.class); // crée une instance d'une classe ananyme qui implémente l'interface

    /**
     * Retourne l'instance de l'API pour pouvoir appeler les méthodes (ex: getProfils)
     * @return instance IRequestApi
     */
    public static IRequestApi getApi(){
        return api;
    }

    /**
     * Méthode générique pour exécuter un appel API et gérer la réponse
     * @param call Call Retrofit avec la réponse de type ResponseApi<T>
     * @param callback callback pour gérer succès ou erreur
     * @param <T> type du résultat attendu
     */
    public static <T> void call(Call<ResponseApi<T>> call, ICallbackApi<T> callback){
        call.enqueue(new Callback<ResponseApi<T>>() { // exécute la requête en arrière-plan
            @Override
            public void onResponse(Call<ResponseApi<T>> call, Response<ResponseApi<T>> response) {
                // Log pour debug
                Log.d("API", "code : " + response.body().getCode() +
                        " message : " + response.body().getMessage() +
                        " result : " + response.body().getResult()
                );
                // Si succès et réponse non nulle
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResult()); // on renvoie le résultat
                } else {
                    callback.onError(); // erreur côté API
                    Log.e("API", "Erreur API: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<ResponseApi<T>> call, Throwable throwable) {
                // Erreur réseau ou exception
                callback.onError();
                Log.e("API", "Erreur API", throwable);
            }
        });
    }
}

package com.example.coach.api;

/**
 * Interface pour gérer le résultat d'un appel API
 * @param <T> type du résultat attendu
 */
public interface ICallbackApi<T> {
    void onSuccess(T result); // appelé si succès
    void onError(); // appelé si erreur
}

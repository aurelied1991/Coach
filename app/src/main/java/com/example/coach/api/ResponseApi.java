package com.example.coach.api;

/**
 * Classe générique représentant la structure d'une réponse de l'API REST.
 * Elle permet de récupérer : un code de statut (succès ou erreur), un message associé et
 * un résultat de type générique (T)
 * @param <T> type de l'objet contenu dans la réponse (ex : Profil, Liste, etc.)
 */
public class ResponseApi <T> {
    // Code de réponse (ex : 200 = succès, 400 = erreur...)
    private int code;
    // Message descriptif retourné par l'API
    private String message;
    // Résultat de la requête (objet ou liste)
    private T result;

    /**
     * Retourne le code de la réponse API
     * @return code HTTP ou code métier
     */
    public int getCode() {
        return code;
    }

    /**
     * Retourne le message associé à la réponse
     * @return message de succès ou d'erreur
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retourne le résultat de la requête
     * @return objet de type T (dépend de l'appel API)
     */
    public T getResult() {
        return result;
    }
}

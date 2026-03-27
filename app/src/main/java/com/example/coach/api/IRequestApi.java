package com.example.coach.api;

import com.example.coach.model.Profil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Interface Retrofit décrivant les endpoints de l'API REST.
 * Retrofit utilise cette interface pour générer automatiquement le code permettant de communiquer avec le serveur.
 */
public interface IRequestApi {
    /**
     * Récupère la liste des profils depuis l'API
     * Requête HTTP : GET /profil
     * @return un appel Retrofit contenant une réponse API avec une liste de profils
     */
    @GET("profil")
    Call<ResponseApi<List<Profil>>> getProfils();

    /**
     * Envoie un nouveau profil à l'API pour l'enregistrer.
     * Requête HTTP : POST /profil
     * Le profil est envoyé sous forme de chaîne JSON dans le champ "champs"
     * @param profilJson profil converti en JSON
     * @return un appel Retrofit contenant une réponse API avec un entier (ex : ID du profil créé)
     */
    @FormUrlEncoded
    @POST("profil")
    Call<ResponseApi<Integer>> creerProfil(@Field("champs") String profilJson);

    /**
     * Envoi en DELETE pour supprimer un profil
     * le nom de la table ("profil") est ajouté à l'url
     * ainsi que le paramètre "champs" en clé et profilJson en valeur
     * @param profilJson profil au format json (à envoyer)
     * @return objet Call permettant d'exécuter la requête (la réponse étant le nombre de lignes supprimées)
     */
    @DELETE("profil/{champs}")
    Call<ResponseApi<Integer>> supprProfil(@Path(value = "champs", encoded = true) String profilJson);
}

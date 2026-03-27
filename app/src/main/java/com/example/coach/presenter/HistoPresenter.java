package com.example.coach.presenter;

import android.util.Log;

import com.example.coach.api.CoachApi;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter pour la vue historique des profils (liste)
 * Rôle : récupérer la liste des profils, les trier, les supprimer ou transférer
 */
public class HistoPresenter {
    private IHistoView vue; // vue associée (affichage historique)
    public HistoPresenter(IHistoView vue) {
        this.vue = vue;
    } // Constructeur : injection de la vue

    /**
     * Charge tous les profils depuis l'API et les affiche dans la vue
     */
    public void chargerProfils() {
        // Sollicite l'api et récupère la réponse
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>(){
            @Override
            public void onSuccess(List<Profil> result) {
                if(result != null){
                    List<Profil> profils = result;
                    if (profils != null && !profils.isEmpty()) {
                        // Trie les profils par date décroissante (plus récent en premier)
                        Collections.sort(profils, (p1, p2) -> p2.getDateMesure().compareTo(p1.getDateMesure()));
                        vue.afficherListe(profils); // affichage dans la vue
                    }else{
                        vue.afficherMessage("échec chargement profils");
                    }
                }else{
                    vue.afficherMessage("échec chargement profils");
                }
            }
            @Override
            public void onError() {
                vue.afficherMessage("échec chargement profils");
            }
        });
    }

    /**
     * Supprime dans la BDD (et la liste) le profil reçu en paramètre
     * @param profil
     * @param callback
     */
    public void supprProfil (Profil profil, ICallbackApi<Void> callback){
        // Convertit le profil en JSON
        String profilJson = CoachApi.getGson().toJson(profil);
        // sollicite l'api et récupère la réponse
        HelperApi.call(HelperApi.getApi().supprProfil(profilJson), new ICallbackApi<Integer>(){
            @Override
            public void onSuccess(Integer result) {
                if (result == 1) {
                    callback.onSuccess(null);
                    vue.afficherMessage("profil supprimé");
                }else{
                    vue.afficherMessage("échec supression profil");
                }
            }
            @Override
            public void onError() {
                vue.afficherMessage("échec suppression profil");
            }
        });
    }

    /**
     * Demnde de transfert du profil vers une autre activity
     * @param profil
     */
    public void transfertProfil(Profil profil){
        vue.transfertProfil(profil);
    }
}

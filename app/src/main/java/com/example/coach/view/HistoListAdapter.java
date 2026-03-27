package com.example.coach.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coach.R;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.HistoPresenter;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HistoListAdapter extends RecyclerView.Adapter<HistoListAdapter.ViewHolder> {
    private List<Profil> profils; // Liste des profils à afficher
    private IHistoView vue; // Vue associée pour les callbacks (affichage messages, transfert profil)

    /**
     * Constructeur
     * @param profils liste des profils
     * @param vue vue associée (HistoActivity)
     */
    public HistoListAdapter(List<Profil> profils, IHistoView vue) {
        this.profils = profils;
        this.vue = vue;
    }

    /**
     * Crée la vue pour un item (appelé par RecyclerView)
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public HistoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(parentContext);
        // On “inflate” le layout XML de l’item
        View view = layout.inflate(R.layout.layout_liste_histo, parent, false);
        return new HistoListAdapter.ViewHolder(view);
    }

    /**
     * Lie les données d’un profil aux vues de l’item
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HistoListAdapter.ViewHolder holder, int position) {
        // IMG formatée avec 1 décimale
        Double img = profils.get(position).getImg();
        String img1desimal = String.format("%.01f", img);
        holder.txtListIMG.setText(img1desimal);
        // Date formatée pour affichage
        Date dateMesure = profils.get(position).getDateMesure();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dateFormatee = sdf.format(dateMesure);
        holder.txtListDate.setText(dateFormatee);
    }

    /**
     * Nombre d’items dans la liste
     * @return
     */
    @Override
    public int getItemCount() {
        return profils.size();
    }

    /**
     * ViewHolder : contient les vues de chaque item
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtListDate;
        public final TextView txtListIMG;
        public final ImageButton btnListSupp;
        private HistoPresenter presenter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Récupération des vues depuis le layout
            txtListDate = (TextView) itemView.findViewById(R.id.txtListDate);
            txtListIMG = (TextView) itemView.findViewById(R.id.txtListIMG);
            btnListSupp = (ImageButton) itemView.findViewById(R.id.btnListSupp);
            init(); // configuration des listeners
        }

        /**
         * Initialise les actions sur les boutons et champs
         */
        private void init(){
            presenter = new HistoPresenter(vue); // chaque item peut appeler le presenter
            btnListSupp.setOnClickListener(v -> btnListSuppr_clic());
            txtListDate.setOnClickListener(v -> txtListDateOrImg_clic());
            txtListIMG.setOnClickListener(v -> txtListDateOrImg_clic());
        }

        /**
         * Suppression d’un profil via l’API
         */
        private void btnListSuppr_clic(){
            int position = getBindingAdapterPosition(); // position actuelle
            presenter.supprProfil(profils.get(position), new ICallbackApi<Void>() {
                @Override
                public void onSuccess(Void result) {
                    // Suppression de l’item de la liste et notification RecyclerView
                    profils.remove(position);
                    notifyItemRemoved(position);
                }
                @Override
                // Message d’erreur ici
                public void onError() {
                    vue.afficherMessage("Erreur suppression profil");
                }
            });
        }

        /**
         * Transfert du profil sélectionné vers CalculActivity
         */
        private void txtListDateOrImg_clic(){
            int position = getBindingAdapterPosition();
            presenter.transfertProfil(profils.get(position));
        }
    }
}

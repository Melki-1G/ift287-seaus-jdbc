package SeauS.gestion;

import SeauS.SeauSException;
import SeauS.tables.Communautes;
import SeauS.tables.Projets;
//import SeauS.tuples.Communaute;
import SeauS.tuples.Communaute;
import SeauS.tuples.Projet;
import java.util.List;
import java.sql.SQLException;

public class GestionCommunaute extends GestionTransactions {

    private final Communautes communautes;
    private final Projets projets;

    public GestionCommunaute(Communautes communautes, Projets projets) {
        super(communautes.getConnexion());

        this.communautes = communautes;
        this.projets = projets;
    }

    public void ajouterCommunaute(String nom, String nation, String chef, String coord)
            throws SQLException, SeauSException
    {
        try
        {
            // Vérifier que la communauté n'existe pas déjà
            if (communautes.existe(nom))
            {
                throw new SeauSException("Communauté existe déjà : " + nom);
            }

            // Création de la communauté
            Communaute communaute = new Communaute(nom, nation, chef, coord);
            communautes.ajouterCommunaute(communaute);

            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    // TODO : implémenter le reste des fonctions

    // Fonction servant a lister les projets de la communauté
    public void listerProjetsCommunaute(String nomCommunaute) throws Exception {
        try {
            if (!communautes.existe(nomCommunaute)) {
                throw new SeauSException("Communauté inexistante : " + nomCommunaute);
            }

            List<Projet> listeProjets = projets.getProjetsCompagnie(nomCommunaute);

            for (Projet p : listeProjets) {
                System.out.println(p);
            }

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void supprimerCommunaute(String nom) throws Exception {
        try {

            if (!communautes.existe(nom)) {
                throw new SeauSException("Communauté inexistante : " + nom);
            }

            communautes.supprimerCommunaute(nom);

            cx.commit();

        } catch (Exception e) {

            cx.rollback();
            throw e;
        }
    }

    public void editerCommunaute(String nomActuel,
                                 String nouveauNom,
                                 String nation,
                                 String chef,
                                 String coord) throws Exception {

        try {

            if (!communautes.existe(nomActuel)) {
                throw new SeauSException("Communauté inexistante : " + nomActuel);
            }

            Communaute c =
                    new Communaute(nouveauNom, nation, chef, coord);

            communautes.editerCommunaute(c, nomActuel);

            cx.commit();

        } catch (Exception e) {

            cx.rollback();
            throw e;
        }
    }

    public void afficherCommunaute(String nom) throws Exception {

        try {

            if (!communautes.existe(nom)) {
                throw new SeauSException("Communauté inexistante : " + nom);
            }

            System.out.println("Communauté : " + nom);

            cx.commit();

        } catch (Exception e) {

            cx.rollback();
            throw e;
        }
    }

    public void afficherProjetsCommunaute(String nomCommunaute) throws Exception {
        try {
            if (!communautes.existe(nomCommunaute)) {
                throw new SeauSException("Communauté inexistante : " + nomCommunaute);
            }

            List<Projet> listeProjets = projets.getProjetsCommunaute(nomCommunaute);



            for (Projet p : listeProjets) {
                System.out.println(p);
            }

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
}

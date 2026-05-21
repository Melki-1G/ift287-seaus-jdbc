// GestionProjet.java

package SeauS.gestion;

import SeauS.SeauSException;
import SeauS.tables.Projets;
import SeauS.tuples.Projet;

import java.sql.Date;

public class GestionProjet extends GestionTransactions {

    private final Projets projets;

    public GestionProjet(Projets projets) {
        super(projets.getConnexion());

        this.projets = projets;
    }

    public void ajouterProjet(int idCommunaute,
                              int idCompagnie,
                              float budgetInitial,
                              float budgetFinal,
                              String chargeProjet,
                              String dateAnnonce,
                              String dateDebut,
                              String dateFin,
                              String etatAvancement) throws Exception {

        try {

            Projet p = new Projet(
                    0,
                    idCommunaute,
                    idCompagnie,
                    budgetInitial,
                    budgetFinal,
                    chargeProjet,
                    Date.valueOf(dateAnnonce),
                    Date.valueOf(dateDebut),
                    Date.valueOf(dateFin),
                    etatAvancement
            );

            projets.ajouterProjet(p);

            cx.commit();

        } catch (Exception e) {

            cx.rollback();
            throw e;
        }
    }

    public void editerProjet(int idProjet,
                             int idCommunaute,
                             int idCompagnie,
                             float budgetInitial,
                             float budgetFinal,
                             String chargeProjet,
                             String dateAnnonce,
                             String dateDebut,
                             String dateFin,
                             String etatAvancement) throws Exception {

        try {

            if (!projets.existe(idProjet)) {
                throw new SeauSException("Projet inexistant : " + idProjet);
            }

            Projet p = new Projet(
                    idProjet,
                    idCommunaute,
                    idCompagnie,
                    budgetInitial,
                    budgetFinal,
                    chargeProjet,
                    Date.valueOf(dateAnnonce),
                    Date.valueOf(dateDebut),
                    Date.valueOf(dateFin),
                    etatAvancement
            );

            projets.editerProjet(p);

            cx.commit();

        } catch (Exception e) {

            cx.rollback();
            throw e;
        }
    }

    public void afficherProjet(int idProjet) throws Exception {

        try {

            if (!projets.existe(idProjet)) {
                throw new SeauSException("Projet inexistant : " + idProjet);
            }

            Projet p = projets.getProjet(idProjet);

            System.out.println(p);

            cx.commit();

        } catch (Exception e) {

            cx.rollback();
            throw e;
        }
    }

    public void supprimerProjet(int idProjet) throws Exception {

        try {

            if (!projets.existe(idProjet)) {
                throw new SeauSException("Projet inexistant : " + idProjet);
            }

            projets.supprimerProjet(idProjet);

            cx.commit();

        } catch (Exception e) {

            cx.rollback();
            throw e;
        }
    }
}
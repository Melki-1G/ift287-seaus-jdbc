package SeauS.gestion;

import SeauS.SeauSException;
import SeauS.tables.Compagnies;
import SeauS.tables.Parents;
import SeauS.tables.Projets;
import SeauS.tuples.Compagnie;
import SeauS.tuples.Projet;

import java.util.List;

public class GestionCompagnie extends GestionTransactions {

    private final Compagnies compagnies;
    private final Parents parents;
    private final Projets projets;

    public GestionCompagnie(Compagnies compagnies, Parents parents, Projets projets) {
        super(compagnies.getConnexion());

        this.compagnies = compagnies;
        this.parents = parents;
        this.projets = projets;
    }

    public void ajouterCompagnie(String nom, String adresse) throws Exception {
        try {
            if (compagnies.existe(nom)) {
                throw new SeauSException("Compagnie existe déjà : " + nom);
            }

            Compagnie compagnie = new Compagnie(nom, adresse);
            compagnies.ajouterCompagnie(compagnie);

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void editerCompagnie(String nomActuel, String nouveauNom, String adresse) throws Exception {
        try {
            if (!compagnies.existe(nomActuel)) {
                throw new SeauSException("Compagnie inexistante : " + nomActuel);
            }

            Compagnie c = new Compagnie(nouveauNom, adresse);
            compagnies.editerCompagnie(c, nomActuel);

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void afficherCompagnie(String nom) throws Exception {
        try {
            if (!compagnies.existe(nom)) {
                throw new SeauSException("Compagnie inexistante : " + nom);
            }

            Compagnie c = compagnies.getCompagnie(nom);
            System.out.println(c);

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void supprimerCompagnie(String nom) throws Exception {
        try {
            if (!compagnies.existe(nom)) {
                throw new SeauSException("Compagnie inexistante : " + nom);
            }

            compagnies.supprimerCompagnie(nom);

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void afficherProjetsCompagnie(String nomCompagnie) throws Exception {
        try {
            if (!compagnies.existe(nomCompagnie)) {
                throw new SeauSException("Compagnie inexistante : " + nomCompagnie);
            }

            List<Projet> listeProjets = projets.getProjetsCompagnie(nomCompagnie);

            for (Projet p : listeProjets) {
                System.out.println(p);
            }

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void listerProjetsCompagnie(String nomCompagnie) throws Exception {
        afficherProjetsCompagnie(nomCompagnie);
    }

    public void ajouterParent(String idEnfant, String idParent) throws Exception {
        try {
            int enfant = Integer.parseInt(idEnfant);
            int parent = Integer.parseInt(idParent);

            if (parents.existe(enfant, parent)) {
                throw new SeauSException("Relation parent déjà existante.");
            }

            parents.ajouterParent(enfant, parent);

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void enleverParent(String idParent, String idEnfant) throws Exception {
        try {
            int parent = Integer.parseInt(idParent);
            int enfant = Integer.parseInt(idEnfant);

            if (!parents.existe(enfant, parent)) {
                throw new SeauSException("Relation parent inexistante.");
            }

            parents.supprimerParent(enfant, parent);

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }

    public void supprimerParent(String nom) throws Exception {
        supprimerCompagnie(nom);
    }

    public void afficherParents(String nomEnfant) throws Exception {
        try {
            if (!compagnies.existe(nomEnfant)) {
                throw new SeauSException("Compagnie inexistante : " + nomEnfant);
            }

            Compagnie enfant = compagnies.getCompagnie(nomEnfant);
            List<Compagnie> listeParents = parents.getParents(enfant.idCompagnie);

            for (Compagnie c : listeParents) {
                System.out.println(c);
            }

            cx.commit();
        } catch (Exception e) {
            cx.rollback();
            throw e;
        }
    }
}
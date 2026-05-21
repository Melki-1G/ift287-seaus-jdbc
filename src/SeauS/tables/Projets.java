package SeauS.tables;

import SeauS.bdd.Connexion;
import SeauS.tuples.Projet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Projets extends GestionTables {

    private final PreparedStatement stmtExisteProjet;
    private final PreparedStatement stmtInsertProjet;
    private final PreparedStatement stmtUpdateProjet;
    private final PreparedStatement stmtDeleteProjet;
    private final PreparedStatement stmtSelectProjetsCompagnie;
    private final PreparedStatement stmtSelectProjetsCommunaute;

    public Projets(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteProjet = cx.getConnection().prepareStatement(
                "select * from projet where idprojet = ?"
        );

        stmtInsertProjet = cx.getConnection().prepareStatement(
                "insert into projet (idcommunaute, idcompagnie, budget_initial, budget_final, charge_projet, date_annonce, date_debut, date_fin, etat_avancement) values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        stmtUpdateProjet = cx.getConnection().prepareStatement(
                "update projet set idcommunaute = ?, idcompagnie = ?, budget_initial = ?, budget_final = ?, charge_projet = ?, date_annonce = ?, date_debut = ?, date_fin = ?, etat_avancement = ? where idprojet = ?"
        );

        stmtDeleteProjet = cx.getConnection().prepareStatement(
                "delete from projet where idprojet = ?"
        );

        stmtSelectProjetsCompagnie = cx.getConnection().prepareStatement(
                "select p.* from projet p join compagnie c on p.idcompagnie = c.idcompagnie where c.nom_compagnie = ?"
        );

        stmtSelectProjetsCommunaute = cx.getConnection().prepareStatement(
                "select p.* from projet p join communaute c on p.idcommunaute = c.idcommunaute where c.nom_communaute = ?"
        );
    }

    public boolean existe(int idProjet) throws SQLException {
        stmtExisteProjet.setInt(1, idProjet);

        ResultSet rs = stmtExisteProjet.executeQuery();
        boolean existe = rs.next();

        rs.close();

        return existe;
    }

    public int ajouterProjet(Projet p) throws SQLException {
        stmtInsertProjet.setInt(1, p.idCommunaute);
        stmtInsertProjet.setInt(2, p.idCompagnie);
        stmtInsertProjet.setFloat(3, p.budgetInitial);
        stmtInsertProjet.setFloat(4, p.budgetFinal);
        stmtInsertProjet.setString(5, p.chargeProjet);
        stmtInsertProjet.setDate(6, p.dateAnnonce);
        stmtInsertProjet.setDate(7, p.dateDebut);
        stmtInsertProjet.setDate(8, p.dateFin);
        stmtInsertProjet.setString(9, p.etatAvancement);

        return stmtInsertProjet.executeUpdate();
    }

    public int editerProjet(Projet p) throws SQLException {
        stmtUpdateProjet.setInt(1, p.idCommunaute);
        stmtUpdateProjet.setInt(2, p.idCompagnie);
        stmtUpdateProjet.setFloat(3, p.budgetInitial);
        stmtUpdateProjet.setFloat(4, p.budgetFinal);
        stmtUpdateProjet.setString(5, p.chargeProjet);
        stmtUpdateProjet.setDate(6, p.dateAnnonce);
        stmtUpdateProjet.setDate(7, p.dateDebut);
        stmtUpdateProjet.setDate(8, p.dateFin);
        stmtUpdateProjet.setString(9, p.etatAvancement);
        stmtUpdateProjet.setInt(10, p.idProjet);

        return stmtUpdateProjet.executeUpdate();
    }

    public int supprimerProjet(int idProjet) throws SQLException {
        stmtDeleteProjet.setInt(1, idProjet);

        return stmtDeleteProjet.executeUpdate();
    }

    public List<Projet> getProjetsCompagnie(String nomCompagnie) throws SQLException {
        stmtSelectProjetsCompagnie.setString(1, nomCompagnie);

        ResultSet rs = stmtSelectProjetsCompagnie.executeQuery();
        List<Projet> liste = new ArrayList<>();

        while (rs.next()) {
            Projet p = new Projet(
                    rs.getInt("idprojet"),
                    rs.getInt("idcommunaute"),
                    rs.getInt("idcompagnie"),
                    rs.getFloat("budget_initial"),
                    rs.getFloat("budget_final"),
                    rs.getString("charge_projet"),
                    rs.getDate("date_annonce"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("etat_avancement")
            );

            liste.add(p);
        }

        rs.close();

        return liste;
    }

    public List<Projet> getProjetsCommunaute(String nomCommunaute) throws SQLException {

        stmtSelectProjetsCommunaute.setString(1, nomCommunaute);

        ResultSet rs = stmtSelectProjetsCommunaute.executeQuery();

        List<Projet> liste = new ArrayList<>();

        while (rs.next()) {

            Projet p = new Projet(
                    rs.getInt("idprojet"),
                    rs.getInt("idcommunaute"),
                    rs.getInt("idcompagnie"),
                    rs.getFloat("budget_initial"),
                    rs.getFloat("budget_final"),
                    rs.getString("charge_projet"),
                    rs.getDate("date_annonce"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("etat_avancement")
            );

            liste.add(p);
        }

        rs.close();

        return liste;
    }
    public Projet getProjet(int idProjet) throws SQLException {
        stmtExisteProjet.setInt(1, idProjet);

        ResultSet rs = stmtExisteProjet.executeQuery();

        Projet p = null;

        if (rs.next()) {
            p = new Projet(
                    rs.getInt("idprojet"),
                    rs.getInt("idcommunaute"),
                    rs.getInt("idcompagnie"),
                    rs.getFloat("budget_initial"),
                    rs.getFloat("budget_final"),
                    rs.getString("charge_projet"),
                    rs.getDate("date_annonce"),
                    rs.getDate("date_debut"),
                    rs.getDate("date_fin"),
                    rs.getString("etat_avancement")
            );
        }

        rs.close();

        return p;
    }

}

package SeauS.tables;

import SeauS.bdd.Connexion;
import SeauS.tuples.Compagnie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Parents extends GestionTables {

    private final PreparedStatement stmtExisteParent;
    private final PreparedStatement stmtInsertParent;
    private final PreparedStatement stmtDeleteParent;
    private final PreparedStatement stmtSelectParents;

    public Parents(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteParent = cx.getConnection().prepareStatement(
                "select * from parent where idcompagnie_enfant = ? and idcompagnie_parent = ?"
        );

        stmtInsertParent = cx.getConnection().prepareStatement(
                "insert into parent (idcompagnie_enfant, idcompagnie_parent) values (?, ?)"
        );

        stmtDeleteParent = cx.getConnection().prepareStatement(
                "delete from parent where idcompagnie_enfant = ? and idcompagnie_parent = ?"
        );

        stmtSelectParents = cx.getConnection().prepareStatement(
                "select c.* from compagnie c join parent p on c.idcompagnie = p.idcompagnie_parent where p.idcompagnie_enfant = ?"
        );
    }

    public boolean existe(int idEnfant, int idParent) throws SQLException {
        stmtExisteParent.setInt(1, idEnfant);
        stmtExisteParent.setInt(2, idParent);

        ResultSet rs = stmtExisteParent.executeQuery();
        boolean existe = rs.next();

        rs.close();

        return existe;
    }

    public int ajouterParent(int idEnfant, int idParent) throws SQLException {
        stmtInsertParent.setInt(1, idEnfant);
        stmtInsertParent.setInt(2, idParent);

        return stmtInsertParent.executeUpdate();
    }

    public int supprimerParent(int idEnfant, int idParent) throws SQLException {
        stmtDeleteParent.setInt(1, idEnfant);
        stmtDeleteParent.setInt(2, idParent);

        return stmtDeleteParent.executeUpdate();
    }

    public List<Compagnie> getParents(int idEnfant) throws SQLException {
        stmtSelectParents.setInt(1, idEnfant);

        ResultSet rs = stmtSelectParents.executeQuery();
        List<Compagnie> liste = new ArrayList<>();

        while (rs.next()) {
            Compagnie c = new Compagnie(
                    rs.getInt("idcompagnie"),
                    rs.getString("nom_compagnie"),
                    rs.getString("adresse")
            );

            liste.add(c);
        }

        rs.close();

        return liste;
    }
}

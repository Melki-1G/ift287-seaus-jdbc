package SeauS.tables;

import SeauS.bdd.Connexion;
import SeauS.tuples.Compagnie;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Compagnies extends GestionTables {
    private final PreparedStatement stmtExisteCompagnie;
    private final PreparedStatement stmtExisteCompagnie_id;
    private final PreparedStatement stmtInsertCompagnie;
    private final PreparedStatement stmtUpdateCompagnie;
    private final PreparedStatement stmtDeleteCompagnie;
    private final PreparedStatement stmtGetCompagnie;

    public Compagnies(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteCompagnie = cx.getConnection().prepareStatement(
                "select idcompagnie, nom_compagnie, adresse from compagnie where nom_compagnie = ?");
        stmtExisteCompagnie_id = cx.getConnection().prepareStatement(
                "select idcompagnie, nom_compagnie, adresse from compagnie where idcompagnie = ?");
        stmtInsertCompagnie = cx.getConnection().prepareStatement(
                "insert into compagnie (nom_compagnie, adresse) values (?,?)");
        stmtUpdateCompagnie = cx.getConnection().prepareStatement(
                "update compagnie set nom_compagnie = ?, adresse = ? where nom_compagnie = ?");
        stmtDeleteCompagnie = cx.getConnection().prepareStatement(
                "delete from compagnie where nom_compagnie = ?");
        stmtGetCompagnie = cx.getConnection().prepareStatement(
                "select idcompagnie, nom_compagnie, adresse from compagnie where nom_compagnie = ?"
        );
    }

    public boolean existe(String nom) throws SQLException {
        stmtExisteCompagnie.setString(1, nom);
        ResultSet rs = stmtExisteCompagnie.executeQuery();
        boolean existe = rs.next();

        rs.close();
        return existe;
    }

    public Compagnie getCompagnie(String nom) throws SQLException {
        stmtGetCompagnie.setString(1, nom);

        ResultSet rs = stmtGetCompagnie.executeQuery();

        Compagnie compagnie = null;

        if (rs.next()) {
            compagnie = new Compagnie(
                    rs.getInt("idcompagnie"),
                    rs.getString("nom_compagnie"),
                    rs.getString("adresse")
            );
        }

        rs.close();

        return compagnie;
    }

    public int ajouterCompagnie(Compagnie c) throws SQLException {
        stmtInsertCompagnie.setString(1, c.nomCompagnie);
        stmtInsertCompagnie.setString(2, c.adresse);

        return stmtInsertCompagnie.executeUpdate();
    }

    public int editerCompagnie(Compagnie c, String nomActuel) throws SQLException {
        stmtUpdateCompagnie.setString(1, c.nomCompagnie);
        stmtUpdateCompagnie.setString(2, c.adresse);
        stmtUpdateCompagnie.setString(3, nomActuel);

        return stmtUpdateCompagnie.executeUpdate();
    }

    public int supprimerCompagnie(String nom) throws SQLException {
        stmtDeleteCompagnie.setString(1, nom);

        return stmtDeleteCompagnie.executeUpdate();
    }

}

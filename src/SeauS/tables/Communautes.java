package SeauS.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import SeauS.bdd.Connexion;
import SeauS.tuples.Communaute;
import java.sql.PreparedStatement;

public class Communautes extends  GestionTables{

    private final PreparedStatement stmtExisteCommunaute;
    private final PreparedStatement stmtInsertCommunaute;
    private final PreparedStatement stmtUpdateCommunaute;
    private final PreparedStatement stmtDeleteCommunaute;

    public Communautes(Connexion cx) throws SQLException {
        super(cx);

        stmtExisteCommunaute = cx.getConnection().prepareStatement(
                "select * from communaute where nom_communaute = ?"
        );
        stmtInsertCommunaute = cx.getConnection().prepareStatement(
                "insert into communaute (nom_communaute, nation, chef_communaute, coordonnees) values (?, ?, ?, ?)"
        );

        stmtUpdateCommunaute = cx.getConnection().prepareStatement(
                "update communaute set nom_communaute = ?, nation = ?, chef_communaute = ?, coordonnees = ? where nom_communaute = ?"
        );

        stmtDeleteCommunaute = cx.getConnection().prepareStatement(
                "delete from communaute where nom_communaute = ?"
        );
    }

    public boolean existe(String nom) throws SQLException {
        stmtExisteCommunaute.setString(1, nom);

        ResultSet rs = stmtExisteCommunaute.executeQuery();
        boolean existe = rs.next();

        rs.close();

        return existe;
    }

    public int ajouterCommunaute(Communaute c) throws SQLException {
        stmtInsertCommunaute.setString(1, c.nomCommunaute);
        stmtInsertCommunaute.setString(2, c.nation);
        stmtInsertCommunaute.setString(3, c.chefCommunaute);
        stmtInsertCommunaute.setString(4, c.coordonnees);

        return stmtInsertCommunaute.executeUpdate();
    }

    public int editerCommunaute(Communaute c, String nomActuel) throws SQLException {
        stmtUpdateCommunaute.setString(1, c.nomCommunaute);
        stmtUpdateCommunaute.setString(2, c.nation);
        stmtUpdateCommunaute.setString(3, c.chefCommunaute);
        stmtUpdateCommunaute.setString(4, c.coordonnees);
        stmtUpdateCommunaute.setString(5, nomActuel);

        return stmtUpdateCommunaute.executeUpdate();
    }

    public int supprimerCommunaute(String nom) throws SQLException {
        stmtDeleteCommunaute.setString(1, nom);

        return stmtDeleteCommunaute.executeUpdate();
    }
}

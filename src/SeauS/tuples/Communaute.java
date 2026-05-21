package SeauS.tuples;

public class Communaute {
    public int idCommunaute;
    public String nomCommunaute;
    public String nation;
    public String chefCommunaute;
    public String coordonnees;

    public Communaute(int idCommunaute, String nomCommunaute,
                      String nation, String chefCommunaute,
                      String coordonnees) {

        this.idCommunaute = idCommunaute;
        this.nomCommunaute = nomCommunaute;
        this.nation = nation;
        this.chefCommunaute = chefCommunaute;
        this.coordonnees = coordonnees;
    }

    public Communaute(String nomCommunaute,
                      String nation,
                      String chefCommunaute,
                      String coordonnees) {

        this.nomCommunaute = nomCommunaute;
        this.nation = nation;
        this.chefCommunaute = chefCommunaute;
        this.coordonnees = coordonnees;
    }
}

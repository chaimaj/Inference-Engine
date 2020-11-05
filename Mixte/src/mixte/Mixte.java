/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mixte;

import java.io.FileNotFoundException;
import java.util.Vector;

/**
 *
 * @author chaima
 */
public class Mixte {

    private Base_fait BF; // base de faits
    private Base_regle BR; // base de règles
    private Vector<Regle> BRF;
    private String trace = "";

    public Mixte(String fich_fait, String fich_regles) {
        this.BF = new Base_fait();
        this.BR = new Base_regle();
        this.BRF = new Vector<Regle>();

        this.BF.lecture(fich_fait); // appel de lecture
        this.BR.get_file(fich_regles);

    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    // getters and setters
    public Base_fait getBF() {
        return BF;
    }

    public void setBF(Base_fait BF) {
        this.BF = BF;
    }

    public Base_regle getBR() {
        return BR;
    }

    public void setBR(Base_regle BR) {
        this.BR = BR;
    }

    //*********************CHAINAGE AVANT***********************************
    // rechercher un but 
    public String recherche(String but) throws FileNotFoundException {
// instanciation de BF et BR

        Boolean init = false;
        String tr = "BF=" + this.BF.afficher();  // chaine qui permet d'afficher la trace des opérations effectuées

        // on vérifie si le but n'existe pas deja dans la base de faits
        for (int i = 0; i < BF.getFaits().size(); i++) {
            if (BF.getFaits().elementAt(i).equals(but)) {
                init = true;
            } else {
                init = false;
            }
        }
        if (init == true) {
            tr = tr + "\n The goal was found in the initial fact base";
            System.out.println("The goal was found");
        } else {

            int i = 0;
            int j = 8;

// on parcours liste_regle
            while ((BR.getListe_regle().size() > 0) && (i < 9)) {

                Boolean verif = false;

                // pour chaque regle on vérifie si ses prémisses existent dans la base de faits
                if (BR.getListe_regle().elementAt(i).verifier(BF.getFaits()) == true) {
                    for (int k = 0; k < BF.getFaits().size(); k++) {

                        if (BR.getListe_regle().elementAt(i).getResultat().equals(BF.getFaits().elementAt(k))) {
                            verif = true;
                            tr = tr + "\n The rule is verified but the result is already in the fact base";
                            break;
                        } else {
                            verif = false;
                        }
                    }

                    if (verif == false) {
                        //BF.ecrire(BR.getListe_regle().elementAt(i).getResultat(), fich_fait);
                        BF.getFaits().add(BR.getListe_regle().elementAt(i).getResultat());
                        tr = tr + "\n The rule is verified and the result " + BR.getListe_regle().elementAt(i).getResultat() + " was added";
                        tr = tr + "\n BF=" + this.BF.afficher();
                    }
                    if (BR.getListe_regle().elementAt(i).getResultat().equals(but)) {
                        System.out.println("The goal was found");
                        tr = tr + " \n The goal " + but + " was found";
                        break;
                    }
                    BR.supprimer(BR.getListe_regle().elementAt(i));
                    i = 0;
                    j = BR.getListe_regle().size() - 1;
                    continue;
                } else {
                    j--;
                    if (j <= 0) {
                        System.out.println("Goal not found");
                        tr = tr + " \n The goal " + but + " was not found";
                        break;
                    }



                }
                i++;

            }


        }
        return tr;
    }

    public String saturer() throws FileNotFoundException {

        String tr = "BF=" + this.BF.afficher();
        int i = 0;
        int j = 8;

        Boolean verif = false;

        while ((BR.getListe_regle().size() > 0) && (i < 9)) {
            if (BR.getListe_regle().elementAt(i).verifier(BF.getFaits()) == true) {
                for (int k = 0; k < BF.getFaits().size(); k++) {

                    if (BR.getListe_regle().elementAt(i).getResultat().equals(BF.getFaits().elementAt(k))) {
                        verif = true;
                        tr = tr + "\n The rule was verified but the result is already in the fact base";
                        break;
                    } else {
                        verif = false;
                    }
                }

                if (verif == false) {
                    //BF.ecrire(BR.getListe_regle().elementAt(i).getResultat(), fich_fait);
                    BF.getFaits().add(BR.getListe_regle().elementAt(i).getResultat());
                    tr = tr + "\n The rule was verified and the result " + BR.getListe_regle().elementAt(i).getResultat() + " was added";
                    tr = tr + "\n BF=" + this.BF.afficher();
                }
                BR.supprimer(BR.getListe_regle().elementAt(i));
                i = 0;
                j = BR.getListe_regle().size() - 1;
                continue;
            } else {
                j--;
                if (j <= 0) {
                    tr = tr + "\n The base is full";
                    break;
                }
            }
            i++;
        }
        return tr;
    }

    public Vector<Regle> ensemble_conflit(Vector<Regle> br, Vector<String> bf, String tr) {
        BRF.clear();
        int i = 0;
        while ((BR.getListe_regle().size() > 0) && (i < 9)) {

            Boolean verif = false;

            // pour chaque regle on vérifie si ses prémisses existent dans la base de faits
            if (BR.getListe_regle().elementAt(i).verifier(BF.getFaits()) == true) {
                for (int k = 0; k < BF.getFaits().size(); k++) {

                    if (BR.getListe_regle().elementAt(i).getResultat().equals(BF.getFaits().elementAt(k))) {
                        verif = true;
                        tr = tr + "\n The rule is verified but the result is already in the fact base";
                        break;
                    } else {
                        verif = false;
                    }
                }

                if (verif == false) {
                    BRF.add(BR.getListe_regle().elementAt(i));
                }
            }
            i++;
        }
        return BRF;



    }

    public String recherche2(String but) throws FileNotFoundException {
// instanciation de BF et BR

        BRF = new Vector<Regle>();
        Regle R = null;
        Boolean verif = false;


        Boolean init = false;
        String tr = "BF=" + this.BF.afficher();  // chaine qui permet d'afficher la trace des opérations effectuées

        // on vérifie si le but n'existe pas deja dans la base de faits
        for (int i = 0; i < BF.getFaits().size(); i++) {
            if (BF.getFaits().elementAt(i).equals(but)) {
                init = true;
            } else {
                init = false;
            }
        }
        if (init == true) {
            tr = tr + "\n The goal was found in the initial fact base";
            System.out.println("The goal was found");
        } else {

            BRF = ensemble_conflit(BR.getListe_regle(), BF.getFaits(), tr);
            while (BRF.size() > 0) {

                int max = BRF.elementAt(0).getPremisse().size();
                R = new Regle(BRF.elementAt(0).getPremisse(), BRF.elementAt(0).getResultat());
                for (int i = 1; i < BRF.size(); i++) {
                    if (BRF.elementAt(i).getPremisse().size() > max) {
                        max = BRF.elementAt(i).getPremisse().size();
                        R = new Regle(BRF.elementAt(i).getPremisse(), BRF.elementAt(i).getResultat());

                    }

                }
                //BF.ecrire(R.getResultat(), fich_fait);
                BF.getFaits().add(R.getResultat());
                tr = tr + "\n The rule was verified and the result " + R.getResultat() + " was added";
                tr = tr + "\n BF=" + this.BF.afficher();

                if (R.getResultat().equals(but)) {
                    System.out.println("The goal was found");
                    verif = true;
                    tr = tr + " \n the goal " + but + " was found";
                    break;

                }
                BR.supprimer(R);
                BRF = ensemble_conflit(BR.getListe_regle(), BF.getFaits(), tr);

            }


        }
        if ((BRF.isEmpty()) && verif == false) {
            tr = tr + "\n the goal" + but + "was not found";
        }
        return tr;
    }

    public Regle max_premisse(Vector<Regle> vect) {

        int max = vect.elementAt(0).getPremisse().size();

        Regle R = new Regle(vect.elementAt(0).getPremisse(), vect.elementAt(0).getResultat());
        for (int i = 1; i < vect.size(); i++) {
            if (vect.elementAt(i).getPremisse().size() > max) {
                max = vect.elementAt(i).getPremisse().size();
                R = new Regle(vect.elementAt(i).getPremisse(), vect.elementAt(i).getResultat());

            }

        }
        return R;
    }

    public String saturer2() throws FileNotFoundException {
// instanciation de BF et BR

        BRF = new Vector<Regle>();
        Regle R = null;


        Boolean init = false;
        String tr = "BF=" + this.BF.afficher();  // chaine qui permet d'afficher la trace des opérations effectuées


        BRF = ensemble_conflit(BR.getListe_regle(), BF.getFaits(), tr);
        while (BRF.size() > 0) {

            R = max_premisse(BRF);
            //BF.ecrire(R.getResultat(), fich_fait);
            BF.getFaits().add(R.getResultat());
            tr = tr + "\n The rule is verified and the result " + R.getResultat() + " was added";
            tr = tr + "\n BF=" + this.BF.afficher();


            BR.supprimer(R);
            BRF = ensemble_conflit(BR.getListe_regle(), BF.getFaits(), tr);

        }



        if (BRF.isEmpty()) {
            tr = tr + "The base is full";
        }
        return tr;
    }
//********************CHAINAGE ARRIERE********************************

    public Boolean chainage_arriere(String but) {

        trace = trace + "\n BF=" + BF.afficher();
        // on vérifie si le but n'existe pas deja dans la base de faits
        for (int i = 0; i < BF.getFaits().size(); i++) {
            if (BF.getFaits().elementAt(i).equals(but)) {

                trace = trace + "\n The goal " + but + " is in the fact base";
                return true;
            }
        }

        for (int i = 0; i < BR.getListe_regle().size(); i++) {
            // on cherche le but dans les resultats des regles
            if (BR.getListe_regle().elementAt(i).VerifBut(but) == true) {
                trace = trace + "\n wecan get the goal " + but + " from the rule " + BR.getListe_regle().elementAt(i).afficheRegle();
                Boolean res = true;
                for (int k = 0; k < BR.getListe_regle().elementAt(i).getPremisse().size(); k++) {
                    res = res && chainage_arriere(BR.getListe_regle().elementAt(i).getPremisse().elementAt(k));
                }

                if (res) {
                    trace = trace + "\n The goal " + but + " was found";
                    BF.getFaits().add(but);
                    return res;
                }

            }



        }
        trace = trace + "\n The goal " + but + " was not found";
        return false;

    }

  

    public Vector<Regle> tri(Vector<Regle> vect) {

        Boolean inversion = true;
        while (inversion) {
            inversion = false;
            for (int i = 0; i < vect.size()-1; i++) {
                if (vect.elementAt(i).getPremisse().size() < vect.elementAt(i + 1).getPremisse().size()) {
                    Regle inter = vect.elementAt(i);
                    vect.setElementAt(vect.elementAt(i + 1), i);
                    vect.setElementAt(inter, i + 1);
                    inversion = true;
                }

            }
        }
        return vect;
    }

 // Règle ayant le plus de prémisses
    
     public Boolean chainage_arriere2(String but, Vector <Regle> BR2) {

        trace = trace + "\n BF=" + BF.afficher();
        // on vérifie si le but n'existe pas deja dans la base de faits
        for (int i = 0; i < BF.getFaits().size(); i++) {
            if (BF.getFaits().elementAt(i).equals(but)) {

                trace = trace + "\n The goal " + but + " is in the fact base";
                return true;
            }
        }

        for (int i = 0; i < BR2.size(); i++) {
            // on cherche le but dans les resultats des regles
            if (BR.getListe_regle().elementAt(i).VerifBut(but) == true) {
                trace = trace + "\n we can get the goal " + but + " from the rule " + BR2.elementAt(i).afficheRegle();
                Boolean res = true;
                for (int k = 0; k < BR2.elementAt(i).getPremisse().size(); k++) {
                    res = res && chainage_arriere2(BR2.elementAt(i).getPremisse().elementAt(k), BR2);
                }

                if (res) {
                    trace = trace + "\n the goal " + but + " was found";
                    BF.getFaits().add(but);
                    return res;
                }

            }



        }
        trace = trace + "\n the goal" + but + " was not found";
        return false;

    }
    
    

    //***************************CHAINAGE MIXTE********************************
    public Boolean existe(Regle regle) {

        for (int k = 0; k < BF.getFaits().size(); k++) {

            if (regle.getResultat().equals(BF.getFaits().elementAt(k))) {
                return true;

            }

        }
        return false;
    }

    public Boolean chainage_mixte(String but) {
        Vector<String> inter = new Vector<String>();

        trace = trace + " \n *** Forward chaining ***";


        // on vérifie si le but n'existe pas deja dans la base de faits
        for (int i = 0; i < BF.getFaits().size(); i++) {
            if (BF.getFaits().elementAt(i).equals(but)) {
                trace = trace + "\n BF=" + BF.afficher();
                trace = trace + "\n The goal " + but + " is in the fact base";
                return true;
            }
        }
        trace = trace + "\n" + but + " is not in the fact base";
        Boolean nouv = false;

        for (int i = 0; i < BR.getListe_regle().size(); i++) {

            if (this.existe(BR.getListe_regle().elementAt(i))) {
                continue;
            } else if (BR.getListe_regle().elementAt(i).verifier(BF.getFaits()) == true) {
                trace = trace + "\n The rule " + BR.getListe_regle().elementAt(i).afficheRegle() + " is verified, we add " + BR.getListe_regle().elementAt(i).getResultat() + " in the fact base";
                trace = trace + "\n BF=" + BF.afficher();
                inter.add(BR.getListe_regle().elementAt(i).getResultat());

                nouv = true;



                if (BR.getListe_regle().elementAt(i).getResultat().equals(but)) {
                    trace = trace + "\n The goal " + but + " was found";
                    return true;
                }
            }

        }

        for (int j = 0; j < inter.size(); j++) {
            BF.getFaits().add(inter.elementAt(j));
        }
        if (nouv) {
            return arriere(but);
        }
        trace = trace + "\n The goal " + but + "was not found";
        return false;

    }

    public Boolean arriere(String but) {
        trace = trace + "\n *** Backward chaining ***";
        Boolean verif = false;
        for (int i = 0; i < BR.getListe_regle().size(); i++) {

            if (BR.getListe_regle().elementAt(i).VerifBut(but) == true) {
                trace = trace + "\n We can get the goal from the rule " + BR.getListe_regle().elementAt(i).afficheRegle();
                verif = BR.getListe_regle().elementAt(i).verifier(BF.getFaits());

            }
            if (verif) {
                trace = trace + "\n BF=" + BF.afficher();
                trace = trace + "\n The elements of the rule " + BR.getListe_regle().elementAt(i).afficheRegle() + " are in the fact base";

                return verif;
            }


        }
        trace = trace + "\n the elements of the rule are not in the fact base";
        return chainage_mixte(but);
    }
    
    
    // mixte ayant le plus de prémisses
    
     public Boolean chainage_mixte2(String but, Vector <Regle> BR2) {
        Vector<String> inter = new Vector<String>();

        trace = trace + " \n *** forward chaining ***";


        // on vérifie si le but n'existe pas deja dans la base de faits
        for (int i = 0; i < BF.getFaits().size(); i++) {
            if (BF.getFaits().elementAt(i).equals(but)) {
                trace = trace + "\n BF=" + BF.afficher();
                trace = trace + "\n The goal " + but + " is in the fact base";
                return true;
            }
        }
        trace = trace + "\n" + but + " is not in the fact base";
        Boolean nouv = false;

        for (int i = 0; i < BR2.size(); i++) {

            if (this.existe(BR2.elementAt(i))) {
                continue;
            } else if (BR2.elementAt(i).verifier(BF.getFaits()) == true) {
                trace = trace + "\n The rule " + BR2.elementAt(i).afficheRegle() + " is verified, we add " + BR2.elementAt(i).getResultat() + " in the fact base";
                trace = trace + "\n BF=" + BF.afficher();
                inter.add(BR2.elementAt(i).getResultat());

                nouv = true;



                if (BR2.elementAt(i).getResultat().equals(but)) {
                    trace = trace + "\n The goal " + but + " was found";
                    return true;
                }
            }

        }

        for (int j = 0; j < inter.size(); j++) {
            BF.getFaits().add(inter.elementAt(j));
        }
        if (nouv) {
            return arriere2(but, BR2);
        }
        trace = trace + "\n The goal " + but + "was not found";
        return false;

    }

    public Boolean arriere2(String but, Vector <Regle> BR2) {
        trace = trace + "\n *** Backward chaining ***";
        Boolean verif = false;
        for (int i = 0; i < BR2.size(); i++) {

            if (BR2.elementAt(i).VerifBut(but) == true) {
                trace = trace + "\n We can get the goal from the rule " + BR2.elementAt(i).afficheRegle();
                verif = BR2.elementAt(i).verifier(BF.getFaits());

            }
            if (verif) {
                trace = trace + "\n BF=" + BF.afficher();
                trace = trace + "\n the elements of the rule " + BR2.elementAt(i).afficheRegle() + " are in the fact base";

                return verif;
            }


        }
        trace = trace + "\n the elements of this rule are not in the fact base";
        return chainage_mixte2(but, BR2);
    }
    
    
    
    

   
}

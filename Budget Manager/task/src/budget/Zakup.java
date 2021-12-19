package budget;

import java.io.Serializable;

public class Zakup implements Serializable {

    private static final long serialVersionUID = 1L;

    String nazwa;
    float cena;
    String kategoria;


    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }
}

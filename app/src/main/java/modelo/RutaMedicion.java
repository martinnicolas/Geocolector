package modelo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fede on 14/03/2017.
 */

@Entity
public class RutaMedicion {

    @Id
    private Long id;

    @NotNull
    private String domicilio;
    private String ape_y_nom;
    private String cartegoria;
    @Generated(hash = 2122712593)
    public RutaMedicion(Long id, @NotNull String domicilio, String ape_y_nom,
            String cartegoria) {
        this.id = id;
        this.domicilio = domicilio;
        this.ape_y_nom = ape_y_nom;
        this.cartegoria = cartegoria;
    }
    @Generated(hash = 1731830566)
    public RutaMedicion() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDomicilio() {
        return this.domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public String getApe_y_nom() {
        return this.ape_y_nom;
    }
    public void setApe_y_nom(String ape_y_nom) {
        this.ape_y_nom = ape_y_nom;
    }
    public String getCartegoria() {
        return this.cartegoria;
    }
    public void setCartegoria(String cartegoria) {
        this.cartegoria = cartegoria;
    }

}

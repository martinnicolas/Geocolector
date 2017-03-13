package modelo;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by fede on 10/03/2017.
 */

public class TomaEstado {

    @Id
    private int nro_legajo;

    @NotNull
    private String nombre;
    private String password;
    private Boolean administrador;

}

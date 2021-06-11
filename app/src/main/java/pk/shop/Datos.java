package pk.shop;

public class Datos {

    private int id;
    private String nombre;
    private String usuario;
    private String localitation;
    private int Imagen;
    private String ciudad;
    private String adrees;
    private String creacion;

    public Datos(int id, String nombre, String usuario, String localitation, int imagen, String ciudad, String adrees, String creacion) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.localitation = localitation;
        Imagen = imagen;
        this.ciudad = ciudad;
        this.adrees = adrees;
        this.creacion = creacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLocalitation() {
        return localitation;
    }

    public void setLocalitation(String localitation) {
        this.localitation = localitation;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getAdrees() {
        return adrees;
    }

    public void setAdrees(String adrees) {
        this.adrees = adrees;
    }

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }
}

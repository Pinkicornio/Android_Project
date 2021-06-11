package pk.shop;

public class productos {
    private int id;
    private String nombre;
    private String usuario;
    private String marca;
    private String precio;

    private String Stock;
    private String StockMin;
    private String idProducto;
    private String fechaCompra;
    private int imagen;

    public productos(int id, String nombre, String usuario, String marca, String precio, String stock, String stockMin, String idProducto, String fechaCompra, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.marca = marca;
        this.precio = precio;

        this.Stock = stock;
        this.StockMin = stockMin;
        this.idProducto = idProducto;
        this.fechaCompra = fechaCompra;
        this.imagen = imagen;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getStockMin() {
        return StockMin;
    }

    public void setStockMin(String stockMin) {
        StockMin = stockMin;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}

package info.fashion.bag.models;

public class TarjeticCode {

    private Integer id_canje;
    private String codigo;
    private Integer cantidad_fichas;
    private String estado_cupon;

    public Integer getId_canje() {
        return id_canje;
    }

    public void setId_canje(Integer id_canje) {
        this.id_canje = id_canje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidad_fichas() {
        return cantidad_fichas;
    }

    public void setCantidad_fichas(Integer cantidad_fichas) {
        this.cantidad_fichas = cantidad_fichas;
    }

    public String getEstado_cupon() {
        return estado_cupon;
    }

    public void setEstado_cupon(String estado_cupon) {
        this.estado_cupon = estado_cupon;
    }

    @Override
    public String toString() {
        return "TarjeticCode{" +
                "id_canje=" + id_canje +
                ", codigo='" + codigo + '\'' +
                ", cantidad_fichas=" + cantidad_fichas +
                ", estado_cupon='" + estado_cupon + '\'' +
                '}';
    }

}

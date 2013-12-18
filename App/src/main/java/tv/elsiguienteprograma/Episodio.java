package tv.elsiguienteprograma;

public class Episodio {
  private int temporada;
  private int episodio;
  private String nombre;
  private String link;

  public int getTemporada() {
    return temporada;
  }

  public void setTemporada(int temporada) {
    this.temporada = temporada;
  }

  public int getEpisodio() {
    return episodio;
  }

  public void setEpisodio(int episodio) {
    this.episodio = episodio;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Episodio episodio1 = (Episodio) o;

    if (episodio != episodio1.episodio) return false;
    if (temporada != episodio1.temporada) return false;
    if (link != null ? !link.equals(episodio1.link) : episodio1.link != null) return false;
    if (nombre != null ? !nombre.equals(episodio1.nombre) : episodio1.nombre != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = temporada;
    result = 31 * result + episodio;
    result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
    result = 31 * result + (link != null ? link.hashCode() : 0);
    return result;
  }

  @Override public String toString() {
    return "Episodio{" +
        "temporada=" + temporada +
        ", episodio=" + episodio +
        ", nombre='" + nombre + '\'' +
        ", link='" + link + '\'' +
        '}';
  }
}

package tv.elsiguienteprograma;

import android.app.Application;
import android.util.SparseArray;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App extends Application {
  public static final String DEVELOPER_KEY = "AIzaSyDzVOjphFDIkmp4aFsPfu3FDAWhjKvpHig";
  private SparseArray<List<Episodio>> mMapaEpisodios;

  @Override public void onCreate() {
    super.onCreate();

    ObjectMapper mapper =
        new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    try {
      InputStream open = getAssets().open("datos.json");
      List<Episodio> episodios = mapper.readValue(open, new TypeReference<List<Episodio>>() {
      });

      mMapaEpisodios = new SparseArray<List<Episodio>>();

      for (Episodio episodio : episodios) {
        int temporada = episodio.getTemporada();
        if (mMapaEpisodios.get(temporada) == null) {
          mMapaEpisodios.put(temporada, new ArrayList<Episodio>());
        }
        episodio.setLink(fixLink(episodio.getLink()));
        mMapaEpisodios.get(temporada, new ArrayList<Episodio>()).add(episodio);
      }

      for (int i = 0; i < mMapaEpisodios.size(); i++) {
        Collections.sort(mMapaEpisodios.get(mMapaEpisodios.keyAt(i)), EPISODIOS_SORTER);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String fixLink(String link) {
    return link.replace("http://youtu.be/", "");
  }

  public int getEpisodiosCount() {
    return mMapaEpisodios.size();
  }

  public List<Episodio> getEpisodios(int temporada) {
    return mMapaEpisodios.get(temporada);
  }

  private static final Comparator<? super Episodio> EPISODIOS_SORTER = new Comparator<Episodio>() {
    @Override public int compare(Episodio episodio, Episodio episodio2) {
      return episodio.getEpisodio() - episodio2.getEpisodio();
    }
  };
}

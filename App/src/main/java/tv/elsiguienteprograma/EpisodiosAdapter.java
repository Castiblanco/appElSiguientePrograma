package tv.elsiguienteprograma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpisodiosAdapter extends BaseAdapter {

  private final List<Episodio> mEpisodios;
  private final int temporada;
  private final LayoutInflater mLayoutInflater;
  private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
  private final ThumbnailListener thumbnailListener;

  public EpisodiosAdapter(Context context, int temporada) {
    this.temporada = temporada;
    App app = (App) context.getApplicationContext();
    mEpisodios = app.getEpisodios(temporada);

    mLayoutInflater = LayoutInflater.from(context);

    thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
    thumbnailListener = new ThumbnailListener();
  }

  @Override public int getCount() {
    return mEpisodios.size();
  }

  @Override public Episodio getItem(int i) {
    return mEpisodios.get(i);
  }

  @Override public long getItemId(int i) {
    return mEpisodios.get(i).getEpisodio() + (temporada * 100);
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    Episodio item = getItem(i);
    ViewHolder holder;
    if (view == null) {
      view = mLayoutInflater.inflate(R.layout.fila_episodio, null);
      holder = new ViewHolder();

      holder.titulo = (TextView) view.findViewById(R.id.titulo);
      holder.info = (TextView) view.findViewById(R.id.info);

      holder.thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
      holder.thumbnail.setTag(item.getLink());
      holder.thumbnail.initialize(App.DEVELOPER_KEY, thumbnailListener);

      view.setTag(holder);
    } else {
      YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
      YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(thumbnail);
      if (loader == null) {
        thumbnail.setTag(item.getLink());
      } else {
        thumbnail.setImageResource(R.drawable.loading_thumbnail);
        loader.setVideo(item.getLink());
      }

      holder = (ViewHolder) view.getTag();
    }

    String episodio = (item.getEpisodio() < 10 ? "0" : "") + item.getEpisodio();
    holder.info.setText(item.getTemporada() + "x" + episodio);
    holder.titulo.setText(item.getNombre());

    return view;
  }

  private final class ThumbnailListener implements YouTubeThumbnailView.OnInitializedListener,
      YouTubeThumbnailLoader.OnThumbnailLoadedListener {

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
      loader.setOnThumbnailLoadedListener(this);
      thumbnailViewToLoaderMap.put(view, loader);
      view.setImageResource(R.drawable.loading_thumbnail);
      String videoId = (String) view.getTag();
      loader.setVideo(videoId);
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView view,
        YouTubeInitializationResult loader) {
      view.setImageResource(R.drawable.no_thumbnail);
    }

    @Override
    public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
    }

    @Override
    public void onThumbnailError(YouTubeThumbnailView view,
        YouTubeThumbnailLoader.ErrorReason errorReason) {
      view.setImageResource(R.drawable.no_thumbnail);
    }
  }

  private static class ViewHolder {
    YouTubeThumbnailView thumbnail;
    TextView titulo;
    TextView info;
  }
}

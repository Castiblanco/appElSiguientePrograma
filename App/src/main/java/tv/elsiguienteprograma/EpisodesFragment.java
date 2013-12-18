package tv.elsiguienteprograma;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class EpisodesFragment extends ListFragment {
  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String TEMPORADA = "section_number";
  private EpisodiosAdapter mAdapter;

  public EpisodesFragment() {
  }

  public static EpisodesFragment newInstance(int temporada) {
    EpisodesFragment fragment = new EpisodesFragment();
    Bundle args = new Bundle();
    args.putInt(TEMPORADA, temporada);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mAdapter = new EpisodiosAdapter(getActivity(), getTemporada());
    setListAdapter(mAdapter);
  }

  @Override public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    Episodio item = mAdapter.getItem(position);
    VideoActivity.start(getActivity(), item.getLink());
  }

  private int getTemporada() {
    return getArguments().getInt(TEMPORADA);
  }
}

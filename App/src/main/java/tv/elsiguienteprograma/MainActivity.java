package tv.elsiguienteprograma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {

  /**
   * The serialization (saved instance state) Bundle key representing the
   * current dropdown position.
   */
  private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Set up the action bar to show a dropdown list.
    final ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(false);
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

    App app = (App) getApplicationContext();
    int episodiosCount = app.getEpisodiosCount();

    // Set up the dropdown list navigation in the action bar.
    String[] temporadas = new String[episodiosCount];
    for (int i = 0; i < temporadas.length; i++) {
      temporadas[i] = getString(R.string.temporada_x, i + 1);
    }

    actionBar.setListNavigationCallbacks(
        // Specify a SpinnerAdapter to populate the dropdown list.
        new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_list_item_1,
            android.R.id.text1, temporadas), this);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    // Restore the previously serialized current dropdown position.
    if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
      getSupportActionBar().setSelectedNavigationItem(
          savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    // Serialize the current dropdown position.
    outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
        getSupportActionBar().getSelectedNavigationIndex());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.addCategory(Intent.CATEGORY_BROWSABLE);
      intent.setData(Uri.parse("http://elsiguienteprograma.tv"));
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(int position, long id) {
    // When the given dropdown item is selected, show its contents in the
    // container view.
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, EpisodesFragment.newInstance(position + 1))
        .commit();
    return true;
  }
}

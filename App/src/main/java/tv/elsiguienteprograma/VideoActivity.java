package tv.elsiguienteprograma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeFailureRecoveryActivity {

  private static final String EXTRA_VIDEO_ID = "video_id";

  private YouTubePlayerView playerView;
  private YouTubePlayer mPlayer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fullscreen_demo);
    playerView = (YouTubePlayerView) findViewById(R.id.player);
    playerView.initialize(App.DEVELOPER_KEY, this);
  }

  @Override
  public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
      boolean wasRestored) {
    mPlayer = player;
    int controlFlags = mPlayer.getFullscreenControlFlags();
    controlFlags |= YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE;
    mPlayer.setFullscreenControlFlags(controlFlags);
    // Specify that we want to handle fullscreen behavior ourselves.
    player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
    if (!wasRestored) {
      player.loadVideo(getVideoId());
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mPlayer != null) {
      mPlayer.release();
    }
  }

  @Override
  protected YouTubePlayer.Provider getYouTubePlayerProvider() {
    return playerView;
  }

  private String getVideoId() {
    return getIntent().getStringExtra(EXTRA_VIDEO_ID);
  }

  public static void start(Context context, String id) {
    Intent intent = new Intent(context, VideoActivity.class);
    intent.putExtra(EXTRA_VIDEO_ID, id);
    context.startActivity(intent);
  }
}

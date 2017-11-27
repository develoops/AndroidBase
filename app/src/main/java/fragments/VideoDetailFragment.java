package fragments;

import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * Created by Alvaro on 2/23/15.
 */
public class VideoDetailFragment extends YouTubePlayerSupportFragment {

    public static String YouTubeId;

    public VideoDetailFragment() { }

    public static VideoDetailFragment newInstance(String videoId) {

        VideoDetailFragment f = new VideoDetailFragment();

        YouTubeId = videoId;
        f.init();
        Log.i("HOLA",YouTubeId);
        f.setRetainInstance(true);
        return f;
    }

    private void init() {

        initialize("AIzaSyCJ9u1le-ei9nOakV8DbT0VZhHaroFBXUw", new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) { }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {

                    player.cueVideo(""+YouTubeId+"");
                    player.setFullscreenControlFlags(0);
                    player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {
                                 Log.i("FULLSCREEN","HOLA");


                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    return true;
                }
                return false;
            }
        });

        Log.i("HOLA", "HOLA");

    }


}
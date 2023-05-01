package ru.student.detected.educator.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class PauseVideoView extends VideoView {
    private PlayPauseListener mListener;
    public PauseVideoView(Context context) {
        super(context);
    }

    public PauseVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PauseVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PauseVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setPlayPauseListener(PlayPauseListener listener) {
        mListener = listener;
    }
    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void start() {
        super.start();
        if (mListener != null)   {
            mListener.onPlay();
        }
    }

    public interface PlayPauseListener {
        void onPlay();
        void onPause();
    }

}


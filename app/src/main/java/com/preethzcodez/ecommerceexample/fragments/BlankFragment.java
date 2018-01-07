package com.preethzcodez.ecommerceexample.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.preethzcodez.ecommerceexample.R;

/**
 * Created by Preeth on 1/7/2018.
 */

// Blank Fragment For Helping In Removing Fragment With Animation
public class BlankFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.blank, container, false);
    }

    @Override
    public Animation onCreateAnimation (int transit, boolean enter, int nextAnim) {
        //Check if the superclass already created the animation
        Animation anim = super.onCreateAnimation(transit, enter, nextAnim);

        //If not, and an animation is defined, load it now
        if (anim == null && nextAnim != 0) {
            anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }

        //If there is an animation for this fragment, add a listener.
        if (anim != null) {
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart (Animation animation) {
                    onAnimationStarted();
                }

                @Override
                public void onAnimationEnd (Animation animation) {
                    onAnimationEnded();
                }

                @Override
                public void onAnimationRepeat (Animation animation) {
                    onAnimationRepeated();
                }
            });
        }
        return anim;
    }

    protected void onAnimationStarted () {}

    @SuppressWarnings("ConstantConditions")
    protected void onAnimationEnded () {
        // Remove Fragment
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fm.findFragmentById(R.id.fragment));
        ft.commit();
    }

    protected void onAnimationRepeated () {}
}

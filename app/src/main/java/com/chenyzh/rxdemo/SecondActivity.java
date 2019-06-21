package com.chenyzh.rxdemo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;

/**
 * Created by chenyanzhang on 2019/6/19.
 */
public class SecondActivity extends AppCompatActivity {

  ViewGroup rootView;
  Button back;
  int circularX;
  int circularY;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
    circularX = getIntent().getIntExtra("circularX",0);
    circularY = getIntent().getIntExtra("circularY",0);
    setContentView(R.layout.activity_test);
    rootView = findViewById(R.id.root_layout);
    back = findViewById(R.id.back);
    if (savedInstanceState == null) {
      rootView.setVisibility(View.INVISIBLE);
      ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
      if (viewTreeObserver.isAlive()) {
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
          @Override
          public void onGlobalLayout() {
            circularRevealAct();
            if (VERSION.SDK_INT < VERSION_CODES.JELLY_BEAN) {
              rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
              rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
          }
        });
      }
    }
    back.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        circularRevealBack(v);
      }
    });
  }

  private void circularRevealAct() {
    float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());
    Animator circularReveal = ViewAnimationUtils
        .createCircularReveal(rootView, circularX, circularY, 0, finalRadius);
    circularReveal.setDuration(1000);
    rootView.setVisibility(View.VISIBLE);
    circularReveal.start();
  }

  private void circularRevealBack(View v){
    float finalRadius = Math.max(rootView.getWidth(), rootView.getHeight());
    Animator circularReveal = ViewAnimationUtils
        .createCircularReveal(rootView, (v.getLeft()+v.getRight())/2, (v.getTop()+v.getBottom())/2, finalRadius, 0);
    circularReveal.setDuration(1000);
    circularReveal.addListener(new AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animation) {

      }

      @Override
      public void onAnimationEnd(Animator animation) {
        rootView.setVisibility(View.INVISIBLE);
        finish();
      }

      @Override
      public void onAnimationCancel(Animator animation) {
      }

      @Override
      public void onAnimationRepeat(Animator animation) {

      }
    });
    circularReveal.start();
  }
}

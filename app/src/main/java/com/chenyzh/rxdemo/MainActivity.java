package com.chenyzh.rxdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  OperatorManager operatorManager;

  Button subscribe;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    operatorManager = new OperatorManager(this);
    subscribe = findViewById(R.id.subscribe);
    subscribe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        operatorManager.combainObs();
        Intent  intent =new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("circularX",(v.getLeft()+v.getRight())/2);
        intent.putExtra("circularY",(v.getTop()+v.getBottom())/2);
        startActivity(intent);
      }
    });

  }
}

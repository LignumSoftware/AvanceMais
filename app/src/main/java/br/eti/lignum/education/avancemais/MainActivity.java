package com.MaNoS.DarcyRibeiro.Tech.Leonardo;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity{
    private Aluno a;
    private AlunoDao ad;
    private DataBaseHelper helper;
    private Context ctx;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        try {
            helper = new DataBaseHelper(ctx);
        } catch (IOException e) {
            Log.e("Main.oncreate", e.getMessage());
            e.printStackTrace();
        }
        a = new Aluno();
        ad = new AlunoDao(helper);
        gestureDetector = new GestureDetector(this, new MyFlingGestureDetector());
        ad.readAluno(a);
        Log.w("Oncreate", "fim do onCreate");
    }

    protected void onStart() {
        super.onStart();
        Log.d("onStart", "início do método onStart - aluno " + a.getMatricula());
        //adicionar update do circulo
        Log.d("onStart", "fim do método onStart - aluno " + a.getMatricula());
    }

    protected void onResume() {
        super.onResume();
        MontarMenu(ctx);
        Log.d("onResume", "fim do método - aluno " + a.getMatricula());
    }


    protected void MontarMenu(Context ctx) {
        Log.d("MontarMenu", "início do método");
        //desenhar o circulo
        Log.d("MontarMenu", "fim do método");

    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean tratouEvento = gestureDetector.onTouchEvent(event);
        //Toast.makeText(ctx, "Entrei no touch event", Toast.LENGTH_LONG ).show();
        if (tratouEvento) {
            return tratouEvento;
        }
        return super.onTouchEvent(event);
    }

    class MyFlingGestureDetector extends SimpleOnGestureListener {
        private float swipeMinDistance = 100;
        private float swipeThreasholdVelocity = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float VelocityY) {
            try {
                if (e1.getX() - e2.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThreasholdVelocity) {
                    Log.i("gesto", "1 if");
                }
                if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThreasholdVelocity) {
                    Log.i("gesto", "2 if");
                }
            } catch (Exception e) {
            }
            return false;

        }
    }


    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        //ajustar menu de opções
        switch (item.getItemId()) {
            case R.id.action_sincronizartarefas:
                td.AtualizarTarefas(ctx, a);
                MontarMenu(ctx);
                return true;
            case R.id.action_sincronizarmaterias:
                MateriasDao md = new MateriasDao(helper);
                md.AtualizarMaterias(ctx);
                return true;
            case R.id.action_sincronizartiposdetarefas:
                TipodeTarefaDao tipodao = new TipodeTarefaDao(helper);
                tipodao.AtualizarTipodeTarefas(ctx);
                return true;
            case R.id.action_settings:
                //Configurações
                Intent it = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(it);
                helper.close();
                return true;
            default:
                return super.onMenuItemSelected(featureId, item);
        }
    }
}
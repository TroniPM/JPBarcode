package com.troni.jpbarcode;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.troni.jpbarcode.bd.SharedPreferencesManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 0;
    private TextView txResult;

    public static int ipPort = 1613;

    public static SharedPreferencesManager db = null;

    private static String textMain = "2016 JP Contábil";

    Thread thread;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SharedPreferencesManager(this);

        setContentView(R.layout.activity_main);

        txResult = (TextView) findViewById(R.id.txResult);
        getData();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void callZXing(View view) {
        Intent it = new Intent(MainActivity.this, com.google.zxing.client.android.CaptureActivity.class);
        startActivityForResult(it, REQUEST_CODE);
    }

    public void setTextMain() {
        ((TextView) findViewById(R.id.textViewInfo)).setText(textMain + " | IP: " + db.getIp() + " | Chave: " + db.getKey());
    }

    public void getData() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editIP = new EditText(this);
        editIP.setHint("IP");
        editIP.setText(db.getIp());
        layout.addView(editIP);

        final EditText editKey = new EditText(this);
        editKey.setHint("Chave");
        editKey.setText(db.getKey());
        layout.addView(editKey);

        dialog.setPositiveButton("Salvar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        db.setIp(editIP.getText().toString().trim());
                        db.setKey(editKey.getText().toString().trim());

                        if (!editIP.getText().toString().isEmpty() && !editKey.getText().toString().isEmpty())
                            setTextMain();
                    }
                });

        dialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        dialog.dismiss();
                    }
                });

        dialog.setView(layout);

        dialog.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

            String str = data.getStringExtra("SCAN_RESULT");
            char[] aux = str.toCharArray();

            str = "";
            int j = 0;
            for (int i = 0; i < aux.length; i++) {
                str = str + aux[i];
                if (j == 3) {
                    j = -1;
                    if (i != aux.length - 1)
                        str += ".";
                }
                j++;
            }
            Log.v(this.getClass().getName(), data.getStringExtra("SCAN_RESULT"));
            txResult.setText(str);

            //Sending DATA_CONVERSION
            boolean iai = false;

            final Activity actv = this;

            runnable = new Runnable() {
                @Override
                public void run() {
                    TCPClient.sendData(actv, new Danfe(data.getStringExtra("SCAN_RESULT"), "", db.getKey()));
                }
            };

            new Thread(runnable).start();
            /*
            if (iai) {
                Toast.makeText(this, "Enviado", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(this, "Erro ao se conectar ao computador. PERMITA a conexão.", Toast.LENGTH_LONG);
            }*/
        }
    }
}

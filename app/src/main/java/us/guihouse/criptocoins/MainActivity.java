package us.guihouse.criptocoins;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncTaskResult{

    private static final String URLREQUEST = "https://api.coinmarketcap.com/v1/ticker?limit=10";
    private Button btnTest;
    private TextView tvTest;
    private AsyncTaskRequestHttp asynTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTest = (Button) findViewById(R.id.btnCallAPI);
        tvTest = (TextView) findViewById(R.id.tvJsonTest);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asynTask = new AsyncTaskRequestHttp(MainActivity.this, URLREQUEST);
                asynTask.execute();
            }
        });
    }


    @Override
    public void onAsyncTaskResult(ArrayList<CryptoCoin> result) {
       // tvTest.setText(result);
    }
}

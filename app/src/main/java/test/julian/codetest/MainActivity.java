package test.julian.codetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import test.julian.codetest.Presenters.Interfaces.Interfaces;
import test.julian.codetest.Presenters.WeatherPresenter;

public class MainActivity extends AppCompatActivity implements Interfaces.IView {


    WeatherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new WeatherPresenter(this,this,this);
        presenter.processView(getWindow().getDecorView().findViewById(android.R.id.content),getIntent().getExtras());
    }

    @Override
    public void finishView() {
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        presenter.permissionResults(requestCode,permissions,grantResults);
    }

}

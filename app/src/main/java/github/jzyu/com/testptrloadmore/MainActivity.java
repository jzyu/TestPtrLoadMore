package github.jzyu.com.testptrloadmore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickTestList(View view) {
        startActivity(PtrDemoActivity.newIntent(this, PtrDemoActivity.LayoutType.LIST));
    }
    public void onClickTestGrid(View view) {
        startActivity(PtrDemoActivity.newIntent(this, PtrDemoActivity.LayoutType.GRID));
    }
    public void onClickTestStaggeredGrid(View view) {
        startActivity(PtrDemoActivity.newIntent(this, PtrDemoActivity.LayoutType.STAGGER_GRID));
    }
}

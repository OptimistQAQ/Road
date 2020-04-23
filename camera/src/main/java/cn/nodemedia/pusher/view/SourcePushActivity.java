package cn.nodemedia.pusher.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.nodemedia.pusher.R;
import cn.nodemedia.pusher.view.fragment.PushFragment;

public class SourcePushActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_push);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment pushFragment = new PushFragment();
        fragmentTransaction.add(R.id.push_container, pushFragment);
        fragmentTransaction.commit();

    }
}

package be.hogent.jensbuysse.metartaff.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import be.hogent.jensbuysse.metartaff.R;
import be.hogent.jensbuysse.metartaff.fragments.DetailFragment;
import be.hogent.jensbuysse.metartaff.fragments.OldMetarsFragment;
import be.hogent.jensbuysse.metartaff.fragments.RawFragment;
import be.hogent.jensbuysse.metartaff.models.Metar;

public class MetarDetailActivity extends AppCompatActivity implements OldMetarsFragment.OnOldMetarInteractionListener, RawFragment.OnRawFragmentInteractionListener, DetailFragment.OnDetailFragmentInteractionListener {


    private static final String TAG_RAW = "TAG_FRAGMENT_RAW";
    private static final String TAG_DESCRIPTION = "TAG_FRAGMENT_DESCRIPTION";
    private static final String TAG_OLDMETARS = "TAG_OLDMETARS";


    private Metar metar;

    private List<Fragment> fragments = new ArrayList<>(2);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Logger.i("Presed one of the buttons");
            switch (item.getItemId()) {
                case R.id.raw:
                    switchFragment(0, TAG_RAW);
                    return true;
                case R.id.details:
                    switchFragment(1, TAG_DESCRIPTION);
                    return true;
                case R.id.oldmetars:
                    switchFragment(2, TAG_OLDMETARS);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metar_detail);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        buildFragmentList();
        switchFragment(0, TAG_RAW);


    }

    private void buildFragmentList(){

        Fragment detailFragment = new DetailFragment();
        Fragment rawFragment = new RawFragment();
        Fragment oldMetarFragment = new OldMetarsFragment();
        fragments.add(rawFragment);
        fragments.add(detailFragment);
        fragments.add(oldMetarFragment);

    }

    private void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.metarcontent, fragments.get(pos), tag)
                .commit();
    }

    @Override
    public void onRawFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDetailFragmentInteraction(Uri uri) {

    }

    @Override
    public void onOldMetarFragmentInteraction(Uri uri) {

    }
}

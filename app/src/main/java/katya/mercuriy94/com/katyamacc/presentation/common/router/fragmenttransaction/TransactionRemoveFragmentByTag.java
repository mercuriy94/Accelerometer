package katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Nikita on 12.04.2017.
 */

public class TransactionRemoveFragmentByTag extends FragmentTransaction {


    private String mTag;


    public TransactionRemoveFragmentByTag(String tag) {
        mTag = tag;
    }

    @Override
    public void execute(FragmentManager fragmentManager) {
        Fragment fragment = fragmentManager.findFragmentByTag(mTag);
        if (fragment != null) {

            fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commit();

            fragmentManager.popBackStack();

        }
    }
}

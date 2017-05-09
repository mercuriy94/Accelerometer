package katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Nikita on 12.04.2017.
 */

public class RemoveFragmentTransaction extends FragmentTransaction {

    private final Fragment mFragment;


    public RemoveFragmentTransaction(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void execute(FragmentManager fragmentManager) {

        fragmentManager
                .beginTransaction()
                .remove(mFragment)
                .commit();

        fragmentManager.popBackStack();
    }

}

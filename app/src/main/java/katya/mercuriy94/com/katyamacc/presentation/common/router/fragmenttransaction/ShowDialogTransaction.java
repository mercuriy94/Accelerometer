package katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction;

import android.support.v4.app.FragmentManager;

import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseDialogFragment;

/**
 * Created by Nikita on 09.05.2017.
 */

public class ShowDialogTransaction extends FragmentTransaction {

    private BaseDialogFragment mBaseDialogFragment;

    public ShowDialogTransaction(BaseDialogFragment baseDialogFragment) {
        mBaseDialogFragment = baseDialogFragment;
    }

    @Override
    public void execute(FragmentManager fragmentManager) {

        if (mBaseDialogFragment != null) {
            mBaseDialogFragment.show(fragmentManager, "tag");
        }
    }
}

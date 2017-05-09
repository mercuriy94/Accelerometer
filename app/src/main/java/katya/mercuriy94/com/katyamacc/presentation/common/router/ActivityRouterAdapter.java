package katya.mercuriy94.com.katyamacc.presentation.common.router;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction.FragmentTransaction;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseActivity;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseFragment;
import katya.mercuriy94.com.katyamacc.presentation.common.view.IOnActivityLifecycleListener;


public class ActivityRouterAdapter implements FragmentManager.OnBackStackChangedListener {

    @NonNull
    private final BaseActivity mBaseActivity;

    private int mContainerForFragmentsId;


    public ActivityRouterAdapter(@NonNull BaseActivity baseActivity, int containerForFragmentsId) {
        mBaseActivity = baseActivity;
        mContainerForFragmentsId = containerForFragmentsId;
        mBaseActivity.getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    public ActivityRouterAdapter(@NonNull BaseActivity baseActivity) {
        this(baseActivity, 0);
    }

    public void setOnBackStackChangedListener(@NonNull FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        getBaseActivity().getSupportFragmentManager()
                .addOnBackStackChangedListener(onBackStackChangedListener);
    }

    public void setOnActivityLifecycleListener(@NonNull IOnActivityLifecycleListener lifecycleListener) {
        getBaseActivity().setLifecycleListener(lifecycleListener);
    }

    public int getContainerForFragmentsId() {
        return mContainerForFragmentsId;
    }

    @Nullable
    public Fragment getLastFragmentFromContainer(int container) {
        if (container == 0) return null;
        return getBaseActivity()
                .getSupportFragmentManager()
                .findFragmentById(mContainerForFragmentsId);
    }

    @NonNull
    public BaseActivity getBaseActivity() {
        return mBaseActivity;
    }


    public void navigateToActivity(@NonNull Intent intent) {
        getBaseActivity().startActivity(intent);
    }


    public void executeFragmentTransaction(@NonNull FragmentTransaction fragmentTransaction) {
        fragmentTransaction.execute(getBaseActivity().getSupportFragmentManager());
    }

    public int getBackStackEntryCount() {
        return getBaseActivity().getSupportFragmentManager().getBackStackEntryCount();
    }

    public void finishActivity() {
        getBaseActivity().finish();
    }

    //region OnBackStackChangedListener

    @Override
    public void onBackStackChanged() {
        Fragment fragment = getLastFragmentFromContainer(mContainerForFragmentsId);
        if ((fragment != null) && (fragment instanceof BaseFragment)) {
            ((BaseFragment) fragment).onBackStackChanged((BaseFragment) fragment);
        }
    }

    //endregion OnBackStackChangedListener
}

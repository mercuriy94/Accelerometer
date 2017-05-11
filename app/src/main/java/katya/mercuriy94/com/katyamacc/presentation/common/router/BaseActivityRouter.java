package katya.mercuriy94.com.katyamacc.presentation.common.router;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.PriorityQueue;
import java.util.Queue;

import katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction.AddFragmentTransaction;
import katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction.FragmentTransaction;
import katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction.RemoveFragmentTransaction;
import katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction.ReplaceFragmentTransaction;
import katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction.ShowDialogTransaction;
import katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction.TransactionRemoveFragmentByTag;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseDialogFragment;
import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseFragment;
import katya.mercuriy94.com.katyamacc.presentation.common.view.IOnActivityLifecycleListener;



/**
 * Created by nikita on 11.03.2017.
 */
public abstract class BaseActivityRouter implements IOnActivityLifecycleListener {
    public static final String TAG = "BaseActivityRouter";

    @Nullable
    private ActivityRouterAdapter mRouterAdapter;
    private boolean mActivityStateLoss = false;
    private Queue<FragmentTransaction> mTransactionQueue;

    public BaseActivityRouter(@NonNull ActivityRouterAdapter routeConductor) {
        setRouterAdapter(routeConductor);
        mTransactionQueue = new PriorityQueue<>();
    }

    @Nullable
    public ActivityRouterAdapter getRouteAdapter() {
        return mRouterAdapter;
    }

    public void setRouterAdapter(@Nullable ActivityRouterAdapter routerAdapter) {
        mRouterAdapter = routerAdapter;
        if (mRouterAdapter != null) mRouterAdapter.setOnActivityLifecycleListener(this);
    }

    public void close() {
        if (getRouteAdapter() != null) getRouteAdapter().finishActivity();
    }


    public void navigateToActivity(@NonNull Intent intent) {
        if (getRouteAdapter() != null) getRouteAdapter().navigateToActivity(intent);
    }


    protected void navigateToFragment(@NonNull BaseFragment fragment, int container, String tag) {
        executeTransaction(new AddFragmentTransaction(fragment, container, tag));
    }

    protected void replaceFragment(@NonNull BaseFragment fragment, int container, String tag ){
        executeTransaction(new ReplaceFragmentTransaction(fragment, container, tag));
    }

    protected void showDialogFragment(@NonNull BaseDialogFragment baseDialogFragment) {
        executeTransaction(new ShowDialogTransaction(baseDialogFragment));
    }

    private void executeTransaction(FragmentTransaction transaction) {
        if (mActivityStateLoss || (getRouteAdapter() == null)) mTransactionQueue.add(transaction);
        else getRouteAdapter().executeFragmentTransaction(transaction);
    }

    @SuppressWarnings("ConstantConditions")
    protected void removeFragment(String tag) {
        executeTransaction(new TransactionRemoveFragmentByTag(tag));
    }


    protected void removeFragment(@NonNull BaseFragment baseFragment) {
        executeTransaction(new RemoveFragmentTransaction(baseFragment));
    }

    public void onBackPressed() {
        if (getRouteAdapter() != null) {
            int count = getRouteAdapter().getBackStackEntryCount();
            if (count == 1) {
                getRouteAdapter().finishActivity();
            } else {
                Fragment fragment = getRouteAdapter()
                        .getLastFragmentFromContainer(getRouteAdapter().getContainerForFragmentsId());
                if (fragment != null) removeFragment((BaseFragment) fragment);
            }
        }
    }

    //region IOnActivityLifecycleListener

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onStart() {
        mActivityStateLoss = false;
        if (!mTransactionQueue.isEmpty() && mRouterAdapter != null)
            mTransactionQueue.forEach(mRouterAdapter::executeFragmentTransaction);
    }


    @Override
    public void onStop() {
        mActivityStateLoss = true;
    }

}

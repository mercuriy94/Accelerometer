package katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseFragment;

/**
 * Created by Nikita on 10.05.2017.
 */

public class ReplaceFragmentTransaction extends FragmentTransaction {

    @Nullable
    private String mTag;
    private BaseFragment mFragment;
    private int mContainerForFragmentsId;

    public ReplaceFragmentTransaction(BaseFragment fragment, int containerForFragmentsId) {
        this(fragment, containerForFragmentsId, null);
    }

    public ReplaceFragmentTransaction(BaseFragment fragment,
                                      int containerForFragmentsId,
                                      @Nullable String tag) {
        mFragment = fragment;
        mTag = tag;
        mContainerForFragmentsId = containerForFragmentsId;
    }

    @Nullable
    public String getTag() {
        return mTag;
    }

    public void setTag(@Nullable String tag) {
        mTag = tag;
    }

    public BaseFragment getFragment() {
        return mFragment;
    }

    public void setFragment(BaseFragment fragment) {
        mFragment = fragment;
    }

    public int getContainerForFragmentsId() {
        return mContainerForFragmentsId;
    }

    public void setContainerForFragmentsId(int containerForFragmentsId) {
        mContainerForFragmentsId = containerForFragmentsId;
    }

    @Override
    public void execute(FragmentManager fragmentManager) {

        if (mContainerForFragmentsId == 0) {
            throw new RuntimeException("please set mContainerFragmentId");
        }

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mTag == null) transaction.replace(mContainerForFragmentsId, mFragment);
        else transaction.replace(mContainerForFragmentsId, mFragment, mTag);
        transaction.addToBackStack(mTag).commit();
    }

}

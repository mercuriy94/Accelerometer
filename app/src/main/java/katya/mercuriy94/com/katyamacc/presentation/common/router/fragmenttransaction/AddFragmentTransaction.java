package katya.mercuriy94.com.katyamacc.presentation.common.router.fragmenttransaction;


import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import katya.mercuriy94.com.katyamacc.presentation.common.view.BaseFragment;


/**
 * Created by Nikita on 12.04.2017.
 */

public class AddFragmentTransaction extends FragmentTransaction {

    @Nullable
    private String mTag;
    private BaseFragment mFragment;
    private int mContainerForFragmentsId;

    public AddFragmentTransaction(BaseFragment fragment, String tag) {
        this(fragment, 0, tag);
    }

    public AddFragmentTransaction(BaseFragment fragment, int containerForFragmentsId) {
        this(fragment, containerForFragmentsId, null);
    }

    public AddFragmentTransaction(BaseFragment fragment,
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

        if (mTag == null && mContainerForFragmentsId == 0) {
            throw new RuntimeException("please set tag or mContainerFragmentId");
        }

        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mContainerForFragmentsId == 0) transaction.add(mFragment, mTag);
        else if (mTag == null) transaction.add(mContainerForFragmentsId, mFragment);
        else transaction.add(mContainerForFragmentsId, mFragment, mTag);
        transaction.addToBackStack(mTag).commit();
    }



}

package katya.mercuriy94.com.katyamacc.presentation.module.main.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import katya.mercuriy94.com.katyamacc.R;
import katya.mercuriy94.com.katyamacc.presentation.common.annotations.Layout;
import katya.mercuriy94.com.katyamacc.presentation.common.router.ActivityRouterAdapter;
import katya.mercuriy94.com.katyamacc.presentation.module.app.MyApp;
import katya.mercuriy94.com.katyamacc.presentation.module.main.MainModuleContract;
import katya.mercuriy94.com.katyamacc.presentation.module.main.presenter.MainPresenter;
import katya.mercuriy94.com.katyamacc.presentation.module.main.router.MainRouter;

/**
 * Created by Nikita on 08.05.2017.
 */
@Layout(R.layout.act_main_layout)
public class MainActivityView extends MainModuleContract.AbstractMainView {

    @InjectPresenter
    MainModuleContract.AbstractMainPresenter mPresenter;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.act_main_title_chat_user_name)
    protected TextView mTvTitle;
    @BindView(R.id.act_main_title_chat_user_image)
    protected ImageView mIvTitle;


    @ProvidePresenter
    MainModuleContract.AbstractMainPresenter provideMainPresenter() {
        return new MainPresenter(MyApp.get(this));
    }

    //region Livecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    //endregion Livecycle

    //region BaseActivity

    @NonNull
    @Override
    public MainModuleContract.AbstractMainPresenter getPresenter() {
        return mPresenter;
    }

    @NonNull
    @Override
    public MainModuleContract.AbstractMainRouter createRouter(ActivityRouterAdapter activityRouteConductor) {
        return new MainRouter(activityRouteConductor);
    }

    @NonNull
    @Override
    protected ActivityRouterAdapter createRouterAdapter() {
        return new ActivityRouterAdapter(this, R.id.act_main_container);
    }

    //endregion BaseActivity

    //region IMainView

    @Override
    public void setInitialState() {
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void setTitleText(@StringRes int titleText) {
        mTvTitle.setText(titleText);
    }

    @Override
    public void setTitleText(String title) {
        mTvTitle.setText(title);
    }

    @Override
    public void setTitleImage(String url) {

    }

    @Override
    public void setVisibilityBackButton(boolean value) {
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(value);
    }


    //endregion


}

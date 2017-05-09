package katya.mercuriy94.com.katyamacc.presentation.module.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.HasPresenterSubcomponentBuilders;
import katya.mercuriy94.com.katyamacc.presentation.common.di.presenterbindings.PresenterComponentBuilder;
import katya.mercuriy94.com.katyamacc.presentation.common.presenter.BasePresenter;
import katya.mercuriy94.com.katyamacc.presentation.module.app.assembly.AppModule;
import katya.mercuriy94.com.katyamacc.presentation.module.app.assembly.DaggerIAppComponent;

/**
 * Created by Nikita on 08.05.2017.
 */

public class MyApp extends MultiDexApplication implements HasPresenterSubcomponentBuilders {


    @Inject
    Map<Class<? extends BasePresenter>, Provider<PresenterComponentBuilder>> mPresenterComponentBuilders;

    @Override
    public void onCreate() {
        super.onCreate();
        inject(this);
    }

    public static HasPresenterSubcomponentBuilders get(Context context) {
        return ((HasPresenterSubcomponentBuilders) context.getApplicationContext());
    }

    public void inject(Context context) {
        DaggerIAppComponent.builder()
                .appModule(new AppModule(context))
                .build()
                .inject(this);
    }


    @Override
    public PresenterComponentBuilder getPresenterComponentBuilder(Class<? extends BasePresenter> presenterClass) {
        return mPresenterComponentBuilders.get(presenterClass).get();
    }
}

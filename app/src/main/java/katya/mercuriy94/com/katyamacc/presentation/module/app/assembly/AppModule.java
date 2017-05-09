package katya.mercuriy94.com.katyamacc.presentation.module.app.assembly;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nikita on 08.05.2017.
 */
@Module
public class AppModule {

    private Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @NonNull
    @Provides
    Context provideContext(){
        return mContext;
    }

}

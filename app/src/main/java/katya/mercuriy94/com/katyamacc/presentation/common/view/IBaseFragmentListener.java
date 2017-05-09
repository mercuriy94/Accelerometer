package katya.mercuriy94.com.katyamacc.presentation.common.view;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Created by nikita on 11.03.2017.
 */

public interface IBaseFragmentListener {

    void changeTitle(@StringRes int title);

    void changeTitle(String title);

    void changeTitleImage(@Nullable String url);

    void changeVisibilityBackButton(boolean value);

}

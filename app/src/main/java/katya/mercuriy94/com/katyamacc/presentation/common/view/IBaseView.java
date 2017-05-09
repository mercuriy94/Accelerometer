package katya.mercuriy94.com.katyamacc.presentation.common.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by nikita on 25.12.2016.
 */

public interface IBaseView extends MvpView {


    void showPending(String message);

    void showPending(@StringRes int id);

    void hidePending();

    @StateStrategyType(value = SkipStrategy.class)
    void setInitialState();

    @StateStrategyType(value = SkipStrategy.class)
    void showLongToast(String message);

    @StateStrategyType(value = SkipStrategy.class)
    void showShortToast(String message);

    @StateStrategyType(value = SkipStrategy.class)
    void showLongToast(@StringRes int stringRes);

    @StateStrategyType(value = SkipStrategy.class)
    void showShortToast(@StringRes int stringRes);

    // region title

    @StateStrategyType(value = AddToEndStrategy.class)
    void setTitleText(@StringRes int titleText);

    @StateStrategyType(value = AddToEndStrategy.class)
    void setTitleText(String title);

    @StateStrategyType(value = AddToEndStrategy.class)
    void setTitleImage(String url);

    @StateStrategyType(value = AddToEndStrategy.class)
    void setVisibilityBackButton(boolean value);

    // endregion

}

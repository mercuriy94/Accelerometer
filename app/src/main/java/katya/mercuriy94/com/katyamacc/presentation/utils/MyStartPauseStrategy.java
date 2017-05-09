package katya.mercuriy94.com.katyamacc.presentation.utils;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.ViewCommand;
import com.arellomobile.mvp.viewstate.strategy.StateStrategy;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Nikita on 09.05.2017.
 */

public class MyStartPauseStrategy implements StateStrategy {

    public static final String TAG_START = "start";
    public static final String TAG_PAUSE = "pause";

    @Override
    public <View extends MvpView> void beforeApply(List<ViewCommand<View>> currentState, ViewCommand<View> incomingCommand) {

        Iterator<ViewCommand<View>> iterator = currentState.iterator();

        while (iterator.hasNext()) {
            ViewCommand<View> entry = iterator.next();
            if (entry.getTag().equals(TAG_START)) {
                iterator.remove();
                break;
            }
        }

        currentState.add(incomingCommand);
    }

    @Override
    public <View extends MvpView> void afterApply(List<ViewCommand<View>> currentState, ViewCommand<View> incomingCommand) {
        if (incomingCommand.getTag().equals(TAG_PAUSE)) {
            Iterator<ViewCommand<View>> iterator = currentState.iterator();

            while (iterator.hasNext()) {
                ViewCommand<View> entry = iterator.next();
                if (entry.getTag().equals(TAG_START) || entry.getTag().equals(TAG_PAUSE)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

}

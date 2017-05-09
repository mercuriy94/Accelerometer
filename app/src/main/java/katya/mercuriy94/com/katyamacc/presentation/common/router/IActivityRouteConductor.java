package katya.mercuriy94.com.katyamacc.presentation.common.router;

import android.support.v4.app.FragmentTransaction;

/**
 * Created by Nikita on 10.04.2017.
 */

public interface IActivityRouteConductor extends IBaseRouteConductor {

    void commmitTransaction(FragmentTransaction fragmentTransaction);

}

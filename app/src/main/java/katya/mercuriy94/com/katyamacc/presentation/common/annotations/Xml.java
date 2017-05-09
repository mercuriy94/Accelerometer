package katya.mercuriy94.com.katyamacc.presentation.common.annotations;

import android.support.annotation.XmlRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by nikita on 11.03.2017.
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Xml {

    @XmlRes int value();

}
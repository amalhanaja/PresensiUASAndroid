package d4tekkom.presensiuas.data;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by doy on 19/06/17.
 */

@Documented
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PrefQualifier {
}

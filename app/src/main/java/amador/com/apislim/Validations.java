package amador.com.apislim;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by amador on 9/02/17.
 */

public class Validations {

    public static boolean validateName(String name){

        return !TextUtils.isEmpty(name);
    }

    public static boolean validateEmail(String email){

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validateLink(String link){

        return Patterns.WEB_URL.matcher(link).matches();
    }
}

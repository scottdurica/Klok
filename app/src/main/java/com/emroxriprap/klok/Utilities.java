package com.emroxriprap.klok;

import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Scott Durica on 8/4/2015.
 */
public class Utilities {

    public static String dateToString (long longDate){
        String dateAsText = new SimpleDateFormat("MM-dd-yyyy").format(longDate);
        return dateAsText;
    }
    public static int dateToInt(long longDate){
        long l = longDate/1000;
        int returnDate = (int)l;
        return returnDate;
    }
    public static String formatToCurrencyString(float value){
        String returnString = "$" + String.format("%.2f",value);

        return returnString;
    }
    public static boolean validateEditTextFields(List<EditText> list){

        for (EditText editText: list){

            if (editText.getText().toString().trim().length()==0){

                return false;
            }
        }


        return true;

    }
}

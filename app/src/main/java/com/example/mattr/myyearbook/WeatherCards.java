package com.example.mattr.myyearbook;

import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Matt on 3/3/2018.
 */

public class WeatherCards {

    static TextView cityText;

    static TextView dayOneText;
    static TextView dayTwoText;
    static TextView dayFourText;
    static TextView dayThreeText;
    static TextView dayFiveText;


    static TextView dateOneText;
    static TextView dateTwoText;
    static TextView dateThreeText;
    static TextView dateFourText;
    static TextView dateFiveText;

    static TextView tempOneText;
    static TextView tempTwoText;
    static TextView tempThreeText;
    static TextView tempFourText;
    static TextView tempFiveText;

    static TextView decriptionOneText;
    static TextView decriptionTwoText;
    static TextView decriptionThreeText;
    static TextView decriptionFourText;
    static TextView decriptionFiveText;
    static ProgressBar progressBar;

    static TextView[] dayArray = {dayOneText, dayTwoText, dayThreeText, dayFourText, dayFiveText};
    static TextView[] date = {dateOneText, dateTwoText, dateThreeText, dateFourText, dateFiveText};
    static TextView[] temp = {tempOneText, tempTwoText, tempThreeText, tempFourText, tempFiveText};
    static TextView[] description = {decriptionOneText, decriptionTwoText, decriptionThreeText, decriptionFourText, decriptionFiveText};
}

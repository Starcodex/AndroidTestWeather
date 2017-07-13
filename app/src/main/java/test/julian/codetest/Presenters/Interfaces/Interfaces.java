package test.julian.codetest.Presenters.Interfaces;

import android.os.Bundle;
import android.view.View;

/**
 * Created by JulianStack on 12/07/2017.
 */

public class Interfaces {

    // Generic Presenter
    public interface IPresenter {
        void processView(View ly, Bundle bundle);

    }

    //
    public interface IView {
        void finishView();
    }
    // Update UI messages
    public interface UIMSG {
        void updateMessageUI(String message);
    }


}

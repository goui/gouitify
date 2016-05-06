package fr.goui.gouitify;

import android.content.Context;

/**
 *
 */
public interface IView {

    Context getContext();

    void showError(String message);
}

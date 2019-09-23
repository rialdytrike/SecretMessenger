package com.trikersdev.secret.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.trikersdev.secret.R;
import com.trikersdev.secret.constants.Constants;

public class PopularSettingsDialog extends DialogFragment implements Constants {

    /** Declaring the interface, to invoke a callback function in the implementing activity class */
    AlertPositiveListener alertPositiveListener;

    private int category;

    /** An interface to be implemented in the hosting activity for "OK" button click listener */
    public interface AlertPositiveListener {

        public void onChangePopularCategory(int position);
    }

    /** This is a callback method executed when this fragment is attached to an activity.
     *  This function ensures that, the hosting activity implements the interface AlertPositiveListener
     * */
    public void onAttach(android.app.Activity activity) {

        super.onAttach(activity);

        try {

            alertPositiveListener = (AlertPositiveListener) activity;

        } catch(ClassCastException e){

            // The hosting activity does not implemented the interface AlertPositiveListener
            throw new ClassCastException(activity.toString() + " must implement AlertPositiveListener");
        }
    }

    /** This is the OK button listener for the alert dialog,
     *  which in turn invokes the method onPositiveClick(position)
     *  of the hosting activity which is supposed to implement it
     */
    OnClickListener positiveListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            AlertDialog alert = (AlertDialog)dialog;

            int position = alert.getListView().getCheckedItemPosition();

            alertPositiveListener.onChangePopularCategory(position);
        }
    };

    /** This is a callback method which will be executed
     *  on creating this fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String[] popular_categories = new String[] {

                getText(R.string.dialog_popular_0).toString(),
                getText(R.string.dialog_popular_1).toString(),
                getText(R.string.dialog_popular_2).toString(),
                getText(R.string.dialog_popular_3).toString(),

        };

        /** Getting the arguments passed to this fragment */
        Bundle bundle = getArguments();

        category = bundle.getInt("category");

        /** Creating a builder for the alert dialog window */
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        /** Setting a title for the window */
        b.setTitle(getText(R.string.title_dialog_popular));

        /** Setting items to the alert dialog */
        b.setSingleChoiceItems(popular_categories, setChecked(category), null);

        /** Setting a positive button and its listener */
        b.setPositiveButton(getText(R.string.action_ok), positiveListener);

        /** Setting a positive button and its listener */
        b.setNegativeButton(getText(R.string.action_cancel), null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog d = b.create();

        /** Return the alert dialog window */
        return d;
    }

    private int setChecked(int category) {

        int result = 0;

        switch (category) {

            case POPULAR_CONST_1: {

                result = 0;

                break;
            }

            case POPULAR_CONST_2: {

                result = 1;

                break;
            }

            case POPULAR_CONST_3: {

                result = 2;

                break;
            }

            case POPULAR_CONST_4: {

                result = 3;

                break;
            }

            default: {

                result = 0;

                break;
            }
        }

        return  result;
    }
}
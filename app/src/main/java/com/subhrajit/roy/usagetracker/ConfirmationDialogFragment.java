package com.subhrajit.roy.usagetracker;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ConfirmationDialogFragment extends DialogFragment {
    private ConfirmationDialogHandler handler;

    public ConfirmationDialogFragment(ConfirmationDialogHandler handler) {
        this.handler = handler;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.reset_confirmation))
                .setNegativeButton(getString(R.string.confirm), handler )
                .setPositiveButton(getString(R.string.cancel), handler )
                .create();
    }

    public static String TAG = "ConfirmationFragmentDialog";
}

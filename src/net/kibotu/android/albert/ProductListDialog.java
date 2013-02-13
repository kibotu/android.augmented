package net.kibotu.android.albert;

import android.app.Dialog;
import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 13.02.13
 * Time: 03:36
 * To change this template use File | Settings | File Templates.
 */
public class ProductListDialog extends Dialog{
    public ProductListDialog(Context context) {
        super(context);
    }

    public ProductListDialog(Context context, int theme) {
        super(context, theme);
    }

    protected ProductListDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}

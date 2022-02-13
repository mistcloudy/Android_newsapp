package com.News.ns2.adapters;

import android.view.MenuItem;
import android.view.View;

public interface ContextMenuHandler extends View.OnCreateContextMenuListener {

    boolean onContextItemSelected(MenuItem item);
}

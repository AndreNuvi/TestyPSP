package pl.strazpozarna.inspekcja.testypsp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.MenuItem;

/**
 * Created by Andre on 2017-03-12.
 */
class Util {




        static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
            Drawable normalDrawable = item.getIcon();
            Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
            DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));
            item.setIcon(wrapDrawable);
        }


}


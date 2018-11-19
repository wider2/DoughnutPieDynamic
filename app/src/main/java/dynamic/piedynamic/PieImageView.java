package dynamic.piedynamic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;

/**
 * Created by Aleksei Jegorov on 19 Nov 2018.
 */

public class PieImageView extends android.support.v7.widget.AppCompatImageView {

    RectF mBoundsF;
    RectF mInnerBoundsF;
    final float START_ANGLE = 0.f;
    float mDrawTo;
    String text;
    private int color;
    private int fillColor;
    private int thickness;
    private boolean innerPart;
    private boolean doughnut;
    private boolean threeColors;
    private int textColor = 0x000000;
    private int textSize;
    private int globalLevel;
    private boolean fillGradient;
    private int width;
    private int height;

    public PieImageView(Context context) {
        super(context);
    }

    public PieImageView setFillColor(int fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public PieImageView setFillGradient(boolean fillGradient) {
        this.fillGradient = fillGradient;
        return this;
    }

    public PieImageView setColor(int color) {
        this.color = color;
        return this;
    }

    public PieImageView setThickness(int thickness) {
        this.thickness = thickness;
        return this;
    }

    public PieImageView setInnerPart(boolean innerPart) {
        this.innerPart = innerPart;
        return this;
    }

    public PieImageView setDoughnut(boolean doughnut) {
        this.doughnut = doughnut;
        return this;
    }

    public PieImageView setThreeColors(boolean threeColors) {
        this.threeColors = threeColors;
        return this;
    }

    public PieImageView setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public PieImageView setText(String text) {
        this.text = text;
        return this;
    }

    public PieImageView setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public PieImageView setLevel(int level) {
        this.globalLevel = level;
        mDrawTo = START_ANGLE + ((float) 360 * level) / 100f;
        return this;
    }

    public PieImageView setWidth(int width) {
        this.width = width;
        return this;
    }

    public PieImageView setHeight(int height) {
        this.height = height;
        return this;
    }

    public Bitmap setBitmap() {
        // Create a mutable bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        //Immutable bitmap passed to Canvas constructor, so we change it here
        bitmap = bitmap.copy(bitmap.getConfig(), true);

        bitmap = getRoundBitmap(bitmap);

        return bitmap;
    }

    public Bitmap getRoundBitmap(Bitmap bitmap) {
        try {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setAntiAlias(true); //smooths out the edges of what is being drawn
            paint.setFilterBitmap(true);
            paint.setDither(true);

            mBoundsF = mInnerBoundsF = new RectF(0, 0, width, height);
            final int halfBorder = (int) (paint.getStrokeWidth() / 2f + 0.5f);
            //final int halfBorder = (int) (paint.getStrokeWidth());
            mInnerBoundsF.inset(halfBorder, halfBorder);


            Canvas canvas = new Canvas(bitmap);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mBoundsF.centerX(), height / 2, (mBoundsF.width() / 2), paint);
            canvas.rotate(-90f, width / 2, height / 2);

            if (threeColors) {
                if (globalLevel <= 33) {
                    color = Color.parseColor("#00c000");
                } else if (globalLevel > 33 && globalLevel <= 66) {
                    color = Color.parseColor("#FFFF00");
                } else {
                    color = Color.parseColor("#FF0000");
                }
            }
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            //canvas.drawARGB(0, 0, 0, 0);
            paint.setSubpixelText(false);



            if (innerPart) {
                canvas.drawArc(mInnerBoundsF, mDrawTo, 360 - mDrawTo, true, paint);
            } else {
                canvas.drawArc(mInnerBoundsF, START_ANGLE, mDrawTo, true, paint);
            }


            if (doughnut) {
                if (fillGradient) {
                    //Shader used to draw a bitmap as a texture. The bitmap can be repeated or mirrored by setting the tiling mode.
                    paint.setShader(new LinearGradient(0, 0, width, height, fillColor, Color.WHITE, Shader.TileMode.MIRROR));
                } else {
                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                }
                canvas.drawCircle(mBoundsF.centerX(), height / 2, (mBoundsF.width() / 2) - thickness, paint);
            }


            canvas.rotate(90f, width / 2, height / 2);
            paint = new Paint(Paint.ANTI_ALIAS_FLAG); // to show text above the bg gradient

            float message_width = paint.measureText(text);
            Paint.FontMetrics fm = new Paint.FontMetrics();
            paint.getFontMetrics(fm);

            paint.setColor(textColor);
            paint.setTextSize(textSize);
            canvas.drawText(text, (width / 2) - (message_width / 1.6f), (height / 2) + 5, paint); //1.2f is too much at left

            return bitmap;
        } catch (Exception ex) {
            Log.wtf("getRoundBitmap", ex.getMessage());
        }
        return null;
    }

}
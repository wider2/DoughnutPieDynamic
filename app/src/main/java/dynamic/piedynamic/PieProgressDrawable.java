package dynamic.piedynamic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class PieProgressDrawable extends Drawable {

    Paint mPaint;
    RectF mBoundsF;
    RectF mInnerBoundsF;
    final float START_ANGLE = 0.f;
    float mDrawTo;
    String text;
    private int color;
    private int fillColor;
    private int thickness;
    private float borderWidth;
    private boolean innerPart;
    private boolean doughnut;
    private boolean threeColors;
    private int textColor = 0x000000;
    private int textSize;
    private int globalLevel;
    private boolean fillGradient;

    public PieProgressDrawable() {
        super();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setBorderWidth(float widthDp, DisplayMetrics dm) {
        borderWidth = widthDp * dm.density;
        //mPaint.setStrokeWidth(borderWidth);
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public void setFillGradient(boolean fillGradient) {
        this.fillGradient = fillGradient;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setInnerPart(boolean innerPart) {
        this.innerPart = innerPart;
    }

    public void setDoughnut(boolean doughnut) {
        this.doughnut = doughnut;
    }

    public void setThreeColors(boolean threeColors) {
        this.threeColors = threeColors;
    }


    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    @Override
    public void draw(Canvas canvas) {

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mBoundsF.centerX(), mBoundsF.height() / 2, (mBoundsF.width() / 2), mPaint);
        canvas.rotate(-90f, getBounds().centerX(), getBounds().centerY());

        if (threeColors) {
            if (globalLevel <= 33) {
                color = Color.parseColor("#00c000");
            } else if (globalLevel > 33 && globalLevel <= 66) {
                color = Color.parseColor("#FFFF00");
            } else {
                color = Color.parseColor("#FF0000");
            }
        }
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);


        if (innerPart) {
            canvas.drawArc(mInnerBoundsF, mDrawTo, 360 - mDrawTo, true, mPaint);
        } else {
            canvas.drawArc(mInnerBoundsF, START_ANGLE, mDrawTo, true, mPaint);
        }


        if (doughnut) {
            if (fillGradient) {
                mPaint.setShader(new LinearGradient(0, 0, getBounds().width(), getBounds().height(), fillColor, Color.WHITE, Shader.TileMode.MIRROR));
            } else {
                mPaint.setColor(Color.WHITE);
                mPaint.setStyle(Paint.Style.FILL);
            }
            canvas.drawCircle(mBoundsF.centerX(), getBounds().centerY(), (mBoundsF.width() / 2) - thickness, mPaint);
        }

        if (!TextUtils.isEmpty(text)) {
            canvas.rotate(90f, getBounds().centerX(), getBounds().centerY());

            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            float message_width = mPaint.measureText(text);
            Paint.FontMetrics fm = new Paint.FontMetrics();
            mPaint.getFontMetrics(fm);

            mPaint.setColor(textColor);
            mPaint.setTextSize(textSize);
            canvas.drawText(text, mBoundsF.centerX() - message_width / 1.1f, getBounds().centerY() + 10, mPaint);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mBoundsF = mInnerBoundsF = new RectF(bounds);
        final int halfBorder = (int) (mPaint.getStrokeWidth() / 2f + 0.5f);
        mInnerBoundsF.inset(halfBorder, halfBorder);
    }

    @Override
    protected boolean onLevelChange(int level) {
        this.globalLevel = level;
        final float drawTo = START_ANGLE + ((float) 360 * level) / 100f;
        boolean update = drawTo != mDrawTo;
        mDrawTo = drawTo;
        return update;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return mPaint.getAlpha();
    }
}
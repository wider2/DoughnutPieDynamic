package dynamic.piedynamic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by Aleksei Jegorov on 19 Nov 2018.
 */

public class MainActivity extends AppCompatActivity {

    ImageView timeProgress, ivOutput2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeProgress = (ImageView) findViewById(R.id.iv_output);
        ivOutput2 = (ImageView) findViewById(R.id.iv_output2);

        PieProgressDrawable pieProgressDrawable = new PieProgressDrawable();
        pieProgressDrawable.setDoughnut(true);
        pieProgressDrawable.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        pieProgressDrawable.setFillColor(ContextCompat.getColor(this, R.color.gray_light));
        pieProgressDrawable.setFillGradient(true);
        pieProgressDrawable.setInnerPart(true);
        pieProgressDrawable.setThickness(10);
        //pieProgressDrawable.setBorderWidth(10, new DisplayMetrics());
        pieProgressDrawable.setText("00:09");
        pieProgressDrawable.setTextColor(ContextCompat.getColor(this, R.color.red));
        pieProgressDrawable.setTextSize(21);
        pieProgressDrawable.setThreeColors(false);

        timeProgress.setImageDrawable(pieProgressDrawable);
        pieProgressDrawable.setLevel(20);


        PieImageView pieImageView = new PieImageView(getBaseContext());
        Bitmap bitmap = pieImageView
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setFillColor(ContextCompat.getColor(this, R.color.gray_mid))
                .setFillGradient(true)
                .setDoughnut(true)
                .setInnerPart(true)
                .setThickness(5)
                .setText("00:09")
                .setTextColor(ContextCompat.getColor(this, R.color.red))
                .setTextSize(15)
                .setLevel(70)
                .setThreeColors(true)
                .setWidth(55)
                .setHeight(55)
                .setBitmap();
        ivOutput2.setImageBitmap(bitmap);
    }
}

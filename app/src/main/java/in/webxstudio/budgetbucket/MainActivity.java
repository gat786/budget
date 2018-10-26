package in.webxstudio.budgetbucket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SqliteDataSaver saver;
    Button generator,getter;
    TextView generatedView,totalView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saver=new SqliteDataSaver(getApplicationContext());

        Date todaysDate=Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String date = sdf.format(todaysDate);
        generator=findViewById(R.id.generate);
        getter=findViewById(R.id.totalGetter);

        generatedView=findViewById(R.id.generated);
        totalView=findViewById(R.id.total);

        generator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: date is "+date);
                int i=0;
                StringBuilder rand= new StringBuilder();
                while (i<5){
                    int random=randomGetter();
                    saver.addData(getApplicationContext(),random,date,"nothing","FOOD");
                    rand.append(random);
                    i=i+1;
                }
                generatedView.setText(rand.toString());
            }
        });

        getter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = saver.getMonthlyTotal(getApplicationContext(),10,2018);
                totalView.setText(""+total);
            }
        });

    }

    int randomGetter(){
        Random random = new Random();
        return random.nextInt(100)+1;
    }
}

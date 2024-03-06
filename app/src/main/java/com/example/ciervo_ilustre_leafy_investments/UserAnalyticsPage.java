package com.example.ciervo_ilustre_leafy_investments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.applandeo.materialcalendarview.EventDay;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import com.anychart.charts.Pie;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserAnalyticsPage extends AppCompatActivity {

    Toolbar toolbar;


    String receivedData;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference clientRef = db.collection("clients");
    DocumentReference documentReference;
    TextView totalView;
    TextView amountView;
    ArrayList<DueDate>  data = new ArrayList<>();
    AnyChartView UpcomingChartView;
    AnyChartView AmountChartView;
    List<DataEntry> chartdata1 = new ArrayList<>();
    List<DataEntry> chartdata2 = new ArrayList<>();

    String state = "total";
    Button changeButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_analytics_page);

        AmountChartView = findViewById(R.id.upComingChart);

        totalView = findViewById(R.id.totalView);



        this.receivedData = UserDashboard.receivedData;
        documentReference = clientRef.document(this.receivedData);

        toolbar = findViewById(R.id.calendar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

       loadData();

    }

    public void loadChart()
    {

        Pie Upcomingpie = AnyChart.pie();
        Pie Amountpie = AnyChart.pie();
        APIlib.getInstance().setActiveAnyChartView(AmountChartView);

        int Utilities = 0;
        int Food = 0;
        int Personal= 0;
        int Education= 0;
        int Loans= 0;
        int Total = 0;

        int AUtilities = 0;
        int AFood = 0;
        int APersonal= 0;
        int AEducation= 0;
        int ALoans= 0;
        int ATotal = 0;


        for (int i = 0; i < data.size() ; i++) {

            if(data.get(i).getCategory().equals("Utilities"))
            {
                Utilities+=1;
                AUtilities+=Integer.parseInt(data.get(i).getAmount());
            }
            else if(data.get(i).getCategory().equals("Food"))
            {
                Food+=1;
                AFood+=Integer.parseInt(data.get(i).getAmount());
            }
            else if(data.get(i).getCategory().equals("Personal"))
            {
                Personal+=1;
                APersonal+=Integer.parseInt(data.get(i).getAmount());
            }
            else if(data.get(i).getCategory().equals("Education"))
            {
                Education+=1;
                AEducation+=Integer.parseInt(data.get(i).getAmount());
            }
            else if(data.get(i).getCategory().equals("Loans"))
            {
                Loans+=1;
                ALoans+=Integer.parseInt(data.get(i).getAmount());
            }

            Total = Total + 1;
            ATotal = ATotal + Integer.parseInt(data.get(i).getAmount());
        }
//            chartdata1.add(new ValueDataEntry("Utilities", Utilities));
//            chartdata1.add(new ValueDataEntry("Food", Food));
//            chartdata1.add(new ValueDataEntry("Personal", Personal));
//            chartdata1.add(new ValueDataEntry("Education", Education));
//            chartdata1.add(new ValueDataEntry("Loans", Loans));

            chartdata2.add(new ValueDataEntry("Utilities", AUtilities));
            chartdata2.add(new ValueDataEntry("Food", AFood));
            chartdata2.add(new ValueDataEntry("Personal", APersonal));
            chartdata2.add(new ValueDataEntry("Education", AEducation));
            chartdata2.add(new ValueDataEntry("Loans", ALoans));



            Amountpie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
            Amountpie.data(chartdata2);
            Amountpie.title("Upcoming Costs");
            Amountpie.labels().position("outside");



                AmountChartView.setChart(Amountpie);
                APIlib.getInstance().setActiveAnyChartView(AmountChartView);
                AmountChartView.bringToFront();
                totalView.setText("Total Costs: "+ String.valueOf(ATotal));





    }

    public void loadData()
    {
        documentReference.collection("dueDates").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    String date = documentSnapshot.getString("Due Date").trim();
                    String name = documentSnapshot.getString("Bill Name").trim();
                    String category = documentSnapshot.getString("Category").trim();
                    String amount = documentSnapshot.getString("Amount").trim();

                    data.add(new DueDate(name, amount, category, date, documentSnapshot.getId()));

                }

                loadChart();

            }

        });
    }

}
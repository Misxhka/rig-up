package rig.up;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rig.up.Model.SavedbuildII;
import rig.up.SubMenu.HistoryActivity;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class SavedListActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView viewMobo, viewCpu, viewRam, viewGpu, viewStor, viewCase, viewPsu, viewTprice, viewTwatt;
    private Button btnExp;
    private Bitmap bitmap;
    private ConstraintLayout llpdf;

    private SavedbuildII savedbuildII;

    //INit Firebase
    FirebaseDatabase database;
    DatabaseReference savedBuild;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);

        savedbuildII = (SavedbuildII) getIntent().getSerializableExtra("Savedbuild");

        database = FirebaseDatabase.getInstance();
        savedBuild = database.getReference("Savedbuild");

        viewMobo = findViewById(R.id.viewMobo);
        viewCpu = findViewById(R.id.viewCpu);
        viewRam = findViewById(R.id.viewRam);
        viewGpu = findViewById(R.id.viewGpu);
        viewStor = findViewById(R.id.viewStor);
        viewCase = findViewById(R.id.viewCase);
        viewPsu = findViewById(R.id.viewPsu);
        viewTprice = findViewById(R.id.viewTprice);
        viewTwatt = findViewById(R.id.viewTwatt);

        viewMobo.setText(savedbuildII.getMotherboard());
        viewCpu.setText(savedbuildII.getProcessor());
        viewRam.setText(savedbuildII.getMemory());
        viewGpu.setText(savedbuildII.getGraphic_Card());
        viewStor.setText(savedbuildII.getStorage());
        viewCase.setText(savedbuildII.getCasing());
        viewPsu.setText(savedbuildII.getPower_Supply());
        viewTprice.setText(savedbuildII.getTotal_Price());
        viewTwatt.setText(savedbuildII.getTotal_Wattage());

        btnExp = findViewById(R.id.btnPrtSc);
        llpdf = findViewById(R.id.llpdf);

        btnExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size",""+llpdf.getWidth()+"  "+llpdf.getWidth());
                bitmap = loadBitmapFromView(llpdf, llpdf.getWidth(), llpdf.getHeight());
                createPdf();
            }
        });

    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHeight = (int) height, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);
        paint.setColor(0xFFFFFFFF);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHeight, true);

        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf =Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/" + savedbuildII.getSavedName() + ".pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something's wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }


        // close the document
        document.close();
        Toast.makeText(this, "Saved as PDF", Toast.LENGTH_SHORT).show();



        //openGeneratedPDF();
        addNotification();
    }

    private void addNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle("Rig Up!")
                .setContentText("Successfully export! Tap to view");

        // Creates the intent needed to show the notification
        Intent folderIntent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS) + "/");
        folderIntent.setDataAndType(uri, "*/*");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, folderIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        //startActivity(Intent.createChooser(folderIntent, "Open"));
    }

    /*private void openGeneratedPDF(){
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "/saved.pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }

        }} */
    @Override
    public void onClick(View v) {

    }


}

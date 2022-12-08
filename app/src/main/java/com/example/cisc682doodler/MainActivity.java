package com.example.cisc682doodler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    public static Path drawPath = new Path();
    public static Paint drawPaint = new Paint();
    public static int paintColor = 0xFF000000;
    //public static Paint drawColor = new Paint();
    public static Canvas canvas;
    DoodleView doodleView;
    //public static boolean erase = false;
    public static int brushSize = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doodleView = findViewById(R.id.doodleView);
        ImageButton brush = findViewById(R.id.brushButton);
        ImageButton colorWheel = findViewById(R.id.colorButton);
        ImageButton eraser = findViewById(R.id.eraserButton);
        ImageButton opacity = findViewById(R.id.opacityButton);

        eraser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.eraserButton) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    //drawPath.reset();

                }
            }
        });

        brush.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.brushButton) {
                    setBrushSize();

                }
            }
        });

        opacity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.opacityButton) {
                    setOpacity();

                }
            }
        });

        colorWheel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.colorButton) {
                    setColor();
                   /* Intent intent = new Intent(MainActivity.this,PopColor.class);
                    startActivity(intent);*/

                }
            }
        });

    }



    /*public void colorWheel(View view) {

        if(view.getId()==R.id.colorButton){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            view = LayoutInflater.from(this).inflate(R.layout.layout_dialog,null,false);

        }

    }*/

    public void setBrushSize(){

       AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
       View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null,false);

       TextView selectedBrush = view.findViewById(R.id.txtBrush);
       TextView statusSize = view.findViewById(R.id.statusBrushSize);
       ImageView ivTool = view.findViewById(R.id.iv_tool);
       SeekBar seekBar = view.findViewById(R.id.seekbarSize);
       seekBar.setMax(99);


       selectedBrush.setText("Brush Size :");
       ivTool.setImageResource(R.drawable.brush);
       statusSize.setText("Selected Size :"+brushSize);

       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    brushSize= progress +1;
                    statusSize.setText("Selected Size :"+brushSize);
                    doodleView.brushSize(brushSize);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        popDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       });
        popDialog.setView(view);
        popDialog.show();

    }


    public void setOpacity(){
        final Dialog opacityDialog = new Dialog(this);
        opacityDialog.setTitle("Opacity Level:");
        opacityDialog.setContentView(R.layout.opacity_control);

        TextView txtOpacity= (TextView) opacityDialog.findViewById(R.id.opq_txt);
        SeekBar seekBarOpacity = (SeekBar) opacityDialog.findViewById(R.id.opacity_seek);
        Button opacityBtn = (Button) opacityDialog.findViewById(R.id.opq_ok);


        seekBarOpacity.setMax(100);
        int currLevel = doodleView.getPaintAlpha();

        txtOpacity.setText(currLevel+"%");
        seekBarOpacity.setProgress(currLevel);


        seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtOpacity.setText(Integer.toString(progress)+"%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        opacityBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                doodleView.setPaintAlpha(seekBarOpacity.getProgress());
                opacityDialog.dismiss();
            }
        });
        opacityDialog.show();

    }


    public void setColor(){
       final Dialog colorDialog = new Dialog(this);
        colorDialog.setTitle("Paint Color:");
        colorDialog.setContentView(R.layout.color_wheel);
/*

        AlertDialog.Builder colorDialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.color_wheel, null,false);
*/


        ImageView blackCol = (ImageView) colorDialog.findViewById(R.id.blackCol);
        ImageView whiteCol = (ImageView) colorDialog.findViewById(R.id.whiteCol);
        ImageView blueCol = (ImageView) colorDialog.findViewById(R.id.blueCol);
        ImageView purpleCol = (ImageView) colorDialog.findViewById(R.id.purpleCol);
      /*  Button greenBtn = (Button) view.findViewById(R.id.greenBtn);
        Button yellowBtn = (Button) view.findViewById(R.id.yellowBtn);*/


        blackCol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.blackCol) {
                    paintColor = Color.parseColor("000000");
                    doodleView.brushColor(paintColor);
                    colorDialog.dismiss();
                }
            }
        });

        whiteCol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.whiteCol) {
                    paintColor = Color.parseColor("WHITE");
                    doodleView.brushColor(paintColor);
                    colorDialog.dismiss();
                }
            }
        });

        blackCol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.blackCol) {
                    paintColor = Color.parseColor("#3700B3");
                    doodleView.brushColor(paintColor);
                    colorDialog.dismiss();
                }
            }
        });

        purpleCol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.purpleCol) {
                    paintColor = Color.parseColor("#FFBB86FC");
                    doodleView.brushColor(paintColor);
                    colorDialog.dismiss();

                }
            }
        });

       /* blueBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.blueBtn) {
                    paintColor = Color.parseColor("BLUE");
                    doodleView.brushColor(paintColor);

                }
            }
        });

        greenBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.greenBtn) {
                    paintColor = Color.parseColor("GREEN");
                    doodleView.brushColor(paintColor);

                }
            }
        });*/

    /*    yellowBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.yellowBtn) {
                    paintColor = Color.parseColor("YELLOW");
                    doodleView.brushColor(paintColor);

                }
            }
        });*/


        /*colorDialog.setItems(new CharSequence[]{"blackBtn", "whiteBtn", "redBtn", "blueBtn", "greenBtn", "yellowBtn"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which)
                {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:
                        paintColor = Color.parseColor("RED");
                        doodleView.brushColor(paintColor);
                        break;
                    case 3:
                        paintColor = Color.parseColor("BLUE");
                        doodleView.brushColor(paintColor);
                        break;
                    case 4:
                        paintColor = Color.parseColor("GREEN");
                        doodleView.brushColor(paintColor);
                        break;
                    case 5:

                        break;
                }
            }


        });*/
        colorDialog.show();

        }


        @Override
    public void onClick(View view) {

    }
}
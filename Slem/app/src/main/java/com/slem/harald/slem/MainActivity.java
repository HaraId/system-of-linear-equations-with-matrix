package com.slem.harald.slem;

import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private static double parseDouble(EditText et){
        return Double.parseDouble(et.getText().toString());
    }

    EditText a11;
    EditText a12;
    EditText a13;
    EditText a21;
    EditText a22;
    EditText a23;
    EditText a31;
    EditText a32;
    EditText a33;

    EditText x1;
    EditText x2;
    EditText x3;

    EditText outputPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = (Button)findViewById(R.id.button);

        a11 = (EditText) findViewById(R.id.w_el11);
        a12 = (EditText) findViewById(R.id.w_el12);
        a13 = (EditText) findViewById(R.id.w_el13);
        a21 = (EditText) findViewById(R.id.w_el21);
        a22 = (EditText) findViewById(R.id.w_el22);
        a23 = (EditText) findViewById(R.id.w_el23);
        a31 = (EditText) findViewById(R.id.w_el31);
        a32 = (EditText) findViewById(R.id.w_el32);
        a33 = (EditText) findViewById(R.id.w_el33);

        x1 =  (EditText) findViewById(R.id.w_v1);
        x2 =  (EditText) findViewById(R.id.w_v2);
        x3 =  (EditText) findViewById(R.id.w_v3);

        outputPlace = (EditText) findViewById(R.id.outPlain);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Matrix mtr = new Matrix(3) {{
                    setLine(1, parseDouble(a11), parseDouble(a12), parseDouble(a13));
                    setLine(2, parseDouble(a21), parseDouble(a22), parseDouble(a23));
                    setLine(3, parseDouble(a31), parseDouble(a32), parseDouble(a33));
                }};
                StringBuilder result = new StringBuilder();

                result.append(mtr);

                final double detA = Matrix.determinant(mtr);
                result.append("detA = "  + detA + "\n");

                result.append("A11 = " + Matrix.cofactor(mtr, 1, 1) + "\n");
                result.append("A12 = " + Matrix.cofactor(mtr, 1, 2) + "\n");
                result.append("A13 = " + Matrix.cofactor(mtr, 1, 3) + "\n");
                result.append("A21 = " + Matrix.cofactor(mtr, 2, 1) + "\n");
                result.append("A22 = " + Matrix.cofactor(mtr, 2, 2) + "\n");
                result.append("A23 = " + Matrix.cofactor(mtr, 2, 3) + "\n");
                result.append("A31 = " + Matrix.cofactor(mtr, 3, 1) + "\n");
                result.append("A32 = " + Matrix.cofactor(mtr, 3, 2) + "\n");
                result.append("A33 = " + Matrix.cofactor(mtr, 3, 3) + "\n\n");


                if(detA != 0){
                    result.append("A-1: \n");
                    Matrix invMtr = Matrix.getInvertibleMatrix(mtr);
                    result.append(invMtr);
                    result.append("\n");

                    Matrix param = new Matrix(3, 1){
                        {
                            setLine(1, parseDouble(x1));
                            setLine(2, parseDouble(x2));
                            setLine(3, parseDouble(x3));
                        }
                    };

                    Matrix ans = invMtr.multiply(param);

                    result.append("ANS:\n");
                    result.append(ans);
                }


                outputPlace.setText(result.toString());
            }
        });
    }
}

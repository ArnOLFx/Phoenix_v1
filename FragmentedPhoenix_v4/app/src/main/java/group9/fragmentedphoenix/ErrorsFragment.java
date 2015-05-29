package group9.fragmentedphoenix;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;

    // Class for the errors fragment that will display individual errors on the previous journey.
    // No longer in use as the information wasn't detailed enough to be of real use to the user.
public class ErrorsFragment extends Fragment {
TextView r1c1, r1c2, r1c3, r2c1, r2c2, r2c3, r3c1, r3c2, r3c3, r4c1, r4c2, r4c3,
         r5c1, r5c2, r5c3, r6c1, r6c2, r6c3, r7c1, r7c2, r7c3, r8c1, r8c2, r8c3,
         r9c1, r9c2, r9c3, r10c1, r10c2, r10c3, r11c1, r11c2, r11c3, r12c1, r12c2, r12c3,
         r13c1, r13c2, r13c3, r14c1, r14c2, r14c3,  r15c1, r15c2, r15c3, r16c1, r16c2, r16c3, r17c1,
         r17c2, r17c3, r18c1, r18c2, r18c3, r19c1, r19c2, r19c3, r20c1, r20c2, r20c3,
         r21c1, r21c2, r21c3, r22c1, r22c2, r22c3, r23c1, r23c2, r23c3, r24c1, r24c2,
         r24c3, r25c1, r25c2, r25c3, r26c1, r26c2, r26c3, r27c1, r27c2, r27c3, r28c1,
         r28c2, r28c3, r29c1, r29c2, r29c3, r30c1, r30c2, r30c3;
        // The textviews that will hold the data from the queried database.

    public ErrorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View errorsView = inflater.inflate(R.layout.fragment_errors, container, false);
        r1c1 = (TextView) errorsView.findViewById(R.id.r1c1);
        r1c2 = (TextView) errorsView.findViewById(R.id.r1c2);
        r1c3 = (TextView) errorsView.findViewById(R.id.r1c3);
        r2c1 = (TextView) errorsView.findViewById(R.id.r2c1);
        r2c2 = (TextView) errorsView.findViewById(R.id.r2c2);
        r2c3 = (TextView) errorsView.findViewById(R.id.r2c3);
        r3c1 = (TextView) errorsView.findViewById(R.id.r3c1);
        r3c2 = (TextView) errorsView.findViewById(R.id.r3c2);
        r3c3 = (TextView) errorsView.findViewById(R.id.r3c3);
        r4c1 = (TextView) errorsView.findViewById(R.id.r4c1);
        r4c2 = (TextView) errorsView.findViewById(R.id.r4c2);
        r4c3 = (TextView) errorsView.findViewById(R.id.r4c3);
        r5c1 = (TextView) errorsView.findViewById(R.id.r5c1);
        r5c2 = (TextView) errorsView.findViewById(R.id.r5c2);
        r5c3 = (TextView) errorsView.findViewById(R.id.r5c3);
        r6c1 = (TextView) errorsView.findViewById(R.id.r6c1);
        r6c2 = (TextView) errorsView.findViewById(R.id.r6c2);
        r6c3 = (TextView) errorsView.findViewById(R.id.r6c3);
        r7c1 = (TextView) errorsView.findViewById(R.id.r7c1);
        r7c2 = (TextView) errorsView.findViewById(R.id.r7c2);
        r7c3 = (TextView) errorsView.findViewById(R.id.r7c3);
        r8c1 = (TextView) errorsView.findViewById(R.id.r8c1);
        r8c2 = (TextView) errorsView.findViewById(R.id.r8c2);
        r8c3 = (TextView) errorsView.findViewById(R.id.r8c3);
        r9c1 = (TextView) errorsView.findViewById(R.id.r9c1);
        r9c2 = (TextView) errorsView.findViewById(R.id.r9c2);
        r9c3 = (TextView) errorsView.findViewById(R.id.r9c3);
        r10c1 = (TextView) errorsView.findViewById(R.id.r10c1);
        r10c2 = (TextView) errorsView.findViewById(R.id.r10c2);
        r10c3 = (TextView) errorsView.findViewById(R.id.r10c3);
        r11c1 = (TextView) errorsView.findViewById(R.id.r11c1);
        r11c2 = (TextView) errorsView.findViewById(R.id.r11c2);
        r11c3 = (TextView) errorsView.findViewById(R.id.r11c3);
        r12c1 = (TextView) errorsView.findViewById(R.id.r12c1);
        r12c2 = (TextView) errorsView.findViewById(R.id.r12c2);
        r12c3 = (TextView) errorsView.findViewById(R.id.r12c3);
        r13c1 = (TextView) errorsView.findViewById(R.id.r13c1);
        r13c2 = (TextView) errorsView.findViewById(R.id.r13c2);
        r13c3 = (TextView) errorsView.findViewById(R.id.r13c3);
        r14c1 = (TextView) errorsView.findViewById(R.id.r14c1);
        r14c2 = (TextView) errorsView.findViewById(R.id.r14c2);
        r14c3 = (TextView) errorsView.findViewById(R.id.r14c3);
        r15c1 = (TextView) errorsView.findViewById(R.id.r15c1);
        r15c2 = (TextView) errorsView.findViewById(R.id.r15c2);
        r15c3 = (TextView) errorsView.findViewById(R.id.r15c3);
        r16c1 = (TextView) errorsView.findViewById(R.id.r16c1);
        r16c2 = (TextView) errorsView.findViewById(R.id.r16c2);
        r16c3 = (TextView) errorsView.findViewById(R.id.r16c3);
        r17c1 = (TextView) errorsView.findViewById(R.id.r17c1);
        r17c2 = (TextView) errorsView.findViewById(R.id.r17c2);
        r17c3 = (TextView) errorsView.findViewById(R.id.r17c3);
        r18c1 = (TextView) errorsView.findViewById(R.id.r18c1);
        r18c2 = (TextView) errorsView.findViewById(R.id.r18c2);
        r18c3 = (TextView) errorsView.findViewById(R.id.r18c3);
        r19c1 = (TextView) errorsView.findViewById(R.id.r19c1);
        r19c2 = (TextView) errorsView.findViewById(R.id.r19c2);
        r19c3 = (TextView) errorsView.findViewById(R.id.r19c3);
        r20c1 = (TextView) errorsView.findViewById(R.id.r20c1);
        r20c2 = (TextView) errorsView.findViewById(R.id.r20c2);
        r20c3 = (TextView) errorsView.findViewById(R.id.r20c3);
        r21c1 = (TextView) errorsView.findViewById(R.id.r21c1);
        r21c2 = (TextView) errorsView.findViewById(R.id.r21c2);
        r21c3 = (TextView) errorsView.findViewById(R.id.r21c3);
        r22c1 = (TextView) errorsView.findViewById(R.id.r22c1);
        r22c2 = (TextView) errorsView.findViewById(R.id.r22c2);
        r22c3 = (TextView) errorsView.findViewById(R.id.r22c3);
        r23c1 = (TextView) errorsView.findViewById(R.id.r23c1);
        r23c2 = (TextView) errorsView.findViewById(R.id.r23c2);
        r23c3 = (TextView) errorsView.findViewById(R.id.r23c3);
        r24c1 = (TextView) errorsView.findViewById(R.id.r24c1);
        r24c2 = (TextView) errorsView.findViewById(R.id.r24c2);
        r24c3 = (TextView) errorsView.findViewById(R.id.r24c3);
        r25c1 = (TextView) errorsView.findViewById(R.id.r25c1);
        r25c2 = (TextView) errorsView.findViewById(R.id.r25c2);
        r25c3 = (TextView) errorsView.findViewById(R.id.r25c3);
        r26c1 = (TextView) errorsView.findViewById(R.id.r26c1);
        r26c2 = (TextView) errorsView.findViewById(R.id.r26c2);
        r26c3 = (TextView) errorsView.findViewById(R.id.r26c3);
        r27c1 = (TextView) errorsView.findViewById(R.id.r27c1);
        r27c2 = (TextView) errorsView.findViewById(R.id.r27c2);
        r27c3 = (TextView) errorsView.findViewById(R.id.r27c3);
        r28c1 = (TextView) errorsView.findViewById(R.id.r28c1);
        r28c2 = (TextView) errorsView.findViewById(R.id.r28c2);
        r28c3 = (TextView) errorsView.findViewById(R.id.r28c3);
        r29c1 = (TextView) errorsView.findViewById(R.id.r29c1);
        r29c2 = (TextView) errorsView.findViewById(R.id.r29c2);
        r29c3 = (TextView) errorsView.findViewById(R.id.r29c3);
        r30c1 = (TextView) errorsView.findViewById(R.id.r30c1);
        r30c2 = (TextView) errorsView.findViewById(R.id.r30c2);
        r30c3 = (TextView) errorsView.findViewById(R.id.r30c3);
        // The TextViews are referenced to the layout.

        listErrors();

        return errorsView;
    }


    public void listErrors(){           // Method that will automatically select all Errors from the last journey.
        Cursor c=MainActivity.db.rawQuery("SELECT * FROM errors WHERE JourneyID = '" +ETS2_Fragment.JOURNEYID + "'", null);
        // Cursor is initialised to retrieve the data from errors table.
        if(c.getCount()==0)             // If no errors were made the user will be informed
        {
            System.out.println("no results"); // TODO Create a pop up message
            return;
        }
        String[][] errorstable = new String[30][3];         // Initialise an array that fits the table (30 rows, 3 columns)
        for(int x = 0; x < 30; x++) {                       // A for loop to set all array values to blank (invisible)
            for (int z = 0; z < 3.; z++)
                errorstable[x][z] = "";
        }
        int i = 0;

        c.moveToFirst();
        if (c.getCount() > 30) {                            // The table only holds 30 rows, if there were more than 30 errors
        while (i < 30) {                                    // the array index would be out of bounds.
            errorstable[i][0] = c.getString(0);             // Assign the values from the SQL columns to the array.
            errorstable[i][1] = c.getString(1);
            errorstable[i][2] = c.getString(2);
            i++;
            c.moveToNext();
        }} else if (i < c.getCount()){
            while (i < c.getCount()) {
                errorstable[i][0] = c.getString(0);
                errorstable[i][1] = c.getString(1);
                errorstable[i][2] = c.getString(2);
                i++;
                c.moveToNext();
        }}

        r1c1.setText(errorstable[0][0]);                        // The array values are assigned to each table row.
        r1c2.setText(errorstable[0][1]);
        r1c3.setText(errorstable[0][2]);
        r2c1.setText(errorstable[1][0]);
        r2c2.setText(errorstable[1][1]);
        r2c3.setText(errorstable[1][2]);
        r3c1.setText(errorstable[2][0]);
        r3c2.setText(errorstable[2][1]);
        r3c3.setText(errorstable[2][2]);
        r4c1.setText(errorstable[3][0]);
        r4c2.setText(errorstable[3][1]);
        r4c3.setText(errorstable[3][2]);
        r5c1.setText(errorstable[4][0]);
        r5c2.setText(errorstable[4][1]);
        r5c3.setText(errorstable[4][2]);
        r6c1.setText(errorstable[5][0]);
        r6c2.setText(errorstable[5][1]);
        r6c3.setText(errorstable[5][2]);
        r7c1.setText(errorstable[6][0]);
        r7c2.setText(errorstable[6][1]);
        r7c3.setText(errorstable[6][2]);
        r8c1.setText(errorstable[7][0]);
        r8c2.setText(errorstable[7][1]);
        r8c3.setText(errorstable[7][2]);
        r9c1.setText(errorstable[8][0]);
        r9c2.setText(errorstable[8][1]);
        r9c3.setText(errorstable[8][2]);
        r10c1.setText(errorstable[9][0]);
        r10c2.setText(errorstable[9][1]);
        r10c3.setText(errorstable[9][2]);
        r11c1.setText(errorstable[10][0]);
        r11c2.setText(errorstable[10][1]);
        r11c3.setText(errorstable[10][2]);
        r12c1.setText(errorstable[11][0]);
        r12c2.setText(errorstable[11][1]);
        r12c3.setText(errorstable[11][2]);
        r13c1.setText(errorstable[12][0]);
        r13c2.setText(errorstable[12][1]);
        r13c3.setText(errorstable[12][2]);
        r14c1.setText(errorstable[13][0]);
        r14c2.setText(errorstable[13][1]);
        r14c3.setText(errorstable[13][2]);
        r15c1.setText(errorstable[14][0]);
        r15c2.setText(errorstable[14][1]);
        r15c3.setText(errorstable[14][2]);
        r16c1.setText(errorstable[15][0]);
        r16c2.setText(errorstable[15][1]);
        r16c3.setText(errorstable[15][2]);
        r17c1.setText(errorstable[16][0]);
        r17c2.setText(errorstable[16][1]);
        r17c3.setText(errorstable[16][2]);
        r18c1.setText(errorstable[17][0]);
        r18c2.setText(errorstable[17][1]);
        r18c3.setText(errorstable[17][2]);
        r19c1.setText(errorstable[18][0]);
        r19c2.setText(errorstable[18][1]);
        r19c3.setText(errorstable[18][2]);
        r20c1.setText(errorstable[19][0]);
        r20c2.setText(errorstable[19][1]);
        r20c3.setText(errorstable[19][2]);
        r21c1.setText(errorstable[20][0]);
        r21c2.setText(errorstable[20][1]);
        r21c3.setText(errorstable[20][2]);
        r22c1.setText(errorstable[21][0]);
        r22c2.setText(errorstable[21][1]);
        r22c3.setText(errorstable[21][2]);
        r23c1.setText(errorstable[22][0]);
        r23c2.setText(errorstable[22][1]);
        r23c3.setText(errorstable[22][2]);
        r24c1.setText(errorstable[23][0]);
        r24c2.setText(errorstable[23][1]);
        r24c3.setText(errorstable[23][2]);
        r25c1.setText(errorstable[24][0]);
        r25c2.setText(errorstable[24][1]);
        r25c3.setText(errorstable[24][2]);
        r26c1.setText(errorstable[25][0]);
        r26c2.setText(errorstable[25][1]);
        r26c3.setText(errorstable[25][2]);
        r27c1.setText(errorstable[26][0]);
        r27c2.setText(errorstable[26][1]);
        r27c3.setText(errorstable[26][2]);
        r28c1.setText(errorstable[27][0]);
        r28c2.setText(errorstable[27][1]);
        r28c3.setText(errorstable[27][2]);
        r29c1.setText(errorstable[28][0]);
        r29c2.setText(errorstable[28][1]);
        r29c3.setText(errorstable[28][2]);
        r30c1.setText(errorstable[29][0]);
        r30c2.setText(errorstable[29][1]);
        r30c3.setText(errorstable[29][2]);
    }








}

package test.codevilla.co.actilifecycle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;


public class ListClassesActivity extends Activity {

	    String batchcode;
	    TableLayout tableClasses;
      String date2;
	String date1;
	int d;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.listclasses);
			
			// get batchcode using intent
			batchcode = getIntent().getStringExtra("batchcode");
			tableClasses = (TableLayout) this.findViewById( R.id.tableClasses);
		}
		
		@Override
		public void onStart() {
			super.onStart();
			deleteRowsFromTable();
			addRowsToTable(tableClasses,batchcode);
		}
		
		public void deleteRowsFromTable() {
			if ( tableClasses.getChildCount() > 2) 
			    tableClasses.removeViews(2,tableClasses.getChildCount() - 2);
	    }
		
		private void addRowsToTable(TableLayout table, String batchcode) {
			
			 List<Class> classes = Database.getClasses(this, batchcode);

			Calendar cdate = Calendar.getInstance();
			int day = cdate.get(Calendar.DAY_OF_MONTH);
			int month = cdate.get(Calendar.MONTH)+1;
			int year = cdate.get(Calendar.YEAR);
			date1 = year+"-"+month+"-"+day;

			  TableRow tr = new TableRow(this);
             tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			Calendar calendar = Calendar.getInstance();
				int classno = 1;
             for(final Class c : classes) {
            	    TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.classrow, null);
				 date2=c.getClassDate().toString();
				 Date inputDate = null;
				 try {
					 inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
				 } catch (ParseException e) {
					 e.printStackTrace();
				 }
				 calendar.setTime(inputDate);
				 d = Integer.parseInt(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)))-1;
				 switch (d) {
					 case 1:  d = 1;
						 ((TextView)row.findViewById(R.id.textNo)).setText( String.valueOf(classno)+":(Mon)");
						 break;
					 case 2:  d = 2;
						 ((TextView)row.findViewById(R.id.textNo)).setText( String.valueOf(classno)+":(Tue)");
						 break;
					 case 3:  d = 3;
						 ((TextView)row.findViewById(R.id.textNo)).setText( String.valueOf(classno)+":(Wed)");
						 break;
					 case 4:  d = 4;
						 ((TextView)row.findViewById(R.id.textNo)).setText( String.valueOf(classno)+":(Thu)");
						 break;
					 case 5:  d = 5;
						 ((TextView)row.findViewById(R.id.textNo)).setText( String.valueOf(classno)+":(Fri)");
						 break;
					 case 6:  d = 6;
						 ((TextView)row.findViewById(R.id.textNo)).setText( String.valueOf(classno)+":(Sat)");
						 break;
					 case 7:  d = 7;
						 ((TextView)row.findViewById(R.id.textNo)).setText( String.valueOf(classno)+":(Sun)");
						 break;}
            	    ((TextView)row.findViewById(R.id.textDate)).setText(c.getClassDate());
            	    ((TextView)row.findViewById(R.id.textTime)).setText(c.getClassTime());
				 // handle update button
            	    ImageButton btnUpdate = (ImageButton) row.findViewById(R.id.btnUpdate);
            	    btnUpdate.setOnClickListener( new OnClickListener() {
						@Override
						public void onClick(View v) {
                              Intent intent = new Intent( ListClassesActivity.this,UpdateClassActivity.class);
                              intent.putExtra("classid", c.getClassId()); 
                              startActivity(intent);
						}
					}); 
            	    
            	    table.addView(row);
            	    
            	    TableRow line = new TableRow(this);
            	    TextView tv = new TextView(this);
				 if(date1.compareTo(date2)>0){
					 tv.setBackgroundColor(Color.RED);
				 }
				 else{
					 tv.setBackgroundColor(Color.GREEN);
				 }
            	    TableRow.LayoutParams lp = new TableRow.LayoutParams(LayoutParams.MATCH_PARENT,3);
            	    lp.span = 4;
            	    tv.setLayoutParams(lp);
            	    
            	    line.addView(tv);
            	    
            	    table.addView(line);
           	    
            	    classno ++;
             }
			
		}
		
		
}

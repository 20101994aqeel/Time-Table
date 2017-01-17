package test.codevilla.co.actilifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateClassActivity extends Activity {
	private static final int DATE_DIALOG = 1;
	private static final int TIME_DIALOG = 2;
	private static final int CANCEL_ALERT_DIALOG = 3;
	private static final int DELETE_ALERT_DIALOG = 4;
	
	private int day, month, year, hours, mins;
	private TextView textClassDate, textClassTime, textBatchCode;
	private EditText editPeriod,editRemarks, editTopics;
	
	private String classid;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
         setContentView(R.layout.updateclass);


		textClassDate = (TextView) this.findViewById(R.id.textClassDate);
		textClassTime = (TextView) this.findViewById(R.id.textClassTime);
		
		textBatchCode = (TextView) this.findViewById(R.id.textBatchCode);
		editPeriod = (EditText) this.findViewById(R.id.editPeriod) ;
		editTopics = (EditText) this.findViewById(R.id.editTopics) ;
		editRemarks = (EditText) this.findViewById(R.id.editRemarks) ;
		
		// get details from database
		classid = getIntent().getStringExtra("classid");
	    Class clas = Database.getClass(this, classid);
		if ( clas == null)
		{
			// error 
		}
		else
		{
			textBatchCode.setText( clas.getBatchCode());
			textClassDate.setText( clas.getClassDate());
			textClassTime.setText( clas.getClassTime());
			setTimeToStartTime(clas.getClassTime());
			editPeriod.setText( clas.getPeriod());
			editTopics.setText( clas.getTopics());
			editRemarks.setText( clas.getRemarks());
			setDateToSysdate();
		}
    }


	private void setDateToSysdate() {
		Calendar c = Calendar.getInstance();
		day = c.get(Calendar.DAY_OF_MONTH);
		month = c.get(Calendar.MONTH);
		year = c.get(Calendar.YEAR);
	}
	private void setTimeToStartTime(String starttime) {
		String [] parts = starttime.split(":");
		hours = Integer.parseInt( parts[0]);
		mins =Integer.parseInt( parts[1]);
	}
	
	public void updateClass(View v) {
		 boolean done = Database.updateClass(this,
				  classid,
				 textClassDate.getText().toString(),
				  textClassTime.getText().toString(),
				  editPeriod.getText().toString(),
				  editTopics.getText().toString(),
				  editRemarks.getText().toString());
		 
		 if ( done )
			 Toast.makeText(this,"Updated class successfully!", Toast.LENGTH_LONG).show();
		 else
			 Toast.makeText(this,"Sorry! Could not update class!", Toast.LENGTH_LONG).show();
	}
	
	
	public void deleteClass(View v) {
		this.showDialog(DELETE_ALERT_DIALOG);
	}
	
	public void cancelClass(View v) {
		this.showDialog(CANCEL_ALERT_DIALOG);
	}

	public void showDatePicker(View v) {
		showDialog(DATE_DIALOG);
	}
	public void showTimePicker(View v) {
		showDialog(TIME_DIALOG);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);

		switch (id) {
			case DATE_DIALOG:
				return new DatePickerDialog(this, dateSetListener, year, month, day);
		 case TIME_DIALOG:
			return new TimePickerDialog(this, timeSetListener, hours,mins, false);
  	     case CANCEL_ALERT_DIALOG:
				return getCancelAlertDialog();
  	     case DELETE_ALERT_DIALOG:
				return getDeleteAlertDialog();			
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int pYear, int pMonth, int pDay) {
			year = pYear;
			month = pMonth;
			day = pDay;
			updateDateDisplay();
		}
	};
	private TimePickerDialog.OnTimeSetListener timeSetListener = 
			   new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					  hours = pHours;
					  mins = pMins;
					  updateTimeDisplay();
				}
	 
	};


	private void updateDateDisplay() {
		// Month is 0 based so add 1
		textClassDate.setText(String.format("%04d-%02d-%02d", year, month + 1,day));
	}
	private void updateTimeDisplay() {
		// Month is 0 based so add 1
		textClassTime.setText(String.format("%02d:%02d", hours,mins));
	}
	
	
	public Dialog getDeleteAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to delete current class?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								 boolean done = Database.deleteClass(UpdateClassActivity.this, classid);
								 
								 if ( done ) {
									 Toast.makeText(UpdateClassActivity.this,"Deleted Class Successfully!", Toast.LENGTH_LONG).show();
									 UpdateClassActivity.this.finish();
								 }
								 else
									 Toast.makeText(UpdateClassActivity.this,"Sorry! Could not delete class!", Toast.LENGTH_LONG).show();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		return builder.create();
	}
	
	
	public Dialog getCancelAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to delete current class and add another class?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								 boolean done = Database.cancelClass(UpdateClassActivity.this, textBatchCode.getText().toString(), classid);
								 if ( done ) {
									 Toast.makeText(UpdateClassActivity.this,"Cancelled current class and added new class successfully!", Toast.LENGTH_LONG).show();
									 UpdateClassActivity.this.finish();
								 }
								 else
									 Toast.makeText(UpdateClassActivity.this,"Sorry! Could not cancel class!", Toast.LENGTH_LONG).show();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		return builder.create();
	}
	
}

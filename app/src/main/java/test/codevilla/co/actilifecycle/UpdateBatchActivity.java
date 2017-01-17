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

public class UpdateBatchActivity extends Activity {
	private static final int DATE_DIALOG = 1;
	private static final int DELETE_ALERT_DIALOG = 3;
	
	private int day, month, year, hours, mins;
	private TextView textStartDate,textClasses,textClassesPerWeek;
	private EditText editBatchcode,editCourse,editPeriod, editRemarks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatebatch);

		textStartDate = (TextView) this.findViewById(R.id.textStartDate);

		editBatchcode = (EditText) this.findViewById(R.id.editBatchCode) ;
		editCourse = (EditText) this.findViewById(R.id.editCourse) ;
		editPeriod = (EditText) this.findViewById(R.id.editPeriod) ;
		textClasses = (TextView) this.findViewById(R.id.textClasses) ;
		textClassesPerWeek = (TextView) this.findViewById(R.id.textClassesPerWeek) ;
		editRemarks = (EditText) this.findViewById(R.id.editRemarks) ;
		
		// get details from database
		String batchcode = getIntent().getStringExtra("batchcode");
		Batch batch = Database.getBatch(this, batchcode);
		if ( batch == null)
		{
			// error 
		}
		else
		{
			editBatchcode.setText( batch.getCode());
			editCourse.setText( batch.getCourse());
			textStartDate.setText( batch.getStartdate());
			editPeriod.setText( batch.getPeriod());
			textClasses.setText( batch.getClasses());
			textClassesPerWeek.setText( batch.getClassesperweek());
			editRemarks.setText( batch.getRemarks());
			
			setDateToStartDate( batch.getStartdate());

		}
		
    }
	

	private void setDateToStartDate(String startdate) {
		String [] parts = startdate.split("-");
		day = Integer.parseInt( parts[2]);
		month =Integer.parseInt( parts[1]);
		year = Integer.parseInt( parts[0]);
	}

	public void updateBatch(View v) {

		 boolean done = Database.updateBatch(this,
				  editBatchcode.getText().toString(),
				  editCourse.getText().toString(),
				  editPeriod.getText().toString(),
				  editRemarks.getText().toString());
		 
		 if ( done )
			 Toast.makeText(this,"Updated batch successfully!", Toast.LENGTH_LONG).show();
		 else
			 Toast.makeText(this,"Sorry! Could not update batch!", Toast.LENGTH_LONG).show();
			 
	}
	
	
	public void deleteBatch(View v) {
		this.showDialog(DELETE_ALERT_DIALOG);
	}
	
	
	public void showDatePicker(View v) {
		showDialog(DATE_DIALOG);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);

		switch (id) {
		 case DATE_DIALOG:
			return new DatePickerDialog(this, dateSetListener, year, month, day);
  	     case DELETE_ALERT_DIALOG:
				return getAlertDialog();			
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

	

	private void updateDateDisplay() {
		// Month is 0 based so add 1
		textStartDate.setText(String.format("%04d-%02d-%02d", year, month + 1,day));
	}

	
	public Dialog getAlertDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Do you want to delete current batch?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								 boolean done = Database.deleteBatch(UpdateBatchActivity.this, editBatchcode.getText().toString());
								 
								 if ( done ) {
									 Toast.makeText(UpdateBatchActivity.this,"Deleted batch successfully!", Toast.LENGTH_LONG).show();
									 UpdateBatchActivity.this.finish();
								 }
								 else
									 Toast.makeText(UpdateBatchActivity.this,"Sorry! Could not delete batch!", Toast.LENGTH_LONG).show();
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

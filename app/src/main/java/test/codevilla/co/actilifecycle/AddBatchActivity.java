package test.codevilla.co.actilifecycle;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddBatchActivity extends Activity {
	private static final int DATE_DIALOG = 1;
	private static final int timepickmon = 3;
	private static final int timepicktue = 4;
	private static final int timepickwed = 5;
	private static final int timepickthu = 6;
	private static final int timepickfri = 7;
	private static final int timepicksat = 8;
	private int day, month, year, hours, mins;
	private TextView textStartDate,textTimemon,textTimetue,textTimewed,textTimethu,textTimefri,textTimesat;
	private EditText editBatchcode,editCourse,editPeriod,editClasses,editRemarks,editmon,edittue,editwed
			,editthu,editfri,editsat;
	private CheckBox mon,tue,wed,thu,fri,sat;
	String editClassesPerWeek;
int cow=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addbatch);

		textStartDate = (TextView) this.findViewById(R.id.textStartDate);

		editBatchcode = (EditText) this.findViewById(R.id.editBatchCode) ;
		editCourse = (EditText) this.findViewById(R.id.editCourse) ;
		editPeriod = (EditText) this.findViewById(R.id.editPeriod) ;
		editClasses = (EditText) this.findViewById(R.id.editClasses) ;
		editRemarks = (EditText) this.findViewById(R.id.editRemarks) ;
		mon = (CheckBox) this.findViewById(R.id.mon);
		tue = (CheckBox) this.findViewById(R.id.tue);
		wed = (CheckBox) this.findViewById(R.id.wed);
		thu = (CheckBox) this.findViewById(R.id.thu);
		fri = (CheckBox) this.findViewById(R.id.fri);
		sat = (CheckBox) this.findViewById(R.id.sat);
		editmon = (EditText) this.findViewById(R.id.editmon);
		edittue = (EditText) this.findViewById(R.id.edittue);
		editwed = (EditText) this.findViewById(R.id.editwed);
		editthu = (EditText) this.findViewById(R.id.editthu);
		editfri = (EditText) this.findViewById(R.id.editfri);
		editsat = (EditText) this.findViewById(R.id.editsat);
		textTimemon = (TextView) this.findViewById(R.id.texttimemon);
		textTimetue = (TextView) this.findViewById(R.id.texttimetue);
		textTimewed = (TextView) this.findViewById(R.id.texttimewed);
		textTimethu = (TextView) this.findViewById(R.id.texttimethu);
		textTimefri = (TextView) this.findViewById(R.id.texttimefri);
		textTimesat = (TextView) this.findViewById(R.id.texttimesat);
		setDateToSysdate();
		updateDateDisplay();

	}

	private void setDateToSysdate() {
		Calendar c = Calendar.getInstance();
		day = c.get(Calendar.DAY_OF_MONTH);
		month = c.get(Calendar.MONTH);
		year = c.get(Calendar.YEAR);
	}

	public void addBatch(View v) {
for(int i=1;i<=7;i++){
	switch (i) {
		case 1:  if(mon.isChecked()){
			int mon=Integer.parseInt(editmon.getText().toString());
			for(int j=1;j<=mon;j++){
		cow=cow+1;
			}
		}
		break;
		case 2:  if(tue.isChecked()){
			int tue=Integer.parseInt(edittue.getText().toString());
			for(int j=1;j<=tue;j++){
				cow=cow+1;
			}
		}
		break;
		case 3:  if(wed.isChecked()){
			int wed=Integer.parseInt(editwed.getText().toString());
			for(int j=1;j<=wed;j++){
				cow=cow+1;
			}
		}
		break;
		case 4:  if(thu.isChecked()){

			int thu=Integer.parseInt(editthu.getText().toString());
			for(int j=1;j<=thu;j++){
				cow=cow+1;
			}
		}
		break;
		case 5:  if(fri.isChecked()){

			int fri=Integer.parseInt(editfri.getText().toString());
			for(int j=1;j<=fri;j++){
				cow=cow+1;
			}
		}
		break;
		case 6:  if(sat.isChecked()){
			int sat=Integer.parseInt(editsat.getText().toString());
			for(int j=1;j<=sat;j++){
				cow=cow+1;
			}
		}
		break;
	}
}

		 editClassesPerWeek=String.valueOf(cow);
		boolean done = Database.addBatch(this,
				  editBatchcode.getText().toString(),
				  editCourse.getText().toString(),
				  textStartDate.getText().toString(),
				  editClasses.getText().toString(),
				  editPeriod.getText().toString(),
				  editClassesPerWeek.toString(),
				  editRemarks.getText().toString(),
				 mon.isChecked(),
				 tue.isChecked(),
				 wed.isChecked(),
				 thu.isChecked(),
				 fri.isChecked(),
				 sat.isChecked(),
				editmon.getText().toString(),
				edittue.getText().toString(),
				editwed.getText().toString(),
				editthu.getText().toString(),
				editfri.getText().toString(),
				editsat.getText().toString(),
				textTimemon.getText().toString(),
				textTimetue.getText().toString(),
				textTimewed.getText().toString(),
				textTimethu.getText().toString(),
				textTimefri.getText().toString(),
				textTimesat.getText().toString());

		 if ( done )
			 Toast.makeText(this,"Added batch successfully!", Toast.LENGTH_LONG).show();
		 else
			 Toast.makeText(this,"Sorry! Could not add batch!", Toast.LENGTH_LONG).show();
	}
	
	
	public void showDatePicker(View v) {
		showDialog(DATE_DIALOG);
	}
	public void showTimePickermon(View v){
		showDialog(timepickmon);
	}
	public void showTimePickertue(View v){
		showDialog(timepicktue);
	}
	public void showTimePickerwed(View v){
		showDialog(timepickwed);
	}
	public void showTimePickerthu(View v){
		showDialog(timepickthu);
	}
	public void showTimePickerfri(View v){
		showDialog(timepickfri);
	}
	public void showTimePickersat(View v){
		showDialog(timepicksat);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		super.onCreateDialog(id);

		switch (id) {
		 case DATE_DIALOG:
			return new DatePickerDialog(this, dateSetListener, year, month, day);
			case timepickmon:
			return	new TimePickerDialog(this, timeSetListenermon, hours,mins, false);
			case timepicktue:
				return	new TimePickerDialog(this, timeSetListenertue, hours,mins, false);
			case timepickwed:
				return	new TimePickerDialog(this, timeSetListenerwed, hours,mins, false);
			case timepickthu:
				return	new TimePickerDialog(this, timeSetListenerthu, hours,mins, false);
			case timepickfri:
				return	new TimePickerDialog(this, timeSetListenerfri, hours,mins, false);
			case timepicksat:
				return	new TimePickerDialog(this, timeSetListenersat, hours,mins, false);

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

	private TimePickerDialog.OnTimeSetListener timeSetListenermon =
			new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					hours = pHours;
					mins = pMins;
					textTimemon.setText(String.format("%02d:%02d", hours,mins));
				}

			};



	private TimePickerDialog.OnTimeSetListener timeSetListenertue =
			new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					hours = pHours;
					mins = pMins;
					textTimetue.setText(String.format("%02d:%02d", hours,mins));
				}

			};


	private TimePickerDialog.OnTimeSetListener timeSetListenerwed =
			new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					hours = pHours;
					mins = pMins;
					textTimewed.setText(String.format("%02d:%02d", hours,mins));
				}

			};



	private TimePickerDialog.OnTimeSetListener timeSetListenerthu =
			new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					hours = pHours;
					mins = pMins;
					textTimethu.setText(String.format("%02d:%02d", hours,mins));
				}

			};



	private TimePickerDialog.OnTimeSetListener timeSetListenerfri =
			new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					hours = pHours;
					mins = pMins;
					textTimefri.setText(String.format("%02d:%02d", hours,mins));
				}

			};



	private TimePickerDialog.OnTimeSetListener timeSetListenersat =
			new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker arg0, int pHours, int  pMins) {
					hours = pHours;
					mins = pMins;
					textTimesat.setText(String.format("%02d:%02d", hours,mins));
				}

			};

	private void updateDateDisplay() {
		// Month is 0 based so add 1
		textStartDate.setText(String.format("%04d-%02d-%02d", year, month + 1,day));
	}



}

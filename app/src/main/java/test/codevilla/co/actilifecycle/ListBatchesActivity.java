package test.codevilla.co.actilifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListBatchesActivity  extends Activity {
    int width=0,height=0;
	ListView listBatches;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listbatches);
		listBatches=(ListView) findViewById(R.id.listBatches);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		Configuration newConf=new Configuration();
		if(newConf.orientation==Configuration.ORIENTATION_LANDSCAPE)
		{
		;
			listBatches.getLayoutParams().height=height/3;
		}
		else{

			listBatches.getLayoutParams().height = height - height / 4;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		BatchesAdapter adapter  = new BatchesAdapter(this);
		listBatches.setAdapter(adapter);
	}

	public void addBatch(View v) {
		Intent intent = new Intent(this, AddBatchActivity.class);
		startActivity(intent);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE)
		{

			listBatches.getLayoutParams().height=height/3;
		}
		else{
			listBatches.getLayoutParams().height = height - height / 4;
		}
	}
}

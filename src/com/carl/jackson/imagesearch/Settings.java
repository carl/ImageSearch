package com.carl.jackson.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class Settings extends Activity {

	public static final String KEY_NAME = "options";

	Spinner spImageSize;
	Spinner spColorFilter;
	Spinner spImageType;
	EditText etSiteFilter;
	SearchSettings searchSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		searchSettings  = (SearchSettings) getIntent().getSerializableExtra(KEY_NAME);

		spImageSize = (Spinner)findViewById(R.id.spImageSize);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.imageSizes , android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageSize.setAdapter(adapter);
		spImageSize.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				String item = parent.getItemAtPosition(pos).toString();
				searchSettings.setImageSize(item);
				setSpinnerToValue(spImageSize, item);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				searchSettings.setImageSize("");
			}
		});

		spColorFilter = (Spinner)findViewById(R.id.spColorFilter);
		adapter = ArrayAdapter.createFromResource(this, R.array.colors , android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spColorFilter.setAdapter(adapter);
		spColorFilter.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				String item = parent.getItemAtPosition(pos).toString();
				searchSettings.setColorFilter(item);
				setSpinnerToValue(spColorFilter, item);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				searchSettings.setColorFilter("");
			}
		});

		spImageType = (Spinner)findViewById(R.id.spImageType);
		adapter = ArrayAdapter.createFromResource(this, R.array.imageTypes , android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageType.setAdapter(adapter);
		spImageType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				String item = parent.getItemAtPosition(pos).toString();
				searchSettings.setImageType(item);
				setSpinnerToValue(spImageType, item);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				searchSettings.setImageSize("");
			}
		});

		etSiteFilter = (EditText)findViewById(R.id.etSiteFilter);
		String siteFilter = searchSettings.getSiteFilter();
		etSiteFilter.setText(siteFilter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void onDone(View view) {
		String site = etSiteFilter.getText().toString();
		searchSettings.setSiteFilter(site);
		Intent i = new Intent();
		i.putExtra(KEY_NAME, searchSettings);
		setResult(Activity.RESULT_OK, i);

		finish();
	}

	public void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
			}
		}
		spinner.setSelection(index);
	}
}

package com.carl.jackson.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	SearchSettings searchSettings = new SearchSettings();
	ImageResultArrayAdapter imageAdapter;

	public static final int IMAGE_DISPLAY_ACTIVITY = 0;
	public static final int SETTINGS_ACTIVITY = 1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadImagesForPage(page);
			}
		});

		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SETTINGS_ACTIVITY && resultCode == Activity.RESULT_OK) {
			searchSettings = (SearchSettings) data.getSerializableExtra(Settings.KEY_NAME);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(getApplicationContext(), Settings.class);
			i.putExtra(Settings.KEY_NAME, searchSettings);
			startActivityForResult(i, SETTINGS_ACTIVITY);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch= (Button) findViewById(R.id.btnSearch);
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		imageResults.clear();
		loadImagesForPage(0);
	}

	protected void loadImagesForPage(int page) {
		Log.d("DEBUG", "page=" + page);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://ajax.googleapis.com/ajax/services/search/images?" + getQueryParams(), new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private String getQueryParams() {
		String params = "";
		String value = searchSettings.getColorFilter();
		if(value != null && value.length() > 0)
		{
			params += "&imgcolor=" + Uri.encode(value);
		}
		value = searchSettings.getImageSize();
		if(value != null && value.length() > 0)
		{
			params += "&imgsz="+ Uri.encode(value);
		}

		value = searchSettings.getImageType();
		if(value != null && value.length() > 0)
		{
			params += "&imgtype="+Uri.encode(value);
		}

		value = searchSettings.getSiteFilter();
		if(value != null && value.length() > 0)
		{
			params += "&as_sitesearch=" + Uri.encode(value);
		}

		String query = etQuery.getText().toString();
		params += "&v=1.0&q=" + Uri.encode(query);

		return params;
	}
}

package com.erkutdemirhan.bugunneyesek.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.Recipe;
import com.erkutdemirhan.bugunneyesek.utils.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Implements the starting screen that downloads recipes and loads the global variables
 */
public class RecipeDownloadScreen extends Activity {

    /** Link of the json file to download */
    private static final String JSON_FILE_LINK = "https://dl.dropboxusercontent.com/u/46607299/BugunNeYesek/tarifler.json";

    /** Name of the json file to download */
    private static final String JSON_FILE_NAME = "recipes.json";

    private static final String CHARSET = "UTF-8";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_download_screen);
        TextView statusText = (TextView) findViewById(R.id.download_status_text);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.download_progress_bar);

        RecipeDownloaderTask recipeDownloaderTask = new RecipeDownloaderTask(statusText, progressBar);
        recipeDownloaderTask.execute(JSON_FILE_LINK);
    }

    /**
     * AsyncTask class that performs the recipe download and variable loading tasks.
     */
    private class RecipeDownloaderTask extends AsyncTask<String, String, ArrayList<Recipe>> {

        /** Increment value for the progress bar */
        private static final int PROGRESS_INCR = 30;

        private TextView mProgressText;
        private ProgressBar mProgressBar;
        private int mProgress;

        public RecipeDownloaderTask(TextView progressText, ProgressBar progressBar) {
            mProgressBar = progressBar;
            mProgressText = progressText;
            mProgress = 0;
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setProgress(mProgress);
            mProgressText.setText(getApplicationContext().getString(R.string.download_recipe_file_progress));
        }

        @Override
        protected ArrayList<Recipe> doInBackground(String[] params) {
            String jsonUrl = getJSONStringFromUrl(params[0]);
            publishProgress(getApplicationContext().getString(R.string.update_recipe_file_progress));
            String jsonInternal = getJSONStringFromInternalStorage();
            JSONObject jsonObject = null;
            try {
                if(jsonUrl.length() > jsonInternal.length()) {
                    writeJSONStringToInternalStorage(jsonUrl);
                    jsonObject = new JSONObject(jsonUrl);
                } else {
                    jsonObject = new JSONObject(jsonInternal);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<Recipe> recipeList = JsonParser.jsonToRecipeList(jsonObject);
            publishProgress(getApplicationContext().getString(R.string.load_recipes_progress));
            return recipeList;
        }

        @Override
        protected void onProgressUpdate(String[] params) {
            super.onProgressUpdate();
            mProgress += PROGRESS_INCR;
            mProgressBar.setProgress(mProgress);
            mProgressText.setText(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipeList) {
            super.onPostExecute(recipeList);
            final BugunNeYesek bugunNeYesek = (BugunNeYesek) getApplicationContext();
            bugunNeYesek.initializeMaps(recipeList);
            mProgress += PROGRESS_INCR;
            mProgressBar.setProgress(mProgress);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        /**
         * Writes contents of the Json string into an internal file.
         * @param jsonString
         */
        private void writeJSONStringToInternalStorage(String jsonString) {
            try {
                FileOutputStream fos = openFileOutput(JSON_FILE_NAME, Context.MODE_PRIVATE);
                fos.write(jsonString.getBytes());
                fos.close();
            } catch (Exception e) {
                Log.e("File writer error", "Error writing json file " + e.toString());
            }
        }

        /**
         * Gets contents of the internal json file and returns it in string format
         *
         * @return
         */
        private String getJSONStringFromInternalStorage() {
            String res = "";
            FileInputStream is = null;
            try {
                is = openFileInput(JSON_FILE_NAME);
            } catch (FileNotFoundException e) {
                Log.e("File reader error", "Error reading json file from internal storage " + e.toString());
            }

            if(is==null) {
                return res;
            }

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, CHARSET), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                res = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return res;
        }

        /**
         * Gets contents of the json file from given url, and returns it as a string
         *
         * @param url
         * @return
         */
        private String getJSONStringFromUrl(String url) {
            InputStream is = null;
            String json = "";
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BufferedReader reader;
            try {
                reader = new BufferedReader(new InputStreamReader(is, CHARSET), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return json;
        }
    }

}

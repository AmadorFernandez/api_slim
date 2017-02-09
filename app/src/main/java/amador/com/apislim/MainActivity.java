package amador.com.apislim;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DisplayContext;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://amador.alumno.club/";
    private static final String ALL_SITES = "sites";
    private static final String CRUD_ACTIONS = "site";
    private static final String MAIL = "mail";
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String LINK = "link";
    private static final String NAME = "name";
    private static final String PWD = "pwd";
    private static final String MSG = "msg";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String SUBJECT = "subject";
    private static final int CREATE_SITE = 1;
    private static final int UPDATE_SITE = 2;
    public static final String RECOVERY_MODE_OPEN = "mode";
    public static final String RECOVERY_SITE = "site";

    private ProgressDialog progressDialog;
    private ListView listView;
    private SitesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this);
        registerForContextMenu(listView);
        getAllSites();
    }


    private void getAllSites(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        progressDialog.setMessage("Descargando...");
        progressDialog.show();

        client.get(URL+ALL_SITES, new JsonHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, responseString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                progressDialog.dismiss();
                Gson gson = new Gson();

                try {

                    Result result = gson.fromJson(String.valueOf(response), Result.class);

                    if(result.getCode()){

                        adapter = new SitesAdapter(MainActivity.this, result.getSites());
                        listView.setAdapter(adapter);


                    }else {

                        Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } catch (JsonSyntaxException e) {

                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void deleteSite(int id){

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        RequestParams params = new RequestParams();
        progressDialog.setMessage("Borrando...");
        progressDialog.show();

        client.delete(URL + CRUD_ACTIONS + "/" +String.valueOf(id) , params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                progressDialog.dismiss();
                String response = new String(responseBody);
                Gson gson = new Gson();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Result result = gson.fromJson(String.valueOf(jsonObject), Result.class);

                    if(result.getCode()){

                       getAllSites();

                    }else {

                        Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        
    }

    private void updateSite(Site site){

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        RequestParams params = new RequestParams();
        params.put(NAME, site.getName());
        params.put(LINK, site.getLink());
        params.put(EMAIL, site.getEmail());
        progressDialog.setMessage("Actualizando...");
        progressDialog.show();

        client.put(URL + CRUD_ACTIONS + "/"+String.valueOf(site.getId()), params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                progressDialog.dismiss();
                String response = new String(responseBody);
                Gson gson = new Gson();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Result result = gson.fromJson(String.valueOf(jsonObject), Result.class);

                    if(result.getCode()){

                        getAllSites();

                    }else {

                        Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void sendMsg(String to, String subject, String msg, String from, String pwd){

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        RequestParams params = new RequestParams();
        params.put(TO, to );
        params.put(SUBJECT, subject);
        params.put(MSG, msg);
        params.put(FROM, from);
        params.put(PWD, pwd);
        progressDialog.setMessage("Enviando...");
        progressDialog.show();

        client.post(URL + MAIL, params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                progressDialog.dismiss();
                String response = new String(responseBody);
                Gson gson = new Gson();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Result result = gson.fromJson(String.valueOf(jsonObject), Result.class);
                    Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();


                } catch (JSONException e) {

                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void insertSite(Site site){

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        RequestParams params = new RequestParams();
        params.put(NAME, site.getName());
        params.put(LINK, site.getLink());
        params.put(EMAIL, site.getEmail());
        progressDialog.setMessage("Guardando...");
        progressDialog.show();

        client.post(URL + CRUD_ACTIONS, params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                progressDialog.dismiss();
                String response = new String(responseBody);
                Gson gson = new Gson();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Result result = gson.fromJson(String.valueOf(jsonObject), Result.class);

                    if(result.getCode()){

                        adapter.clear();
                        adapter.addAll(result.getSites());

                    }else {

                        Toast.makeText(MainActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){

            Site site = data.getParcelableExtra(RECOVERY_SITE);

            switch (requestCode){

                case UPDATE_SITE:
                    updateSite(site);
                    break;
                case CREATE_SITE:
                    insertSite(site);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo());
        Site site = adapter.getItem(info.position);

        switch (item.getItemId()){

            case R.id.action_edit:
                Intent intent = new Intent(this, GestionSites.class);
                intent.putExtra(RECOVERY_MODE_OPEN, UPDATE_SITE);
                intent.putExtra(RECOVERY_SITE, (Parcelable) site);;
                startActivityForResult(intent, UPDATE_SITE);
                break;
            case R.id.action_delete:
                deleteSite(site.getId());
                break;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_email:
                throwDialogMail();
                break;
            case R.id.action_add:
                Intent intent = new Intent(this, GestionSites.class);
                intent.putExtra(RECOVERY_MODE_OPEN, CREATE_SITE);
                intent.putExtra(RECOVERY_SITE, (Parcelable) new Site());;
                startActivityForResult(intent, CREATE_SITE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void throwDialogMail(){

        View view = LayoutInflater.from(this).inflate(R.layout.email_dialog, null);
        final TextView from = (TextView)view.findViewById(R.id.edtFrom);
        final TextView to = (TextView)view.findViewById(R.id.edtTo);
        final TextView pwd = (TextView)view.findViewById(R.id.edtPwd);
        final TextView subject = (TextView)view.findViewById(R.id.edtSubject);
        final TextView body = (TextView)view.findViewById(R.id.edtBody);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(view);
        dialog.setTitle("ENVIO DE EMAIL");
        dialog.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                    sendMsg(to.getText().toString(), subject.getText().toString(), body.getText().toString(),
                            from.getText().toString(), pwd.getText().toString());



            }

        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        }).show();

    }
}



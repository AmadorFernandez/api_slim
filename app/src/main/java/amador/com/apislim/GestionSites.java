package amador.com.apislim;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GestionSites extends AppCompatActivity {

    private Button btnOk;
    private EditText edtName, edtEmail, edtLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_sites);

        btnOk = (Button)findViewById(R.id.button);
        edtEmail = (EditText)findViewById(R.id.edtEmailSite);
        edtName = (EditText)findViewById(R.id.edtNameSite);
        edtLink = (EditText)findViewById(R.id.edtLinkSite);
        final Site site = getIntent().getParcelableExtra(MainActivity.RECOVERY_SITE);
        final int mode = getIntent().getExtras().getInt(MainActivity.RECOVERY_MODE_OPEN);
        registerTextChange();
        edtEmail.setText(site.getEmail());
        edtLink.setText(site.getLink());
        edtName.setText(site.getName());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Validations.validateEmail(edtEmail.getText().toString())){

                    edtEmail.setError(getString(R.string.invalid_email));

                }else if(!Validations.validateLink(edtLink.getText().toString())){

                    edtLink.setError(getString(R.string.invalid_link));

                }else if(!Validations.validateName(edtName.getText().toString())){

                    edtName.setError(getString(R.string.invalid_name));

                }else {

                    site.setEmail(edtEmail.getText().toString());
                    site.setName(edtName.getText().toString());
                    site.setLink(edtLink.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.RECOVERY_SITE, (Parcelable) site);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }
        });

    }

    private void registerTextChange() {

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                edtEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                edtName.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                edtLink.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

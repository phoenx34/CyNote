package com.example.cs309_cynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONException;

/**
 * AddUserToClass page, this page is used to add the student or TA type user into specific class.
 *
 * @author Zheming Fan
 */
public class AddUserToClass extends AppCompatActivity {

    private EditText editInputClassCode;
//    private Button cancelFromAddToClass, updateFromAddToClass;
    private int uid, cid;
    private APICalls api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_class);

        Bundle extras = getIntent().getExtras();


        try{

            //----Ensuring the data actually exists----\\

            //If the extras does not exist, big oof
            if(extras == null)
                throw new Exception("No data received");

            //Try pulling data from extras
            SetUid(extras.getInt("UID"));
            //If data does not exist, big oof

            }
        catch(JSONException e) {
            System.out.println("JSONException: ");
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        editInputClassCode = findViewById(R.id.addToClassInput);


    }


    /**
     * Find class-ID (cid) by using APICalls, volley GET method with String-className from input
     */
    public void addUserToClass(final View view) {

//        String url = "http://cs309-sd-7.misc.iastate.edu:8080/";//waiting for backend path /*****
        String inputClassCode = editInputClassCode.getText().toString();//record class code from input
        api = new APICalls(getApplicationContext());//new APICalls

        //check if the Code is null
        if(inputClassCode == null || inputClassCode.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid class name, try again!", Toast.LENGTH_LONG).show();
            return;
        }

        //create url link with UID and CID
        String url = "http://cs309-sd-7.misc.iastate.edu:8080/addclass/";
        url += uid + "/" + cid;

        //get correct response to get class list and go to ClassSelection page
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //call API to get class list and go to ClassSelection page
                api.getClassList(view, getUid());
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Cannot add user to this class");
                System.out.println(error.getMessage());
            }
        };

        try {
            //try Get method to add user to a class
            api.volleyGet(url, responseListener, errorListener);
        } catch (Exception e) {
            System.out.println(e.getMessage());
//
//        url += inputClassCode;
//        APICalls api = new APICalls(getApplicationContext());
//
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                System.out.println("Class ID received");
//                System.out.println(response);
//
//                try {
//                    JSONObject jsonObj = new JSONObject(response);
//                    SetCid(jsonObj.getInt("CID"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                AddUserToClass();
//            }
//        };
//
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("Cannot receive class ID");
//                System.out.println(error.getMessage());
//            }
//        };
//
//        try {
//            api.volleyGet(url, responseListener, errorListener);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }
    }


//    //This still has error with getting classList to ClassSelection page
//    /**
//     * Method used to add current user to a class
//     */
//    public void AddUserToClass() {
//
//        String url = "http://cs309-sd-7.misc.iastate.edu:8080/addclass/";
//        url += uid + "/" + cid;
//        APICalls api = new APICalls(getApplicationContext());
//
//
//        Response.Listener<String> responseListener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Intent goToClassSelection = new Intent(AddUserToClass.this, ClassSelection.class);
//                goToClassSelection.putExtra("UID", uid);
//                startActivity(goToClassSelection);
//            }
//        };
//
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("Cannot add user to this class");
//                System.out.println(error.getMessage());
//            }
//        };
//
//        try {
//            api.volleyGet(url, responseListener, errorListener);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//        }
    }

    /**
     * Cancel the adding action and go back to ClassSelection page.
     * @param view View selected to cancel action.
     */
    public void CancelAddToClass(View view) {
        finish();
    }

    /**
     * Set CID as int
     * @param receivedInteger int number for setting CID
     */
    public void SetCid(int receivedInteger)
    {
        cid = receivedInteger;
    }

    /**
     * Get CID
     * @return Return CID as int
     */
    public int getCid(){
        return cid;
    }

    /**
     * Set UID as int
     * @param receivedInteger int number for setting UID
     */
    public void SetUid(int receivedInteger)
    {
        uid = receivedInteger;
    }

    /**
     * Get UID
     * @return Return UID as int
     */
    public int getUid(){
        return uid;
    }

}

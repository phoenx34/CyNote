package com.example.cs309_cynote;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.objects.ClEnt;
import com.example.objects.User;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * AddUserToClass page, this page is used to add the student or TA type user into specific class,
 * or create new class by Professor type user.
 *
 * @author Zheming Fan
 */
public class AddUserToClass extends AppCompatActivity {

    private EditText editInputClassCode, editInputClassName;
//    private Button cancelFromAddToClass, updateFromAddToClass;
    private int cid;
    private User user;
    private String newClassName;
    private static View jump;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_class);

        Intent intent = getIntent();
        // Grab User passed through intent
        try{
            //Grab the received User
            User user = (User)intent.getSerializableExtra("User");
            System.out.println(user);
            if(user == null)
                throw new Exception("No user received from ClassSelection");
            this.user = user;
        }
        catch(Exception e){
            System.out.println("Exception: ");
            System.out.println(e.getMessage());
        }
        editInputClassCode = findViewById(R.id.addToClassInput);
        editInputClassName = findViewById(R.id.NewClassNameInput);

        //check user type and visible corresponding button
        if(user.getUserType().equalsIgnoreCase("Professor") || user.getUserType().contains("test"))
        {
            //visible add class TextView and Button
            editInputClassCode.setVisibility(View.VISIBLE);
            Button classCodeInputBut = findViewById(R.id.addUserToClassBut);
            classCodeInputBut.setVisibility(View.VISIBLE);
            //visible create class TextView and Button
            editInputClassName.setVisibility(View.VISIBLE);
            Button classNameInputBut = findViewById(R.id.CreateClassBut);
            classNameInputBut.setVisibility(View.VISIBLE);
        }
        else
        {
            //visible add class TextView and Button
            editInputClassCode.setVisibility(View.VISIBLE);
            Button classCodeInputBut = findViewById(R.id.addUserToClassBut);
            classCodeInputBut.setVisibility(View.VISIBLE);
        }

    }

//    /**
//     * Start action of add user to class
//     */
//    public void addUserToClass(final View view) {
//        addUserToClassAction();
//    }

    /**
     * Find class-ID (cid) by using APICalls, volley GET method with String-className from input
     */
    public void addUserToClass(final View view) {

        String inputStringCheck = editInputClassCode.getText().toString();//record input as String
        //check if the Code is null
        if(inputStringCheck == null || inputStringCheck.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid class name, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        int inputClassCode = Integer.parseInt(editInputClassCode.getText().toString());//record class code from input as int

        final APICalls api = new APICalls(getApplicationContext());//new APICalls

        this.setCid(inputClassCode);//set CID from input

        //create url link with UID and CID
        String url = "http://cs309-sd-7.misc.iastate.edu:8080/addclass/";
        url += user.getUID() + "/" + cid;

        //get correct response to get class list and go to ClassSelection page
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                try{
//                    if(response.equalsIgnoreCase("false")) throw new Exception("Class can not be added!");
//                }
//                catch(Exception e) {
//                    System.out.println("Exception: ");
//                    System.out.println(e.getMessage());
//                }

                final APICallbacks classCallbacks = new APICallbacks<User>() {
                    @Override
                    public void onResponse(User user) {
                        //Now that this user is completed, move to ClassSelection as login is complete
                        Intent intent = new Intent(view.getContext(), ClassSelection.class);
                        intent.putExtra("User", user);         //Add User to ClassSelection intent
                        startActivity(intent);
                    }

                    @Override
                    public void onVolleyError(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                };

                api.getClassList(user, classCallbacks);

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
        }

    }

    /**
     * Create new class, this method only used by Professor type user.
     */
    public void CreateNewClass(final View view) throws JSONException {

        newClassName = editInputClassName.toString();//record class name from input
        if(newClassName == null || newClassName.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid class name, try again!", Toast.LENGTH_LONG).show();
            return;
        }

        String url= "http://cs309-sd-7.misc.iastate.edu:8080/classes";//create url
        //create json String for request body
        String json = "{\"cid\":\"" + 0 + "\"," +
                "\"name\":\"" + newClassName + "\"}";

        //create new api
        final APICalls api = new APICalls(getApplicationContext());

        //Set up listener for success case
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                //Toast massage
                Toast.makeText(getApplicationContext(),"Class created!", Toast.LENGTH_LONG).show();

                //call APICalls.getClassList to get class list and go to ClassSelection page
                //api.getClassList(view, getUid(), userType);
            }
        };

        //Set up listener for error case
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Class created fails");
                //Toast massage
                Toast.makeText(getApplicationContext(),"Class creation FAILS!", Toast.LENGTH_LONG).show();
                System.out.println(error.getMessage());

            }
        };

        System.out.println("Calling API");
        api.volleyPost(url, json, responseListener, errorListener);//send JSON request
    }

    /**
     * Open camera and scan the QR code to add user to class.
     * @param view
     */
    public void scanToAddToClass(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        jump = view;
        integrator.initiateScan();
    }

    /**
     * This method get result from scanner and add user to class.
     * @param requestCode   int number including request
     * @param resultCode    int number including result
     * @param data          intent obj pointing this page
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(this,"Scan has been cancelled or stopped",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(this,"Add To Class!",Toast.LENGTH_LONG).show();
                setCid(Integer.parseInt(result.getContents()));//set cid from result
                System.out.println(getCid());
                editInputClassCode.setText(getCid() + "");//set inputBox to be cid
                addUserToClass(jump);

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * Cancel the adding action and go back to ClassSelection page.
     * @param view View selected to cancel action.
     */
    public void CancelAddToClass(final View view) {
        //finish();

        final APICalls api = new APICalls(getApplicationContext());//new APICalls
        final APICallbacks classCallbacks = new APICallbacks<User>() {
            @Override
            public void onResponse(User user) {
                //Now that this user is completed, move to ClassSelection as login is complete
                Intent intent = new Intent(view.getContext(), ClassSelection.class);
                intent.putExtra("User", user);         //Add User to ClassSelection intent
                startActivity(intent);
            }

            @Override
            public void onVolleyError(VolleyError error) {
                System.out.println(error.getMessage());
            }
        };

        api.getClassList(user, classCallbacks);
    }

    /**
     * Set CID as int
     * @param receivedInteger int number for setting CID
     */
    public void setCid(int receivedInteger)
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
     * Set user as User type.
     * @param inputUser User obj to set user
     */
    public void setUser(User inputUser)
    {
        this.user = inputUser;
    }

    /**
     * Get User.
     * @return Return user as User type
     */
    public User getUser(){
        return user;
    }

}

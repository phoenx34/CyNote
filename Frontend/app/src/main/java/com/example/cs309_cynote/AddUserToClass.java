package com.example.cs309_cynote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONException;

/**
 * AddUserToClass page, this page is used to add the student or TA type user into specific class,
 * or create new class by Professor type user.
 *
 * @author Zheming Fan
 */
public class AddUserToClass extends AppCompatActivity {

    private EditText editInputClassCode, editInputClassName;
//    private Button cancelFromAddToClass, updateFromAddToClass;
    private int uid, cid;
    private String userType, newClassName;


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
            setUid(extras.getInt("UID"));//get UID
            setUserType(extras.getString("userType"));//get userType
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
        editInputClassName = findViewById(R.id.NewClassNameInput);

        //check user type and visible corresponding button
        if(userType.equalsIgnoreCase("Professor") || userType.contains("test"))
        {
            //visible add class TextView and Button
            editInputClassCode.setVisibility(View.VISIBLE);
            Button classCodeInputBut = findViewById(R.id.CreateClassBut);
            classCodeInputBut.setVisibility(View.VISIBLE);
            //visible create class TextView and Button
            editInputClassName.setVisibility(View.VISIBLE);
            Button classNameInputBut = findViewById(R.id.addUserToClassBut);
            classNameInputBut.setVisibility(View.VISIBLE);
        }
        else
        {
            //visible add class TextView and Button
            editInputClassCode.setVisibility(View.VISIBLE);
            Button classCodeInputBut = findViewById(R.id.CreateClassBut);
            classCodeInputBut.setVisibility(View.VISIBLE);
        }

    }


    /**
     * Find class-ID (cid) by using APICalls, volley GET method with String-className from input
     */
    public void addUserToClass(final View view) {

//        String url = "http://cs309-sd-7.misc.iastate.edu:8080/";//waiting for backend path /*****
        int inputClassCode = Integer.parseInt(editInputClassCode.getText().toString());//record class code from input
        String inputStringCheck = editInputClassCode.getText().toString();
        final APICalls api = new APICalls(getApplicationContext());//new APICalls

        //check if the Code is null
        if(inputStringCheck == null || inputStringCheck.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Invalid class name, try again!", Toast.LENGTH_LONG).show();
            return;
        }
        this.setCid(inputClassCode);//set CID from input

        //create url link with UID and CID
        String url = "http://cs309-sd-7.misc.iastate.edu:8080/addclass/";
        url += uid + "/" + cid;

        //get correct response to get class list and go to ClassSelection page
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //call API to get class list and go to ClassSelection page
                api.getClassList(view, getUid(), userType);
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
//                    setCid(jsonObj.getInt("CID"));
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
     * Create new class, this method only used by Professor type user.
     */
    public void CreateNewClass(final View view) throws JSONException {

        newClassName = editInputClassName.toString();//record class name from input
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
                api.getClassList(view, getUid(), userType);
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
     * Set UID as int.
     * @param receivedInteger int number for setting UID
     */
    public void setUid(int receivedInteger)
    {
        uid = receivedInteger;
    }

    /**
     * Get UID.
     * @return Return UID as int
     */
    public int getUid(){
        return uid;
    }


    public void setUserType(String inputUserType){
        userType = inputUserType;
    }

    /**
     * Get user type.
     * @return Return user type as String
     */
    public String getUserType(){
        return userType;
    }


}

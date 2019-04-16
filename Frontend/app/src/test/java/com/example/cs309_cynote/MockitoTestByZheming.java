package com.example.cs309_cynote;


import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.internal.Asserts;
import com.example.cs309_cynote.Login;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author Zheming Fan
 */
public class MockitoTestByZheming {


    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void CIDInAddUserToClass(){

        AddUserToClass test2 = new AddUserToClass();
        test2.SetCid(4);

        Assert.assertEquals(4, test2.getCid());

    }

    @Test
    public void EmailValidInLogin(){

        Login testLogin = new Login();

        String emailTest = "test@gmail.com";
        Assert.assertEquals(true, testLogin.isEmailValid(emailTest));
    }

    @Test
    public void LoginFail(){

        Login test = new Login();

        String x = "testname";
        String y = "testpss";
        String url = "http://cs309-sd-7.misc.iastate.edu:8080/userLogin";

        String url1 = url + "/" + x + "/" +y;

        String w = test.oof(url, x, y);
        Assert.assertEquals(url1, w);

        String w2 = test.oof(url, null, y);
        Assert.assertEquals(null, w2);

        String w3 = test.oof(url, x, null);
        Assert.assertEquals(null, w3);
    }
}
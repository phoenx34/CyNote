package com.example.cs309_cynote;


import com.google.android.gms.common.internal.Asserts;
import com.example.cs309_cynote.Login;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MockitoTestByZheming {


    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void UIDInAddUserToClass(){

    }

    @Test
    public void EmailValidInLogin(){

        Login testLogin = new Login();

        String emailTest = "test@gmail.com";
        Assert.assertEquals(true, testLogin.isEmailValid1(emailTest));
    }

    @Test
    public void LoginFail(){

    }
}

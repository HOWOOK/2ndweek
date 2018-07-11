package com.example.q.a2ndweek;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public class WebService extends AsyncTask<String, Void, String> {

    Fragment Fragment;
    WebserviceResponseListner listener = null;
    String METHOD_NAME = "";
    Tab1Contacts.AddEmployeeRequest mAddEmployeePojo;

    public WebService(Fragment Fragment, WebserviceResponseListner listner, String methodName) {
        this.Fragment = Fragment;
        this.METHOD_NAME = methodName;
        this.listener = listner;
    }

    public WebService(Fragment Fragment, WebserviceResponseListner listner, Tab1Contacts.AddEmployeeRequest mAddEmployeePojo, String methodName) {
        this.Fragment = Fragment;
        this.METHOD_NAME = methodName;
        this.listener = listner;
        this.mAddEmployeePojo = mAddEmployeePojo;
    }


    @Override
    protected String doInBackground(String... strings) {

        switch (METHOD_NAME) {
            case "hello":
                HelloNode();
                break;
            case "addEmployee":
                AddEmployee();
                break;
            case "listEmployees":
                ListingEmployees();
                break;
            case "showEmpolyess":
                ShowEmpolyee();
                break;
        }
        return null;
    }

    private void HelloNode() {
        RestClient.get().HelloNode(new RestCallback<Tab1Contacts.HelloNodeResponse>() {

            @Override
            public void success(Tab1Contacts.HelloNodeResponse updateProfileResponsePojo, Response response) {
                Log.e("Successful", response.toString() + "/" + response.getUrl());
                listener.OnResponse(updateProfileResponsePojo, false, "hello");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Url", error.toString() + "/" + error.getUrl());
                listener.OnResponse(new Object(), true, "hello");
            }
        });
    }

    private void AddEmployee() {
        RestClient.get().AddEmployee(mAddEmployeePojo, new RestCallback<Tab1Contacts.AddEmployeeResponse>() {

            @Override
            public void success(Tab1Contacts.AddEmployeeResponse mAddEmployeePojo, Response response) {
                Log.e("Success AddEmployee", response.toString() + "/" + response.getUrl());
                listener.OnResponse(mAddEmployeePojo, false,
                        "addEmployee");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Url", error.toString() + "/" + error.getUrl());
                listener.OnResponse(new Object(), true, "addEmployee");
            }
        });
    }

    private void ListingEmployees() {

        RestClient.get().ListEmployees(new RestCallback<Tab1Contacts.ListEmployeesResponse>() {
            @Override
            public void success(Tab1Contacts.ListEmployeesResponse updateProfileResponsePojo, Response response) {
                Log.e("listEmployee", response.toString() + "/" + response.getUrl());
                listener.OnResponse(updateProfileResponsePojo, false, "listEmployees");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Url", error.toString() + "/" + error.getUrl());
                listener.OnResponse(new Object(), true, "listEmployees");
            }
        });
    }

    private void ShowEmpolyee(){

        RestClient.get().ShowEmpolyee(new RestCallback<Tab1Contacts.ShowEmployeesResponse>() {
            @Override
            public void success(Tab1Contacts.ShowEmployeesResponse showEmployeesResponse, Response response) {
                Log.e("showEmoplyee", response.toString() + "/" + response.getUrl());
                listener.OnResponse(showEmployeesResponse, false, "showEmpolyee");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Url", error.toString() + "/" + error.getUrl());
                listener.OnResponse(new Object(), true, "showEmpolyee");

            }
        });
    }

    public interface WebserviceResponseListner {

        public void OnResponse(Object response, boolean flagToCheckFailure, String webServiceName);

    }

    public interface ApiMethods {

        @GET("/hello")
        void HelloNode(RestCallback<Tab1Contacts.HelloNodeResponse> restCallback);

        @POST("/addEmployee")
        void AddEmployee(@Body Tab1Contacts.AddEmployeeRequest shareRequest, RestCallback<Tab1Contacts.AddEmployeeResponse> restCallback);

        @GET("/listEmployees")
        void ListEmployees(RestCallback<Tab1Contacts.ListEmployeesResponse> restCallback);

        @GET("/showEmpolyee")
        void ShowEmpolyee(RestCallback<Tab1Contacts.ShowEmployeesResponse> restCallback);

    }


    public abstract class RestCallback<T> implements Callback<T> {



    }

}

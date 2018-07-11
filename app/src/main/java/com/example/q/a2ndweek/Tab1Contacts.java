package com.example.q.a2ndweek;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Tab1Contacts extends Fragment implements WebService.WebserviceResponseListner{

    Button btn_add;
    Dialog dialog;
    ListView lv_main;
    ArrayList<String> arrayEmployeeName;

    ArrayList<ListviewItem> ememem =new ArrayList<ListviewItem>() ;
    ListviewAdapter adpater = new ListviewAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tap1contacts, container, false);

        btn_add = (Button) rootView.findViewById(R.id.btn_add);
        lv_main = (ListView) rootView.findViewById(R.id.lv_main);
        dialog = new Dialog(getContext());
        lv_main.setAdapter(adpater);


        new WebService(Tab1Contacts.this, (WebService.WebserviceResponseListner) Tab1Contacts.this,
                "listEmployees").execute();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.dialog_insert_employee);

                final EditText edt_employee_name = (EditText) dialog.findViewById(R.id.edt_employee_name);
                final EditText edt_employee_city = (EditText) dialog.findViewById(R.id.edt_employee_city);
                final EditText edt_employee_age = (EditText) dialog.findViewById(R.id.edt_employee_age);

                Button btn_insert = (Button) dialog.findViewById(R.id.btn_insert);

                btn_insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            AddEmployeeRequest addEmployeeRequest = new AddEmployeeRequest(edt_employee_name.getText().toString(), edt_employee_city.getText().toString(), edt_employee_age.getText().toString());

                            new WebService(Tab1Contacts.this, (WebService.WebserviceResponseListner) Tab1Contacts.this, addEmployeeRequest,
                                    "addEmployee").execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });

        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int postion, long id) {
                try{
                    final TextView del_employee_name= v.findViewById(R.id.emName);
                    final TextView del_employee_city= v.findViewById(R.id.emCity);
                    final TextView del_employee_age =v.findViewById(R.id.emAge);

                    RemoveRq removeRq = new RemoveRq(del_employee_name.getText().toString(), del_employee_city.getText().toString(), del_employee_age.getText().toString());


                    new WebService(Tab1Contacts.this, (WebService.WebserviceResponseListner) Tab1Contacts.this,
                            "remove").execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        return rootView;
    }


    public class HelloNodeResponse {

        String status, message;

        public HelloNodeResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    // Inserting the employee details
    public class AddEmployeeRequest {

        String name;
        String city;
        String age;

        public AddEmployeeRequest(String name, String city, String age) {
            this.name = name;
            this.city = city;
            this.age = age;
        }
    }

    public class RemoveRq {

        String name;
        String city;
        String age;

        public RemoveRq(String name, String city, String age) {
            this.name = name;
            this.city = city;
            this.age = age;
        }
    }

    public class AddEmployeeResponse {

        String success;

        public AddEmployeeResponse(String success) {
            this.success = success;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }
    }

    // Listing of employees
    public class ListEmployeesResponse {

        ArrayList<employee> employee;

        public ListEmployeesResponse(ArrayList<ListEmployeesResponse.employee> employee) {
            this.employee = employee;
        }

        public ArrayList<ListEmployeesResponse.employee> getEmployee() {
            return employee;
        }

        public void setEmployee(ArrayList<ListEmployeesResponse.employee> employee) {
            this.employee = employee;
        }

        public class employee {

            String name, city, age;

            public employee(String name, String city, String age) {
                this.name = name;
                this.city = city;
                this.age = age;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }
        }
    }


    public class Remove {

        String success;

        public Remove(String success) {
            this.success = success;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

    }


    @Override
    public void OnResponse(Object response, boolean flagToCheckFailure, String webServiceName) {

        if (webServiceName.equalsIgnoreCase("hello")) {

            if (!flagToCheckFailure) {
                HelloNodeResponse data = (HelloNodeResponse) response;

            } else {
                Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
            }
        } else if (webServiceName.equalsIgnoreCase("addEmployee")) {
            if (!flagToCheckFailure) {

                AddEmployeeResponse data = (AddEmployeeResponse) response;

                if (data.getSuccess().equalsIgnoreCase("true")) {
                    Toast.makeText(getContext(), "Inserted Successfully!!", Toast.LENGTH_LONG).show();

                    new WebService(Tab1Contacts.this, (WebService.WebserviceResponseListner) Tab1Contacts.this,
                            "listEmployees").execute();

                } else {
                    Toast.makeText(getContext(), "Insert Failed", Toast.LENGTH_LONG).show();
                }

                dialog.dismiss();
            } else {
                Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        } else if (webServiceName.equalsIgnoreCase("listEmployees")) {

            if (!flagToCheckFailure) {
                ListEmployeesResponse data = (ListEmployeesResponse) response;

                //arrayEmployeeName.clear();
                ememem.clear();
                for (int i = 0; i < data.getEmployee().size(); i++) {
                    String emName=data.getEmployee().get(i).getName().toString();
                    String emCity=data.getEmployee().get(i).getCity().toString();
                    String emAge=data.getEmployee().get(i).getAge().toString();

                    ListviewItem dddd = new ListviewItem(emName, emCity, emAge);
                    //arrayEmployeeName.add(data.getEmployee().get(i).getName());
                    System.out.println("이름은 : "+data.getEmployee().get(i).getName().toString() );
                    System.out.println("도시는 : "+data.getEmployee().get(i).getCity().toString() );
                    System.out.println("나이는 : "+data.getEmployee().get(i).getAge().toString() );

                    ememem.add(dddd);
                }
                adpater.notifyDataSetChanged();
            } else if(webServiceName.equalsIgnoreCase("remove")){

                Remove data = (Remove) response;

                if (!flagToCheckFailure) {
                    Toast.makeText(getContext(), "Empolyee delete", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
                }
                //TODO
            } else {
                Toast.makeText(getContext(), "Something went Wrong", Toast.LENGTH_LONG).show();
            }

        }
    }

    public class ListviewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ememem.size();
        }

        @Override
        public Object getItem(int position) {
            return ememem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.single_iv, parent, false);
            String name = ememem.get(position).emName;
            String city = ememem.get(position).emCity;
            String age = ememem.get(position).emAge;

            TextView tv_name = convertView.findViewById(R.id.emName);
            TextView tv_city = convertView.findViewById(R.id.emCity);
            TextView tv_age = convertView.findViewById(R.id.emAge);

            tv_name.setText(name);
            tv_city.setText(city);
            tv_age.setText(age);
            //ListviewItem lv_item = arrayEm;


            return convertView;
        }
    }

    public class ListviewItem {
        private String emName;
        private String emCity;
        private String emAge;

        public String getEmName(){return emName;}
        public String getEmCity(){return emCity;}
        public String getEmAge(){return emAge;}
        public String setEmName(String a){return this.emName = a;}
        public String setEmCity(String a){return this.emCity = a;}
        public String setEmAge(String a){return this.emAge = a;}
        public ListviewItem(String emName,String emCity, String emAge){
            this.emName = emName;
            this.emCity= emCity;
            this.emAge= emAge;
        }
    }
}

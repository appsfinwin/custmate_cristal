package com.finwin.cristal.custmate.home;

import androidx.fragment.app.Fragment;

public class LoanFragmentDetails extends Fragment {

//   /* String [] particular={"Principal Amount","Interest","Penal Interest","Other Charges"};
//    int [] total_receivable={800000,289689,50002,200};
//    int [] total_received={265043,273494,4021,0};
//    int [] balance={534957,16195,981,200};
//    int [] overdue={188290,16195,981,200};
//    int [] current_receipt={};*/
//
//    private TableLayout alert_fixedColumn, alert_header;
//    private TableLayout alert_scrollablePart;
//    private TableRow row;
//    private TextView fixedView;
//    private TextView textViewAmounts;
//
//    private Button acc_submit_btn, remitance_submit_btn;
//    private EditText loan_no_editText, remitance_amnt_edit_text;
//    private ImageButton loan_no_search_img_btn, cancel_btn;
//    private TextView mem_no_textView, cus_no_textView, name_textView, date_of_loan_textView, interest_textView,
//            loan_amount_textView, loan_no_textView, loan_type_textView, period_textView;
//    private LinearLayout loan_deatils_linear, loan_remitance_linear, loan_table_view;
//    private String remitance_amnt, loan_acc_no;
//    LayoutInflater finalInflater;
//    int[] b;
//    SweetAlertDialog progressDialog;
//    JSONParser jsonParser = new JSONParser();
//    private String smem_no, scust_no, sname, sloan_no, sloan_date, sinterest_rate, sloan_amnt, sloan_type, sloan_period;
//    public String[] particularArray, receiptArray,
//            StrTotalRecvd, StrBalance, StrOverdue, StrCurrentRecpt;
//    public int[] intBalance;
//    //    public int[] receivableArray, receivedArray, balanceArray, overdueArray;
//    private String error = "error";
//    RelativeLayout loan_main_relative;
//    View rootView;
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_loan, container, false);
//        progressDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
//        finalInflater = getLayoutInflater(null);
//
//        acc_submit_btn = (Button) rootView.findViewById(R.id.loan_no_submit_btn);
//        remitance_submit_btn = (Button) rootView.findViewById(R.id.loan_remitance_amnt_submit_btn);
//
//        loan_no_editText = (EditText) rootView.findViewById(R.id.loan_no_edit_text);
//        remitance_amnt_edit_text = (EditText) rootView.findViewById(R.id.loan_remitance_amount_editText);
//
//        loan_no_search_img_btn = (ImageButton) rootView.findViewById(R.id.loan_no_search_img_btn);
//        cancel_btn = (ImageButton) rootView.findViewById(R.id.loan_CANCELbtn);
//
//        mem_no_textView = (TextView) rootView.findViewById(R.id.loan_member_no_textview);
//        cus_no_textView = (TextView) rootView.findViewById(R.id.loan_cus_no_textview);
//        name_textView = (TextView) rootView.findViewById(R.id.loan_name_textview);
//        date_of_loan_textView = (TextView) rootView.findViewById(R.id.loan_date_textview);
//        interest_textView = (TextView) rootView.findViewById(R.id.loan_interest_textview);
//        loan_amount_textView = (TextView) rootView.findViewById(R.id.loan_amount_textview);
//        loan_no_textView = (TextView) rootView.findViewById(R.id.loan_loan_no_textview);
//        loan_type_textView = (TextView) rootView.findViewById(R.id.loan_type_textview);
//        period_textView = (TextView) rootView.findViewById(R.id.loan_period_textview);
//
//        loan_deatils_linear = (LinearLayout) rootView.findViewById(R.id.linear_search_deposit);
//        loan_remitance_linear = (LinearLayout) rootView.findViewById(R.id.linear_amount_deposit);
//        loan_table_view = (LinearLayout) rootView.findViewById(R.id.loan_table_view);
//        loan_table_view.setVisibility(GONE);
//        loan_deatils_linear.setVisibility(View.GONE);
//        loan_remitance_linear.setVisibility(View.GONE);
//
//        acc_submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loan_acc_no = loan_no_editText.getText().toString();
//                new getLoanAccountHolder().execute();
//                /*   setCustomerDeatils();
//                 */
//            }
//        });
//
//        loan_no_search_img_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent debit_intent = new Intent(getActivity(), Loan_Number_Search.class);
//                startActivityForResult(debit_intent, 1004);
//            }
//        });
//        cancel_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                fm.popBackStack();
//            }
//        });
//
//
//        remitance_submit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remitance_amnt = remitance_amnt_edit_text.getText().toString();
//                int balance_amt = Integer.parseInt(remitance_amnt);
////                int bal_length = balanceArray.length;
//                int bal_length = intBalance.length;
//                b = new int[bal_length];
//                for (int i = bal_length - 1; i >= 0; i--) {
//
//                    if (balance_amt >= intBalance[i]) {
//                        b[i] = intBalance[i];
//                        balance_amt = balance_amt - intBalance[i];
//                    } else if (balance_amt > 0) {
//                        b[i] = balance_amt;
//                        balance_amt = balance_amt - b[i];
//                    }
//
//                }
//
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
//                View dialogView = finalInflater.inflate(R.layout.alert_loan_deposit_layout, null);
//
//                alert_fixedColumn = (TableLayout) dialogView.findViewById(R.id.alert_fixed_column);
//                alert_scrollablePart = (TableLayout) dialogView.findViewById(R.id.alert_scrollable_part);
//                alert_header = (TableLayout) dialogView.findViewById(R.id.alert_table_header);
//
//                createTable();
//                TextView amount = (TextView) dialogView.findViewById(R.id.remitance_amount_alert_textView);
//                amount.setText(remitance_amnt_edit_text.getText().toString());
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.setNegativeButton("CANCEL", null);
//                dialogBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (connectionStatus) {
//                            new submitLoan().execute();
//                        } else {
//                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
//                                    .setTitleText("BT Device Not Connected")
//                                    .setContentText("please connect the device")
//                                    .setCancelText("NO")
//                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                        @Override
//                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                            startActivity(new Intent(getContext(), Act_Main.class));
//                                            sweetAlertDialog.dismiss();
//                                        }
//                                    }).show();
//                        }
//                    }
//                });
//                AlertDialog alertDialog = dialogBuilder.create();
//                alertDialog.show();
//            }
//        });
//
//
//        return rootView;
//    }
//
//
//
//    //util method
//
//    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
//        TextView recyclableTextView;
//        int screenWidth = getResources().getDisplayMetrics().widthPixels;
//        recyclableTextView = new TextView(getContext());
//        recyclableTextView.setText(text);
//        recyclableTextView.setTextColor(Color.BLACK);
//        recyclableTextView.setGravity(Gravity.CENTER_VERTICAL);
//        recyclableTextView.setTextSize(20);
//        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
//        recyclableTextView.setHeight(fixedHeightInPixels);
//        return recyclableTextView;
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    public void createTable() {
//        TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        int[] fixedColumnWidths = new int[]{20, 20, 20, 20, 20};
//        int[] scrollableColumnWidths = new int[]{20, 20, 20, 20, 20};
//        int fixedRowHeight = 50;
//        int fixedHeaderHeight = 60;
//
//        int length = particularArray.length;
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins(2, 5, 2, 5);
//        for (int i = 0; i < length; i++) {
//            fixedView = makeTableRowWithText(particularArray[i], 35, fixedRowHeight);
//            fixedView.setTextSize(18);
//            fixedView.setGravity(Gravity.CENTER_VERTICAL);
//            fixedView.setLayoutParams(params);
//            fixedView.setTextColor(Color.BLACK);
//            alert_fixedColumn.addView(fixedView);
//            row = new TableRow(getContext());
//            row.setLayoutParams(wrapWrapTableRowParams);
//            row.setGravity(Gravity.CENTER_VERTICAL);
//            row.setGravity(Gravity.CENTER);
//            row.setBackgroundColor(Color.WHITE);
//            textViewAmounts = makeTableRowWithText(" : " + String.valueOf(b[i]), scrollableColumnWidths[4], fixedRowHeight);
//            textViewAmounts.setPadding(5, 0, 0, 0);
//            textViewAmounts.setTextColor(Color.RED);
//            row.addView(textViewAmounts);
//            alert_scrollablePart.addView(row);
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1004 && resultCode == RESULT_OK) {
//            try {
//                String result_acc_no = data.getStringExtra("result_acc_no");
//                loan_no_editText.setText(result_acc_no);
//                loan_acc_no = result_acc_no;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (requestCode == 1004 && resultCode == RESULT_CANCELED) {
//            loan_deatils_linear.setVisibility(GONE);
//            loan_table_view.setVisibility(GONE);
//            loan_remitance_linear.setVisibility(GONE);
//            loan_no_editText.setText(null);
//            remitance_amnt_edit_text.setText(null);
//        }
//        if (requestCode == 1005 && resultCode == RESULT_CANCELED) {
//            loan_deatils_linear.setVisibility(GONE);
//            loan_table_view.setVisibility(GONE);
//            loan_remitance_linear.setVisibility(GONE);
//            loan_no_editText.setText(null);
//            remitance_amnt_edit_text.setText(null);
//        }
//    }
//
//    private class getLoanAccountHolder extends AsyncTask<String, String, String> {
//        private String api_url = ip_global + "/getLoanAccountHolder";
//
//        @Override
//        protected void onPreExecute() {
//            if (loan_acc_no.equals("")) {
//                loan_acc_no = loan_no_editText.getText().toString();
//            } else {
//            }
//            progressDialog.setTitleText("Loading");
//            progressDialog.setContentText("please wait..");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("account_no", loan_acc_no);
//            try {
////                JSONObject data = jsonParser.makeHttpRequest(api_url, "POST", hashMap);
////                JSONObject jobj = data.getJSONObject("accountDetails");
////                JSONObject jobj1 = jobj.getJSONObject("accounts");
////                smem_no = jobj1.getString("MEMBER_NO");
////                scust_no = jobj1.getString("CUST_NO");
////                sname = jobj1.getString("NAME");
////                sloan_no = jobj1.getString("LOAN_NO");
////                sloan_date = jobj1.getString("LOAN_DATE");
////                sinterest_rate = jobj1.getString("INTEREST_RATE");
////                sloan_amnt = jobj1.getString("LOAN_AMOUNT");
////                sloan_type = jobj1.getString("LOAN_TYPE");
////                sloan_period = jobj1.getString("LOAN_PERIOD");
//
//                JSONObject jobj1 = jsonParser.makeHttpRequest(api_url, "POST", hashMap);
////                JSONObject jobj = data.getJSONObject("AccountDetails");
////                JSONObject jobj1 = jobj.getJSONObject("Accounts");
////                "ACNO"
//                smem_no = jobj1.getString("Member_No");
//                scust_no = jobj1.getString("CUST_NO");
//                sname = jobj1.getString("Name");
//                sloan_no = jobj1.getString("LoanNo");
//                sloan_date = jobj1.getString("LoanDate");
//                sinterest_rate = jobj1.getString("InterestRate");
//                sloan_amnt = jobj1.getString("LoanAmount");
//                sloan_type = jobj1.getString("LoanType");
//                sloan_period = jobj1.getString("LoanPeriod");
//
////                JSONArray jobj = jobj1.getJSONArray("Receiptdetails");
//
//                JSONArray recArray = jobj1.getJSONArray("Receiptdetails");
//                int arCount = recArray.length();
//                particularArray = new String[arCount];
//                StrTotalRecvd = new String[arCount];
//                intBalance = new int[arCount];
//                StrOverdue = new String[arCount];
//                StrCurrentRecpt = new String[arCount];
//                for (int j = 0; j < recArray.length(); j++) {
//                    JSONObject ingredObject = recArray.getJSONObject(j);
//                    particularArray[j] = ingredObject.getString("Particular");
//                    StrTotalRecvd[j] = ingredObject.getString("Total Received");
//                    intBalance[j] = Integer.parseInt(ingredObject.getString("Balance"));
//                    StrOverdue[j] = ingredObject.getString("Overdue");
//                    StrCurrentRecpt[j] = ingredObject.getString("Current Receipt");
////                    Log.e("name", ingName);
//                }
//
//
//
////                JSONArray JArrayparticular = jobj.getJSONArray("particular");
////                int particularlength = JArrayparticular.length();
////                particularArray = new String[particularlength];
////                for (int i = 0; i < particularlength; i++) {
////                    JSONObject arrayObject = JArrayparticular.getJSONObject(i);
////                    particularArray[i] = arrayObject.getString("1");
////                }
////                JSONArray JArrayreceivable = jobj.getJSONArray("receivable");
////                int receivablelength = JArrayreceivable.length();
////                receivableArray = new int[receivablelength];
////                for (int i = 0; i < receivablelength; i++) {
////                    JSONObject arrayObject = JArrayreceivable.getJSONObject(i);
////                    receivableArray[i] = Integer.parseInt(arrayObject.getString("1"));
////                }
////                JSONArray JArrayreceived = jobj.getJSONArray("received");
////                int receivedlength = JArrayreceived.length();
////                receivedArray = new int[receivedlength];
////                for (int i = 0; i < receivedlength; i++) {
////                    JSONObject arrayObject = JArrayreceived.getJSONObject(i);
////                    receivedArray[i] = Integer.parseInt(arrayObject.getString("1"));
////                }
////                JSONArray JArraybalance = jobj.getJSONArray("balance");
////                int balancelength = JArraybalance.length();
////                balanceArray = new int[balancelength];
////                for (int i = 0; i < balancelength; i++) {
////                    JSONObject arrayObject = JArraybalance.getJSONObject(i);
////                    balanceArray[i] = Integer.parseInt(arrayObject.getString("1"));
////                }
////                JSONArray JArrayoverdue = jobj.getJSONArray("overdue");
////                int overduelength = JArrayoverdue.length();
////                overdueArray = new int[overduelength];
////                for (int i = 0; i < overduelength; i++) {
////                    JSONObject arrayObject = JArrayoverdue.getJSONObject(i);
////                    overdueArray[i] = Integer.parseInt(arrayObject.getString("1"));
////                }
//
//                error = "ok";
//            } catch (Exception e) {
//                e.printStackTrace();
//                error = "error";
//                return error;
//            }
//            return error;
//        }
//
//        @Override
//        protected void onPostExecute(String url) {
//            progressDialog.dismiss();
//            if (url.equals("error")) {
//                Toast.makeText(getContext(), "Some error Occurred!", Toast.LENGTH_SHORT).show();
//            } else {
//                /*AES aes=new AES();
//                try {
//                    String decrypt=openFileToString(aes.decrypt(sloan_no));
//                    Toast.makeText(getContext(),decrypt, Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }*/
//                setCustomerDeatils();
//                loan_deatils_linear.setVisibility(VISIBLE);
//                CreateTable();
//                loan_deatils_linear.setVisibility(View.VISIBLE);
//                loan_remitance_linear.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    public void setCustomerDeatils() {
//        mem_no_textView.setText(smem_no);
//        cus_no_textView.setText(scust_no);
//        name_textView.setText(sname);
//        date_of_loan_textView.setText(sloan_date);
//        interest_textView.setText(sinterest_rate);
//        loan_amount_textView.setText(sloan_amnt);
//        loan_no_textView.setText(sloan_no);
//        loan_type_textView.setText(sloan_type);
//        period_textView.setText(sloan_period);
//    }
//
//    private void CreateTable() {
//
//        TableLayout header = (TableLayout) rootView.findViewById(R.id.table_header);
//        TableLayout scrollablePart = (TableLayout) rootView.findViewById(R.id.scrollable_part);
//        TableLayout fixedColumn = (TableLayout) rootView.findViewById(R.id.fixed_column);
//        TableRow header_row = new TableRow(getContext());
//
//        TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        int[] fixedColumnWidths = new int[]{20, 20, 20, 20, 20};
//        int[] scrollableColumnWidths = new int[]{20, 20, 20, 20, 20};
//        int fixedRowHeight = 50;
//        int fixedHeaderHeight = 60;
//        loan_table_view.setVisibility(VISIBLE);
//        //header (fixed vertically)
//
//        TextView textView1 = makeTableRowWithText("Particular", fixedColumnWidths[0], fixedHeaderHeight);
//        textView1.setTextColor(Color.BLACK);
//        TextView textView2 = makeTableRowWithText("Total Receivable", fixedColumnWidths[0], fixedHeaderHeight);
//        textView2.setTextColor(Color.BLACK);
//        TextView textView3 = makeTableRowWithText("Total Received", fixedColumnWidths[0], fixedHeaderHeight);
//        textView3.setTextColor(Color.BLACK);
//        TextView textView4 = makeTableRowWithText("Balance", fixedColumnWidths[0], fixedHeaderHeight);
//        textView4.setTextColor(Color.BLACK);
//        TextView textView5 = makeTableRowWithText("Overdue", fixedColumnWidths[0], fixedHeaderHeight);
//        textView5.setTextColor(Color.BLACK);
//        header_row.removeAllViews();
//        header_row.setLayoutParams(wrapWrapTableRowParams);
//        header_row.setGravity(Gravity.CENTER_VERTICAL);
//        header_row.setGravity(Gravity.CENTER);
//        header_row.addView(textView1);
//        header_row.addView(textView2);
//        header_row.addView(textView3);
//        header_row.addView(textView4);
//        header_row.addView(textView5);
//      /*  header_row.addView(makeTableRowWithText("Total Received", fixedColumnWidths[2], fixedHeaderHeight));
//        header_row.addView(makeTableRowWithText("Balance", fixedColumnWidths[3], fixedHeaderHeight));
//        header_row.addView(makeTableRowWithText("Overdue", fixedColumnWidths[4], fixedHeaderHeight));*/
//
//        header.removeAllViews();
//        header.setVisibility(GONE);
//        header.setVisibility(VISIBLE);
//        header.addView(header_row);
//        //header (fixed horizontally)
//
//        int length = particularArray.length;
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins(2, 5, 2, 5);
//
//        fixedColumn.removeAllViews();
//        scrollablePart.removeAllViews();
//
//        for (int i = 0; i < length; i++) {
//            TextView fixedView = makeTableRowWithText(particularArray[i], scrollableColumnWidths[0], fixedRowHeight);
//            fixedView.setTextSize(18);
//            fixedView.setGravity(Gravity.CENTER_VERTICAL);
//            fixedView.setBackgroundResource(R.drawable.cell_shape);
//            fixedView.setLayoutParams(params);
//            fixedView.setTextColor(Color.WHITE);
//
//            fixedColumn.setVisibility(GONE);
//            fixedColumn.setVisibility(VISIBLE);
//            fixedColumn.addView(fixedView);
//
//            TableRow row = new TableRow(getContext());
//
//            row.setVisibility(GONE);
//            row.setVisibility(VISIBLE);
//
//            row.setLayoutParams(wrapWrapTableRowParams);
//            row.setGravity(Gravity.CENTER_VERTICAL);
//            row.setGravity(Gravity.CENTER);
//            row.setBackgroundColor(Color.WHITE);
//            TextView receivableTextView = makeTableRowWithText(String.valueOf(particularArray[i]), scrollableColumnWidths[1], fixedRowHeight);
//            receivableTextView.setPadding(5, 0, 0, 0);
//            receivableTextView.setBackgroundResource(R.drawable.cell_shape_column);
//
//            TextView total_textView = makeTableRowWithText(StrTotalRecvd[i], scrollableColumnWidths[2], fixedRowHeight);
//            total_textView.setBackgroundResource(R.drawable.cell_shape_column);
//
//            TextView balance_textView = makeTableRowWithText(String.valueOf(intBalance[i]), scrollableColumnWidths[3], fixedRowHeight);
//            balance_textView.setBackgroundResource(R.drawable.cell_shape_column);
//
//            TextView textView = makeTableRowWithText(StrOverdue[i], scrollableColumnWidths[4], fixedRowHeight);
//            textView.setBackgroundResource(R.drawable.cell_shape_column);
//
//            textView.setTextColor(Color.RED);
//            row.addView(receivableTextView);
//            row.addView(total_textView);
//            row.addView(balance_textView);
//            row.addView(textView);
//
//
//            scrollablePart.setVisibility(GONE);
//            scrollablePart.setVisibility(VISIBLE);
//            scrollablePart.addView(row);
//        }
//    }
//
//    private class submitLoan extends AsyncTask<String, String, String> {
//
//        private String api_url = ip_global + "/LoanReceiptCash";
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog.setTitleText("Loading");
//            progressDialog.setContentText("please wait..");
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            HashMap<String, String> hashMap = new HashMap<>();
//            hashMap.put("account_no", loan_acc_no);
//            hashMap.put("deposit_amount", remitance_amnt);
//            try {
//                JSONObject data = jsonParser.makeHttpRequest(api_url, "POST", hashMap);
//                JSONObject jobj = data.getJSONObject("receipt");
//                JSONArray dataArray = jobj.getJSONArray("data");
//                receiptArray = new String[dataArray.length()];
//                for (int i = 0; i < dataArray.length(); i++) {
//                    JSONObject receiptObject = dataArray.getJSONObject(i);
//                    receiptArray[i] = receiptObject.getString("1");
//                    error = "ok";
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                error = "error";
//                return error;
//            }
//            return error;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            progressDialog.dismiss();
//            if (result.equals("error")) {
//                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("Error")
//                        .setContentText("Some error occured!")
//                        .show();
//            }
//            if (result.equals("ok")) {
//                for (int i = 0; i < receiptArray.length; i++) {
//                    Log.e("receiptArray => ", receiptArray[i]);
//                }
//
//                Intent intent = new Intent(getActivity(), Receipt_Loan.class);
//                intent.putExtra("member_no", smem_no);
//                intent.putExtra("customer_no", sloan_no);
//                intent.putExtra("name", sname);
//                intent.putExtra("receiptArray", receiptArray);
//                startActivityForResult(intent, 1005);
//            }
//        }
//    }
//




}

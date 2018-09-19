package top.palexu.shanxuntest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final int MessageToast = 0;
    public static final int MessageConfig = 1;

    private String username = null;
    private String password = null;
    private String routerPass = null;

    private EditText usernameEditText = null;
    private EditText passwordEditText = null;
    private EditText routerPassEditText = null;


    Button button1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSharedPreferences();

        button1=(Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        routerPassEditText = (EditText) findViewById(R.id.routePass);

        usernameEditText.setText(this.username);
        passwordEditText.setText(this.password);
        routerPassEditText.setText(this.routerPass);


    }

    private void saveSharedPreferences(String username,String password,String routerPass){
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("routerPass",routerPass);
        editor.commit();
        Log.d("save","saveSharedPreferences:\nusername:"+username+"\npassword:"+password+"\nrouterPass:"+routerPass);
    }

    private void getSharedPreferences(){
        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        this.username = pref.getString("username","@GDPF.XY");
        this.password = pref.getString("password","");
        this.routerPass = pref.getString("routerPass","admin");
        Log.d("read","SharedPreferences:\nusername:"+username+"\npassword:"+password+"\nrouterPass:"+routerPass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveSharedPreferences(this.username,this.password,this.routerPass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
//                        String username = "15381126745@GDPF.XY";
//                        String password = "731097";
//                        String routerPass = "624520";
                        username = usernameEditText.getText().toString();
                        password = passwordEditText.getText().toString();
                        routerPass = routerPassEditText.getText().toString();
                        saveSharedPreferences(username,password,routerPass);
                        Log.d("userDetail","username:"+username);
                        Log.d("userDetail","password:"+password);
                        Log.d("userDetail","routerPass:"+routerPass);
                        try {
                            Log.d("send","start send msg");
                            Shanxun sx = new Shanxun(username,password,routerPass);
                            sx.send();
                            Message ms=mHandler.obtainMessage();
                            ms.what=MessageToast;
                            ms.obj = "连接路由器中";
                            mHandler.sendMessage(ms);

                            Thread.sleep(1500);
                            String visitUrl = "http://ip.6655.com/ip.aspx?area=1";
                            String result = Tools.HttpGet(visitUrl);
                            Message mss = mHandler.obtainMessage();
                            mss.what = MessageToast;
                            mss.obj = result;
                            mHandler.sendMessage(mss);
                        } catch (Exception e) {
                            Log.e("setRouteError",e.getMessage());
                            Message ms=mHandler.obtainMessage();
                            ms.what=MessageToast;
                            ms.obj = "连接路由器失败";
                            mHandler.sendMessage(ms);
                        }

                    }
                };
                new Thread(runnable).start();
                break;
            default:
                break;
        }
    }

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageToast:
                    //String str1 = msg.getData().getString("toast");//接受msg传递过来的参数
                    String show=(String) msg.obj;
                    Toast.makeText(MainActivity.this, show , Toast.LENGTH_SHORT).show();
                    break;
                case MessageConfig:
                    Bundle bundle = msg.getData();
                    String key = bundle.getString("key");
                    String value = bundle.getString("value");
                    Log.d("changevalue",key+" "+value);
                    break;
                default:
                    break;
            }
        }

    };


}
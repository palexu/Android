package top.palexu.listviewtest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.palexu.listviewtest.adapter.FruitAdapter;
import top.palexu.listviewtest.bean.Fruit;

public class MainActivity extends Activity {
    private List<Fruit> fruitList = new ArrayList<Fruit>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits(); // 初始化水果数据
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initFruits() {
        Fruit apple = new Fruit("Apple", R.drawable.apple); fruitList.add(apple);
        Fruit banana = new Fruit("Banana", R.drawable.apple); fruitList.add(banana);
        Fruit orange = new Fruit("Orange", R.drawable.apple); fruitList.add(orange);
        Fruit watermelon = new Fruit("Watermelon", R.drawable.apple); fruitList.add(watermelon);
        Fruit pear = new Fruit("Pear", R.drawable.apple); fruitList.add(pear);
        Fruit grape = new Fruit("Grape", R.drawable.apple); fruitList.add(grape);
        Fruit pineapple = new Fruit("Pineapple", R.drawable.apple); fruitList.add(pineapple);
        Fruit strawberry = new Fruit("Strawberry", R.drawable.apple); fruitList.add(strawberry);
        Fruit cherry = new Fruit("Cherry", R.drawable.apple); fruitList.add(cherry);
        Fruit mango = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);
        Fruit mango1 = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);
        Fruit mango2 = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);
        Fruit mango3 = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);
        Fruit mango4 = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);
        Fruit mango5 = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);
        Fruit mango6 = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);
        Fruit mango7 = new Fruit("Mango", R.drawable.apple); fruitList.add(mango);


    }
}
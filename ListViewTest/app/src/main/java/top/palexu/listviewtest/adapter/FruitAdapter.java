    package top.palexu.listviewtest.adapter;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;

    import java.util.ArrayList;
    import java.util.List;

    import top.palexu.listviewtest.R;
    import top.palexu.listviewtest.bean.Fruit;

    /**
    * Created by xjy on 2016/11/5.
    */
    public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resourceId;
    public FruitAdapter(Context context, int textViewResourceId,
                        List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruit fruit = getItem(position); View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            /*
            *以下代码对listView进行了优化，判断是否重新渲染（？）以及加入缓存，优化了执行速度
            */
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = (TextView) view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageId()); viewHolder.fruitName.setText(fruit.getName());
        return view;
    }
        class ViewHolder {
            ImageView fruitImage;
            TextView fruitName;
        }

    }

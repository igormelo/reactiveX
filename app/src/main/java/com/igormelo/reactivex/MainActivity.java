package com.igormelo.reactivex;


import android.databinding.DataBindingUtil;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.igormelo.reactivex.adapter.UserAdapter;
import com.igormelo.reactivex.databinding.ActivityMainBinding;
import com.igormelo.reactivex.http.UsersParser;
import com.igormelo.reactivex.models.UserModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Observable;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        EventBus.getDefault().register(this);
    }
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            Observable<UserModel> result = UsersParser.searchByLogin(query);
            Subscription subscription = result.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> EventBus.getDefault().postSticky(userModel),
                            Throwable::printStackTrace,
                            () -> Log.d("oi", "complete")
                    );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(UserModel event){
        updateListView(event);
    }
    private void updateListView(UserModel userModel){
        ArrayList<UserModel> list = new ArrayList<>();
        list.add(userModel);
        binding.listUsers.setAdapter(new UserAdapter(this, list));
    }
}

package com.igormelo.reactivex.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.igormelo.reactivex.R;
import com.igormelo.reactivex.databinding.ItemUsersBinding;
import com.igormelo.reactivex.models.UserModel;

import java.util.List;

/**
 * Created by root on 25/11/16.
 */

public class UserAdapter extends ArrayAdapter<UserModel> {

    public UserAdapter(Context context, List<UserModel> users) {
        super(context, 0, users);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModel userModel = getItem(position);

        ItemUsersBinding binding;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.item_users, parent, false);
        } else {
            binding = DataBindingUtil.bind(convertView);
        }
        binding.setUsers(userModel);
        return binding.getRoot();
    }
}

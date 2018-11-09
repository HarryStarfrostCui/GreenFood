package com.example.hca127.greenfood.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hca127.greenfood.MainActivity;
import com.example.hca127.greenfood.R;
import com.example.hca127.greenfood.objects.LocalUser;

import java.util.ArrayList;

public class UserIconFragment extends Fragment implements View.OnClickListener {

    private LocalUser mLocalUser;

    private ArrayList<ImageView> mIcons;
    private int[] mIconIds = {
            R.id.icon_1, R.id.icon_2, R.id.icon_3,
            R.id.icon_4, R.id.icon_5, R.id.icon_6
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_icon, container, false);
        mLocalUser = ((MainActivity)getActivity()).getLocalUser();

        int[] mPictureIds = {
                R.drawable.tree, R.drawable.sunglasses, R.drawable.dog,
                R.drawable.cat, R.drawable.monkey, R.drawable.ghost
        };

        ImageView temp;
        for(int i = 0; i<mIconIds.length; i++){
            temp = view.findViewById(mIconIds[i]);
            mIcons.add(temp);
            mIcons.get(i).setImageResource(mPictureIds[i]);
            mIcons.get(i).setOnClickListener(this);
        }



        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        for(int i = 0; i<mIconIds.length; i++){
            if(id == mIconIds[i]){
                mLocalUser.setProfileIcon(i);
                ((MainActivity)getActivity()).setLocalUser(mLocalUser);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            }
        }

    }
}

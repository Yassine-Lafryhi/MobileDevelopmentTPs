package com.recyclerviewwithroom.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.recyclerviewwithroom.fragments.AddContact;
import com.recyclerviewwithroom.fragments.ContactsList;
import com.recyclerviewwithroom.fragments.UpdateContact;


public class Tabs extends FragmentPagerAdapter {
    public Tabs(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ContactsList();
            case 1:
                return new AddContact();
            case 2:
                return new UpdateContact();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {

        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "List";
            case 1:
                return "Add";
            case 2:
                return "Update";
            default:
                return null;
        }
    }
}

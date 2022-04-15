package com.recyclerviewwithroom.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.recyclerviewwithroom.R;
import com.recyclerviewwithroom.activities.Main;
import com.recyclerviewwithroom.adapters.ContactsAdapter;
import com.recyclerviewwithroom.daos.ContactDao;
import com.recyclerviewwithroom.entities.Contact;

import java.util.List;


public class ContactsList extends Fragment {
    private View view;
    public static RecyclerView recyclerView;
    public static ContactsAdapter adapter;
    public static List<Contact> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = view.findViewById(R.id.commands_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ContactDao dao = Main.database.contactDao();
        list = dao.getAll();
        adapter = new ContactsAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        return view;

    }
}

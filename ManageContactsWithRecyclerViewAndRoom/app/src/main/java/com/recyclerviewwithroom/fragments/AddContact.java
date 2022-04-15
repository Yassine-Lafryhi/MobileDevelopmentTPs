package com.recyclerviewwithroom.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.recyclerviewwithroom.R;
import com.recyclerviewwithroom.activities.Main;
import com.recyclerviewwithroom.daos.ContactDao;
import com.recyclerviewwithroom.entities.Contact;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddContact extends Fragment {
    private View view;
    private EditText firstNameEditText, lastNameEditText, phoneEditText, emailEditText, jobEditText;
    private FloatingActionButton add;
    public static CircleImageView photo;
    public static String photoUriPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        firstNameEditText = view.findViewById(R.id.firstName);
        lastNameEditText = view.findViewById(R.id.lastName);
        phoneEditText = view.findViewById(R.id.phone);
        emailEditText = view.findViewById(R.id.email);
        jobEditText = view.findViewById(R.id.job);
        photo = view.findViewById(R.id.photo);

        add = view.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewContact();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(getActivity());
            }
        });

        return view;
    }

    private void addNewContact() {
        if (!(TextUtils.isEmpty(firstNameEditText.getText().toString().trim()) || TextUtils.isEmpty(lastNameEditText.getText().toString().trim()) || TextUtils.isEmpty(emailEditText.getText().toString().trim()) || TextUtils.isEmpty(jobEditText.getText().toString().trim()) || TextUtils.isEmpty(phoneEditText.getText().toString().trim())) && photoUriPath != null) {
            Contact contact = new Contact();
            contact.setFirstName(firstNameEditText.getText().toString().trim());
            contact.setLastName(lastNameEditText.getText().toString().trim());
            contact.setEmail(emailEditText.getText().toString().trim());
            contact.setJob(jobEditText.getText().toString().trim());
            contact.setPhone(phoneEditText.getText().toString().trim());

            ContactDao dao = Main.database.contactDao();

            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyContacts";
            String photoFileName = emailEditText.getText().toString().split("@")[0] + ".jpg";
            File file = new File(rootPath, photoFileName);
            FileOutputStream stream;
            try {
                stream = new FileOutputStream(file);
                try {
                    byte[] bytesArray = new byte[(int) new File(photoUriPath).length()];

                    FileInputStream fileInputStream = new FileInputStream(new File(photoUriPath));
                    fileInputStream.read(bytesArray);
                    stream.write(bytesArray);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            contact.setPhoto(photoFileName);
            long id = dao.insert(contact);
            contact.setId((int) id);
            Snackbar.make(view, "The contact has been created successfully !", Snackbar.LENGTH_LONG).setAction("Okay", null).show();

            firstNameEditText.getText().clear();
            lastNameEditText.getText().clear();
            jobEditText.getText().clear();
            emailEditText.getText().clear();
            phoneEditText.getText().clear();

            Picasso.get().load(R.drawable.user).into(photo);

            TabLayout.Tab tab = Main.myTabLayout.getTabAt(0);
            tab.select();

            ContactsList.list.add(contact);
            ContactsList.adapter.notifyDataSetChanged();
        } else {
            Snackbar.make(view, "Please fill in all the fields correctly !", Snackbar.LENGTH_LONG).setAction("Okay", null).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

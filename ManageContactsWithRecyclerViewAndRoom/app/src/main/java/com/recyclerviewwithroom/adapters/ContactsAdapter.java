package com.recyclerviewwithroom.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recyclerviewwithroom.R;
import com.recyclerviewwithroom.activities.Main;
import com.recyclerviewwithroom.entities.Contact;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contact> list;
    private Activity context;

    public ContactsAdapter(Activity context, List<Contact> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Contact contact = list.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Confirm deleting contact !");
                builder.setMessage("Are you sure that you want to delete this contact ?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Contact contact = (Contact) view.getTag();
                        int index = list.indexOf(contact);
                        list.remove(contact);
                        Main.database.contactDao().delete(contact);
                        ContactsAdapter.this.notifyItemRemoved(index);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
        holder.call.setTag(contact);
        holder.sms.setTag(contact);
        holder.delete.setTag(contact);

        holder.update.setTag(contact);

        holder.name.setText(contact.getFirstName() + " " + contact.getLastName());
        holder.details.setText(contact.getJob() + "\n" + contact.getPhone() + "\n" + contact.getEmail());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = (Contact) view.getTag();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.getPhone()));
                context.startActivity(intent);
            }
        });
        holder.sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", contact.getPhone(), null)));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = (Contact) view.getTag();
                int index = list.indexOf(contact);
                list.remove(contact);
                Main.database.contactDao().delete(contact);
                ContactsAdapter.this.notifyItemRemoved(index);
            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = (Contact) view.getTag();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.manage_contact);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText firstName = dialog.findViewById(R.id.firstName);
                EditText lastName = dialog.findViewById(R.id.lastName);
                EditText email = dialog.findViewById(R.id.email);
                EditText job = dialog.findViewById(R.id.job);
                EditText phone = dialog.findViewById(R.id.phone);
                Button update = dialog.findViewById(R.id.update);

                CircleImageView image = dialog.findViewById(R.id.photo);


                firstName.setText(contact.getFirstName());
                lastName.setText(contact.getLastName());
                email.setText(contact.getEmail());
                job.setText(contact.getJob());
                phone.setText(contact.getPhone());


                if (contact.getPhoto() != null) {
                    String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyContacts";
                    File file = new File(rootPath, contact.getPhoto());
                    Picasso.get().load(file).into(image);
                }

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(firstName.getText().toString().trim()) || TextUtils.isEmpty(lastName.getText().toString().trim()) || TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(job.getText().toString().trim()) || TextUtils.isEmpty(phone.getText().toString().trim())) {
                            Toast.makeText(context, "Please fill in all the fields correctly !", Toast.LENGTH_SHORT).show();
                        } else {
                            contact.setFirstName(firstName.getText().toString().trim());
                            contact.setLastName(lastName.getText().toString().trim());
                            contact.setPhone(phone.getText().toString().trim());
                            contact.setEmail(email.getText().toString().trim());
                            contact.setJob(job.getText().toString().trim());
                            Main.database.contactDao().update(contact);
                            ContactsAdapter.this.notifyItemChanged(list.indexOf(contact));
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        if (contact.getPhoto() != null) {
            String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyContacts";
            File file = new File(rootPath, contact.getPhoto());
            Picasso.get().load(file).into(holder.photo);
        }


        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = (Contact) view.getTag();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.image_viewer);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                CircleImageView image = dialog.findViewById(R.id.photo);
                String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyContacts";
                File file = new File(rootPath, contact.getPhoto());
                Picasso.get().load(file).into(image);
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, details;
        CircleImageView photo;
        Button call, sms, delete, update;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.description);
            photo = itemView.findViewById(R.id.photo);
            call = itemView.findViewById(R.id.call);
            sms = itemView.findViewById(R.id.sms);
            delete = itemView.findViewById(R.id.delete);
            update = itemView.findViewById(R.id.update);
        }
    }
}

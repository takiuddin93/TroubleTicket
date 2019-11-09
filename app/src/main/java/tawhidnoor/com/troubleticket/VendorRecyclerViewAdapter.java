package tawhidnoor.com.troubleticket;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class VendorRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<Model> dataset;
    private Context mContext;
    Typeface subjectFonts, phoneFonts, messageFonts, resolvedFonts;

    public VendorRecyclerViewAdapter(ArrayList<Model> mlist, Context context) {
        this.dataset = mlist;
        this.mContext = context;
        subjectFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Bold.otf");
        phoneFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Bold.otf");
        messageFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Light.otf");
        resolvedFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Light.otf");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardComplaints;
        TextView subject, phone, message;
        Button resolve;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardComplaints = (CardView) itemView.findViewById(R.id.cardComplaints);
            this.subject = (TextView) itemView.findViewById(R.id.subject);
            this.phone = (TextView) itemView.findViewById(R.id.phone);
            this.message = (TextView) itemView.findViewById(R.id.message);
            this.resolve = (Button) itemView.findViewById(R.id.resolve);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_recyclerviewadapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Model object = dataset.get(position);

        ((ViewHolder) holder).subject.setTypeface(subjectFonts);
        ((ViewHolder) holder).subject.setText(Html.fromHtml(object.subject));
        ((ViewHolder) holder).phone.setTypeface(phoneFonts);
        ((ViewHolder) holder).phone.setText(Html.fromHtml(String.valueOf(object.phone)));
        ((ViewHolder) holder).message.setTypeface(messageFonts);
        ((ViewHolder) holder).message.setText(Html.fromHtml(object.message));

        if (object.resolved.equals("0")) {
            ((ViewHolder) holder).resolve.setTypeface(resolvedFonts);
            ((ViewHolder) holder).resolve.setText("Not Resolved");
            ((ViewHolder) holder).resolve.setBackgroundColor(Color.parseColor("#ff0000"));

            ((ViewHolder) holder).cardComplaints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ComplaintsDetails.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(mContext, R.anim.slide_in_up, R.anim.slide_out_up).toBundle();
                    intent.putExtra("itemPosition", position);
                    intent.putExtra("complainId", object.id);
                    intent.putExtra("vendorId", MainActivity.preferenceConfiguration.readUserid());
                    mContext.startActivity(intent, bundle);
                }
            });

        } else {
            ((ViewHolder) holder).resolve.setTypeface(resolvedFonts);
            ((ViewHolder) holder).resolve.setText("Resolved");
            ((ViewHolder) holder).resolve.setBackgroundColor(Color.parseColor("#008000"));
        }

    }

    public void filterList(ArrayList<Model> filteredList) {
        dataset = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}

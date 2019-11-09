package tawhidnoor.com.troubleticket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<AdminModel> dataset;
    private Context mContext;
    Typeface complainPostFonts, vendorFonts, complainFonts, callFonts;

    public AdminRecyclerViewAdapter(ArrayList<AdminModel> mlist, Context context) {
        this.dataset = mlist;
        this.mContext = context;
        complainPostFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Bold.otf");
        vendorFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Bold.otf");
        complainFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Light.otf");
        callFonts = Typeface.createFromAsset(mContext.getAssets(), "fonts/Poppins-Light.otf");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardUnresolved;
        TextView complainPost, vendor, complain;
        Button call;

        public ViewHolder(View itemView) {
            super(itemView);
            this.cardUnresolved = (CardView) itemView.findViewById(R.id.cardComplaints);
            this.complainPost = (TextView) itemView.findViewById(R.id.complainPost);
            this.vendor = (TextView) itemView.findViewById(R.id.vendor);
            this.complain = (TextView) itemView.findViewById(R.id.complain);
            this.call = (Button) itemView.findViewById(R.id.call);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_recyclerviewadapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final AdminModel object = dataset.get(position);

        ((ViewHolder) holder).complainPost.setTypeface(complainPostFonts);
        ((ViewHolder) holder).complainPost.setText(Html.fromHtml(object.complain_post_time));
        ((ViewHolder) holder).vendor.setTypeface(vendorFonts);
        ((ViewHolder) holder).vendor.setText(Html.fromHtml(String.valueOf(object.vendor_id)));
        ((ViewHolder) holder).complain.setTypeface(complainFonts);
        ((ViewHolder) holder).complain.setText(Html.fromHtml(object.complain_id));

        ((ViewHolder) holder).call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + 0 + object.vendor_phone));
                mContext.startActivity(callIntent);
            }
        });

    }

    public void filterList(ArrayList<AdminModel> filteredList) {
        dataset = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}

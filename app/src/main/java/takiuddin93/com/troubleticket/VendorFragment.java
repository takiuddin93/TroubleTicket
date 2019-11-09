package takiuddin93.com.troubleticket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VendorFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView searchResult;
    private EditText searchQuery;
    private ProgressBar progressBar;

    private LinearLayoutManager mLayoutManager;

    private ArrayList<Model> list;
    private VendorRecyclerViewAdapter adapter;

    private String baseURL = "http://www.plusequalsto.com/complaints/";
    public static List<Complaints> mComplains;

    public VendorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vendorView = inflater.inflate(R.layout.fragment_vendor, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) vendorView.findViewById(R.id.swipeVendor);

        searchQuery = (EditText) vendorView.findViewById(R.id.searchQuery);
        searchResult = (RecyclerView) vendorView.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) vendorView.findViewById(R.id.progressBar);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        searchResult.setLayoutManager(mLayoutManager);

        list = new ArrayList<Model>();

        getRetrofit();

        adapter = new VendorRecyclerViewAdapter(list, getActivity());
        searchResult.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // call getRetrofit
                        getRetrofit();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        return vendorView;
    }

    private void filter(String text) {
        ArrayList<Model> filteredList = new ArrayList<>();

        for (Model item : list) {
            if (item.id().contains(text.toLowerCase()) || item.name().contains(text.toLowerCase()) || item.phone().contains(text.toLowerCase()) || item.subject().contains(text.toLowerCase()) || item.message().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void getRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API service = retrofit.create(API.class);
        Call<List<Complaints>> call = service.getComplaints();

        call.enqueue(new Callback<List<Complaints>>() {
            @Override
            public void onResponse(Call<List<Complaints>> call, Response<List<Complaints>> response) {
                mComplains = response.body();
                progressBar.setVisibility(View.GONE);

                for (int i = 0; i < mComplains.size(); i++) {
                    Log.d("VendorFragment", mComplains.get(i).getResolved());
                    list.add(new Model(Model.IMAGE_TYPE, mComplains.get(i).getId(), mComplains.get(i).getName(),
                            mComplains.get(i).getPhone(), mComplains.get(i).getSubject(), mComplains.get(i).getMessage(),
                            mComplains.get(i).getResolved()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Complaints>> call, Throwable t) {
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

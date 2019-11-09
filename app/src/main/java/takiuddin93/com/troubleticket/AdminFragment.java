package takiuddin93.com.troubleticket;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView searchResult;
    private EditText searchQuery;
    private ProgressBar progressBar;

    private LinearLayoutManager mLayoutManager;

    private ArrayList<AdminModel> list;
    private AdminRecyclerViewAdapter adapter;

    private String baseURL = "http://www.plusequalsto.com/complaints/";
    public static List<Unresolved> mUnresolved;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View adminView = inflater.inflate(R.layout.fragment_admin, container, false);

        performAdminSignIn();

        swipeRefreshLayout = (SwipeRefreshLayout) adminView.findViewById(R.id.swipeVendor);

        searchQuery = (EditText) adminView.findViewById(R.id.searchQuery);
        searchResult = (RecyclerView) adminView.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) adminView.findViewById(R.id.progressBar);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        searchResult.setLayoutManager(mLayoutManager);

        list = new ArrayList<AdminModel>();

        getRetrofit();

        adapter = new AdminRecyclerViewAdapter(list, getActivity());
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

        // Inflate the layout for this fragment
        return adminView;
    }

    private void performAdminSignIn() {
//        final String username = userName.getText().toString();
        final String phone = MainActivity.preferenceConfiguration.readUserphone();
        final String password = MainActivity.preferenceConfiguration.readPassword();
        final String token = MainActivity.preferenceConfiguration.readAdminToken();
        Log.d("AdminFragment", phone + password + token);

        Call<Users> call = MainActivity.api.performAdminToken(phone, password, token);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Log.d("AdminFragment", "onResponse: " + response.body().getResponse());
                switch (response.body().getResponse()) {
                    case "ok":
                        MainActivity.preferenceConfiguration.displayToast("Token Save Success");
                        break;
                    case "failed":
                        MainActivity.preferenceConfiguration.displayToast("Token Save Failed");
                        break;
                    case "":
                        MainActivity.preferenceConfiguration.displayToast("Token Save hakgsdjaf");
                        break;
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<AdminModel> filteredList = new ArrayList<>();

        for (AdminModel item : list) {
            if (item.id().contains(text.toLowerCase()) || item.complain_post_time().contains(text.toLowerCase()) || item.vendor_id().contains(text.toLowerCase()) || item.complain_id().contains(text.toLowerCase())) {
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
        Call<List<Unresolved>> call = service.getUnresolved();

        call.enqueue(new Callback<List<Unresolved>>() {
            @Override
            public void onResponse(Call<List<Unresolved>> call, Response<List<Unresolved>> response) {
                mUnresolved = response.body();
                progressBar.setVisibility(View.GONE);

                for (int i = 0; i < mUnresolved.size(); i++) {
                    list.add(new AdminModel(AdminModel.IMAGE_TYPE, mUnresolved.get(i).getId(), mUnresolved.get(i).getVendor_id(),
                            mUnresolved.get(i).getVendor_phone(), mUnresolved.get(i).getComplain_id(), mUnresolved.get(i).getComplain_post_time()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Unresolved>> call, Throwable t) {
            }
        });
    }

}

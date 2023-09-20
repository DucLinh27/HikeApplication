package com.example.hikeapplication.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hikeapplication.ConnectDb;
import com.example.hikeapplication.Hike.Hike;
import com.example.hikeapplication.Hike.HikeAdapter;
import com.example.hikeapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchHikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchHikeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchHikeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchHikeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchHikeFragment newInstance(String param1, String param2) {
        SearchHikeFragment fragment = new SearchHikeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView recyclerView;
    List<Hike> hikeList = new ArrayList<>();
    HikeAdapter hikeAdapter;

    ConnectDb db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setTitle("Search hike");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_hike, container, false);
        recyclerView = view.findViewById(R.id.searchRecyclerView);
        db = new ConnectDb(getActivity());


        Button searchButton = view.findViewById(R.id.search_btn);
        searchButton.setOnClickListener(v -> {
            EditText inputSearch = view.findViewById(R.id.inputSearch);
            TextView result = view.findViewById(R.id.result);
            result.setText(R.string.result);
            displayHike();
            hikeAdapter = new HikeAdapter(getActivity(), getContext(), hikeList);
            hikeAdapter.getFilter().filter(inputSearch.getText());
            recyclerView.setAdapter(hikeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });


        return view;
    }

    public void displayHike() {
        hikeList.clear();
        hikeList = db.getHike();
        if (hikeList.size() == 0) {
            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
        }
    }
}